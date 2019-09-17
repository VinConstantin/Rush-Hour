package donnee;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 * Permet la resolution du'une grille de départ part un Breadth-first search algorithm
 *
 * Bugs: aucun connu
 *
 * @author       Vincent Constantin
 * @nom			 Resolution.java
 */
public class Resolution {

	private Grille posDepart;
    public Resolution(Grille posDepart)
    {
        this.posDepart = posDepart;
    }
    //Breadth-first search algorithm qui permet de trouver la grille gagnante et de retourner son path
	public List<Grille> bfs()
    {
		long startTime = System.nanoTime();
		Queue<Grille> queue = new LinkedList<Grille>();
        queue.add(posDepart);
        while (!queue.isEmpty())
        {
        	Grille next = queue.remove();
            if (next.isGoal())
            {                     
            	long endTime = System.nanoTime();
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Victoire!");
                alert.setHeaderText("Victoire!");
                alert.setContentText("La solution c'est fait en " + next.getPath().size() + " mouvements!\n"
                		+ "Durée pour trouver la solution : "+(endTime - startTime)/1000000 + " ms");
                alert.showAndWait();
                return next.getPath();
            }
            next.setChildren();
            for (Grille child : next.getChildren()) {
                queue.add(child);
            }    
            
        }
        return null;
    }
}
