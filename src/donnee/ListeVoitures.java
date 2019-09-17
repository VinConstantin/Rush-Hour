package donnee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
/**
 * Liste de voitures qui permet de cree la liste de voitures
 *
 * Bugs: aucun connu
 *
 * @author       Vincent Constantin
 * @nom			 ListeVoitures.java
 */
public  class ListeVoitures extends ArrayList<Voiture> {
	private char ch = 'A';
	private static final long serialVersionUID = 1L;
	//constructeur initiales de la liste de voitues
	public ListeVoitures(String strFicher){
		BufferedReader brFichier = null;
		try {
			brFichier = new BufferedReader(new FileReader(strFicher));
			String strLigne = null;
			StringTokenizer st;

			while ((strLigne = brFichier.readLine())!= null ){
				st = new StringTokenizer(strLigne, ",");

				String strColor = st.nextToken().trim();
				int intDimension = Integer.parseInt(st.nextToken().trim());
				int X = Integer.parseInt(st.nextToken().trim());
				int Y = Integer.parseInt(st.nextToken().trim());
				Orientation orientation = null;
				switch (st.nextToken().trim()) {
				case "H":
					orientation = Orientation.HORIZONTAL;
					break;
				case "V":
					orientation = Orientation.VERTICAL;
					break;
				default:
					break;
				}
				
				//associer le bon imageview
				String strFilePath = null;
				switch (intDimension) {
				//si camion
				case 3:
					switch (orientation) {
					//si horizontal
					case HORIZONTAL:
						strFilePath = ("file:.\\images\\camion_H_"+strColor+".gif");
					break;
					//si vertical
					case VERTICAL:
						strFilePath = ("file:.\\images\\camion_V_"+strColor+".gif");
					break;
					}		
				break;
				//si voiture
				case 2:
					switch (orientation) {
					//si horizontal
					case HORIZONTAL:
						strFilePath = ("file:.\\images\\auto_H_"+strColor+".gif");
					break;
					//si vertical
					case VERTICAL:
						strFilePath = ("file:.\\images\\auto_V_"+strColor+".gif");
					break;
					}	
				break;
				}
				//créer la voiture et l'ajouter au la liste et au grid
				Voiture voiture = new Voiture(strColor, intDimension, X, Y, orientation, strFilePath);
				add(voiture);
			}
				
		} catch(Exception e1) {
			e1.printStackTrace();
		}
		
	}
	//constructeur des liste de voitures suivant l'initial
	public ListeVoitures() {
		// TODO Auto-generated constructor stub
	}
	//ajoute la voiture au arraylist
	public boolean add(Voiture voiture){	
		//si voiture rouge mettre R et en premier du arraylist
		if(voiture.getStrColor().equals("rouge")){
			super.add(0, voiture);
			voiture.setChar('R');
		}
		else{
			//sinon mettre le char suivant
			super.add(voiture);
			voiture.setChar(ch);
			ch ++;
		}
		return false;
	}

}
