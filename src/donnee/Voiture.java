package donnee;

import java.util.ArrayList;
import java.util.List;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

/**
 * Représente un voiture
 *
 * Bugs: aucun connu
 *
 * @author       Vincent Constantin
 * @nom			 Voiture.java
 */
public class Voiture extends ImageView implements Cloneable {
	private String strColor;		//la couleur de la voiture
	private int intDimension;		//dimension de la voiture
	private int Xpresent;			//coord en x de la voiture
	private int Ypresent;			//coord en y de la voitures
	private Orientation orientation;//orientation		
	private char ch;				//char qui represente la voiture
	private String strFilePath;		//le filepath associé à l'image
	
	private ArrayList<Direction> lstMove = new ArrayList<Direction>();//liste des mouvements que la voiture peut faire
	//return laliste des mouvements que la voiture peut faire
	public ArrayList<Direction> getLstMove() {
		return lstMove;
	}
	//ajoute un mouvements a la liste de mouvements que la voiture peut faire
	public void addMove(Direction direction) {
		this.lstMove.add(direction);
	}
	//constructeur pour les voitures initiales
	public Voiture(String strColor, int intDimension, int Xpresent, int Ypresent, Orientation orientation, String strFilePath) {
		this.strColor = strColor;
		this.intDimension = intDimension;
		this.Xpresent = Xpresent+1;
		this.Ypresent = Ypresent+1;
		this.orientation = orientation;
		this.strFilePath = strFilePath;

		handleEvent();
	}
	//constructeur des voitures suivants (clone)
    public Voiture(String strColor, int intDimension, int xpresent, int ypresent, Orientation orientation, char ch,String strFilePath) {
		this.strFilePath = strFilePath;
		this.strColor = strColor;
		this.intDimension = intDimension;
		this.Xpresent = xpresent;
		this.Ypresent = ypresent;
		this.orientation = orientation;
		this.ch = ch;

		handleEvent();
	}
	private void handleEvent() {
		this.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				/* data is dragged over the target */
				/* accept it only if it is not dragged from the same node
				 * and if it has a string data */
				event.acceptTransferModes(TransferMode.COPY_OR_MOVE);

				event.consume();
			}
		});
	}
	private void dragCar(double x, double y, MouseEvent e){
		if (orientation == Orientation.HORIZONTAL){
			setXpresent((int)x);
		}
		else if (orientation == Orientation.VERTICAL){
			setYpresent((int)y);
		}
	}
    //clone une voiture
    public Voiture clone()
    {
    	Voiture c = new Voiture( strColor,  intDimension,  Xpresent,  Ypresent,  orientation, ch, strFilePath);
        return c;
    }
    //bouge la voiture dans la direction donnée
    public void move(Direction direction)
    {
        if (direction == Direction.U)
        {
            --Ypresent;
        }
        else if (direction == Direction.D)
        {
        	++Ypresent;
        }
		else if (direction == Direction.L)
        {
            --Xpresent;
        }
		else if (direction == Direction.R)
        {
            ++Xpresent;
        }
    }
    //set le char de la voiture
	public void setChar(char ch) {
		this.ch = ch;
	}
	//return le char de la voiture
	public char getChar() {
		return ch;
	}

	@Override
	public String toString() {
		return "Voiture [strColor=" + strColor + ", intDimension=" + intDimension + ", Xinitial=" + Xpresent
				+ ", Yinitial=" + Ypresent + ", orientation=" + orientation + ", strFilePath=" + super.getImage() +"]";
	}
	public String getStrColor() {
		return strColor;
	}
	public int getIntDimension() {
		return intDimension;
	}
	public Orientation getOrientation() {
		return orientation;
	}
	public int getXpresent() {
		return Xpresent;
	}
	public void setXpresent(int xpresent) {
		Xpresent = xpresent;
	}
	public int getYpresent() {
		return Ypresent;
	}
	public String getStrFilePath() {
		return strFilePath;
	}
	public void setStrFilePath(String strFilePath) {
		this.strFilePath = strFilePath;
	}
	public void setYpresent(int ypresent) {
		Ypresent = ypresent;
	}
	
	
	
}
