package application;
	
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

import donnee.Grille;
import donnee.ListeVoitures;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;



/**
 * main class qui lunch la Stage javaFX
 *
 * Bugs: aucun connu
 *
 * @author       Vincent Constantin
 * @nom			 main.java
 */
public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStage) {
		try {
			LevelSelection selection = new LevelSelection(primaryStage);
			//GrilleDeJeu grilleDeJeu = new GrilleDeJeu(primaryStage, "test.txt");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
