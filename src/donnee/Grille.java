package donnee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
/**
 * Objet qui représente une grille qui contient les voitures
 *
 * Bugs: aucun connu
 *
 * @author       Vincent Constantin
 * @nom			 Grille.java
 */
public class Grille{
	private char[][] matrix = new char[8][8];   						//matrix qui représente la grille
	private ListeVoitures lstVoitures = new ListeVoitures();			//liste des voitures
	private int intFather;												//index de la voiture qui a créer la grille
	private Direction directionFather;									//direction de la voiture qui a créer la grille
	private Grille father = null;										//grille pere
	private List<Grille> children = new ArrayList<>();					//grille enfant de celle-ci
	private String strCle = "";											//la clé de cette grille
	private static ArrayList<String> arrCle = new ArrayList<String>();	//liste des grilles déjà visité
	
	//return le array list representant les clé des grilles deja visités
	/*
	 * Pour des question de performances, au lieu de comparer la matrix ou encore la liste de voiture, 
	 * j'ai décider de comparer un String qui represente la position de chaquoi voiture (ex. A12B45C78 : la voiture a est a 1,2 , la voiture B a 4,5 etc.)
	 */
	public static ArrayList<String> getArr(){
		return arrCle;		
	}
	//ajoute la clee a l'objet
	public static void addArrCle(String strCle) {
		arrCle.add(strCle);
	}
	//return la cle de l'objet
	public String getStrCle() {
		return strCle;
	}
	//constructeur de la Grille initial
	public Grille(ListeVoitures lstVoitures){
		this.lstVoitures = lstVoitures;
		createCle(); 
		createMatrix();
	}
	//constructeur des grilles venant d'un parent
    public Grille(Grille vader,Direction direction, int iVoiture)
    {
        for (Voiture car: vader.getLstVoiture()) {
            this.lstVoitures.add(car.clone());
        }

        this.directionFather = direction;
        this.intFather = iVoiture;
        this.lstVoitures.get(iVoiture).move(direction);
        father = vader;
        createCle(); 
        createMatrix();
    }
    //génere la clé en prenant les positions des voitures sur la grille
	private void createCle() {
		for (Voiture voiture : lstVoitures) {
			strCle += voiture.getChar() +""+ voiture.getXpresent() +""+voiture.getYpresent();
		}
	}
	//creation de la matrix [Y][X] ___ Positionnement des voitures dans un tableau 2D de char
	private void createMatrix(){
		//remplir toute la matrix avec des "."("." = espace libre)
		for (char[] row: matrix)
		    Arrays.fill(row, '.');
		//encadrer la matrix avec des "@"
		for(int Y = 0; Y<8; Y++){
			matrix[Y][0] = '@';
			matrix[Y][7] = '@';
		}
		for(int X = 0; X<8; X++){
			matrix[0][X] = '@';
			matrix[7][X] = '@';
		}
		//marquer la porte dans la matrix avec un "*"
		matrix[3][7] = '*';
		//marquer les voitures par leurs char associé dans la matrix
		refreshMatrix();
	}
	//marquer les voitures par leurs char associé dans la matrix
	private void refreshMatrix(){ 
		for(Voiture voiture : lstVoitures){
			for(int i = 0; i < voiture.getIntDimension() ; i ++){
				switch (voiture.getOrientation()) {
				case VERTICAL:
					matrix[(voiture.getYpresent())+i][(voiture.getXpresent())] = voiture.getChar();
					break;
				case HORIZONTAL:
					matrix[(voiture.getYpresent())][(voiture.getXpresent())+i] = voiture.getChar();
					break;
	
				default:
					break;
				}			
			}
		}
	}
	//print la matrix
	public String toString(){
	String str = "";
	  for (char[] row : matrix)
		  str += Arrays.toString(row) + "\n";
	  return str;
	}
	//regarde les moves que les voiture peut faire et retourne la liste des liste des moves possibles
	private ArrayList<ArrayList<Direction>> getPossibleMoves(){	
		ArrayList<ArrayList<Direction>> arr = new ArrayList<ArrayList<Direction>>();
		for (Voiture voiture : lstVoitures) {	
			switch (voiture.getOrientation()) {
			case VERTICAL:
						if(matrix[voiture.getYpresent()+voiture.getIntDimension()][voiture.getXpresent()] == '.'){
							voiture.addMove(Direction.D);
						}		
						if(matrix[voiture.getYpresent()-1][voiture.getXpresent()] == '.'){
							voiture.addMove(Direction.U);
						}	
				break;
			
			case HORIZONTAL:
						if(matrix[voiture.getYpresent()][voiture.getXpresent()+voiture.getIntDimension()] == '.'){
							voiture.addMove(Direction.R);
						}	
						if(matrix[voiture.getYpresent()][voiture.getXpresent()-1] == '.'){
							voiture.addMove(Direction.L);
						}	
				break;
			}
			arr.add(voiture.getLstMove());
		}
		return arr;
	}
	//regarder si la voiture rouge est a la position finale
    public boolean isGoal() {
        return matrix[3][6]=='R';
    }
    //return la liste de voitures
    public ListeVoitures getLstVoiture(){
    	return lstVoitures;
    }
    //ajoutes tous les grilles enfant a la grille parent
    public void setChildren()
    {
        if (children.isEmpty())
        {
            getNextGrille(getPossibleMoves());
        }
    }
    private void getNextGrille(ArrayList<ArrayList<Direction>> moves) {
    	children.clear();
    	for (int i = 0; i < moves.size(); i++) {
    		  ArrayList<Direction> info = moves.get(i);
    		  for (int j = 0; j < info.size(); j++) {
    			  if(father!=null) {
	    			  if(true) {
	    				   Grille nextGrille = new Grille(this, info.get(j), i);
	    				   if(!(arrCle.contains(nextGrille.getStrCle()))){
	    			           Grille.addArrCle(nextGrille.getStrCle());
	    					   children.add(nextGrille);
	    				   }
	    				   if(nextGrille.isGoal())
	    					   break;
	    			  }
    			  }
    			  else {
    				  Grille nextGrille = new Grille(this, info.get(j), i);
   				   	children.add(nextGrille);
    			  }
				
			}
    		 
		}
    }
    //return la matrix
    public char[][] getMatrix() {
		return matrix;
	}
    //regarde les parents de la grille et return son path
	public List<Grille> getPath()
    {
        Grille temp_father;
        List<Grille> path = new LinkedList<>();
        path.add(this);
        temp_father = this.father;
        while (temp_father != null)
        {
            path.add(0, temp_father);
            temp_father = temp_father.father;
        }
        return path;
    }
	//retourne l'index de la voiture utilisé pour générer cette grille
	public int getVoitureFather() {
		return intFather;
	}
	//set l'index de la voiture utilisé pour générer cette grille
	public void setVoitureFather(int intFather) {
		this.intFather = intFather;
	}
	//retourne la direction du mouvement utilisé pour générer cette grille
	public Direction getDirectionFather() {
		return directionFather;
	}
	//set la direction du mouvement utilisé pour générer cette grille
	public void setDirectionFather(Direction directionFather) {
		this.directionFather = directionFather;
	}
	//return la liste des enfants
	public List<Grille> getChildren() {
		return children;
	}
	//set la liste des enfants
	public void setChildren(List<Grille> children) {
		this.children = children;
	}   
}
