package application;

import java.util.List;
import java.util.Optional;

import javax.swing.plaf.synth.SynthSpinnerUI;


import donnee.Direction;
import donnee.Grille;
import donnee.ListeVoitures;
import donnee.Resolution;
import donnee.Voiture;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 * Scene javaFX de la grille de jouer
 *
 * Bugs: Quand je joue et pendant que ca joue je change de niveau et rejoue tout de suite ce meme niveau, ca crash
 *
 * @author       Vincent Constantin
 * @nom			 GrilleDeJeu.java
 */
public class GrilleDeJeu {
	private int intVitesse = 600;
	private ListeVoitures lstVoituresDebut;
	@SuppressWarnings("unchecked")
	GrilleDeJeu(Stage primaryStage, String strNiveau){
		int intNb=0;
		HBox root = new HBox();
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(20));
		Scene scene = new Scene(root,900,650);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		
		//mettre une image en background
		Image img = new Image("file:.\\images\\jaunenoir.gif");
		BackgroundImage bgImg = new BackgroundImage(img, 
		    BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
		    BackgroundPosition.CENTER, 
		    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
		root.setBackground(new Background(bgImg));
		
		
		//creation du pane
		Pane pane = new Pane();
		pane.setMinHeight(586);
		pane.setMinWidth(527);
		
		//mettre une image en background
		Image img2 = new Image("file:.\\images\\grille.gif");
		BackgroundImage bgImg2 = new BackgroundImage(img2, 
		    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
		    BackgroundPosition.CENTER, 
		    new BackgroundSize(527, 586, false, false, false, false));
		pane.setBackground(new Background(bgImg2));
		//liste de voitures du depart
		lstVoituresDebut = new ListeVoitures(strNiveau);
		//grille de depart
		Grille grille = new Grille(lstVoituresDebut);
		//mettre les voitures a leurs places initiales
		for(Voiture voiture : lstVoituresDebut){
			pane.getChildren().add(voiture);
			voiture.setImage(new Image(voiture.getStrFilePath()));
			voiture.setLayoutX((voiture.getXpresent()*72)-29);
			voiture.setLayoutY((voiture.getYpresent()*72)+7);
		}
		//creation du vBox de droit
		VBox vBoxDroit = new VBox();
		Text txtNb = new Text(Integer.toString(intNb) + " mouvement");
		Slider slider = new Slider();
		vBoxDroit.setMaxWidth(50);
		vBoxDroit.setAlignment(Pos.CENTER);
		vBoxDroit.setSpacing(40);
		vBoxDroit.setPadding(new Insets(7));
			//Logo
			ImageView imgLogo = new ImageView("file:.\\images\\logo.gif");
		
			//button pour solutionner
			Button btnSolutionner = new Button("Solutionner");

			btnSolutionner.setMinWidth(220);
			btnSolutionner.setOnAction(e ->{
				solutionner(intVitesse, grille, intNb, txtNb);
				btnSolutionner.setDisable(true);
				slider.setDisable(true);
			});
			//button pour revenir à la selection de niveau
			Button btnChanger = new Button("Changer de Niveau");
			btnChanger.setMinWidth(220);
			btnChanger.setOnAction(e ->{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText("Changer de niveau");
				alert.setContentText("Vous êtes sur le point de quitter cette partie!");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					LevelSelection lvlSelection = new LevelSelection(primaryStage);
				}
			});
			//button pour quitter
			Button btnQuitter = new Button("Quitter");
			btnQuitter.setMinWidth(220);
			btnQuitter.setOnAction(e ->{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText("Quitter");
				alert.setContentText("Vous êtes sur le point de quitter cette partie!");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					primaryStage.close();
				}
			});
			//slider qui permet de choisir la vitesse des mouvements
			slider.setMin(1);
			slider.setMax(1499);
			slider.setValue(600);
			slider.setMajorTickUnit(50);
			slider.setMinorTickCount(5);
			slider.setBlockIncrement(10);
			slider.setMaxWidth(220);
			slider.setAccessibleText("La vitesse de l'animation");
			slider.valueProperty().addListener(new ChangeListener() {
	            @Override
	            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
	            	intVitesse = (int) slider.getValue();
	            }
	        });
			//Label affichant le nombre de mouvement
			txtNb.setId("nombre");			
			
		vBoxDroit.getChildren().addAll(imgLogo, txtNb, btnSolutionner,slider, btnChanger, btnQuitter);
		
		root.getChildren().addAll(pane, vBoxDroit);
		
		
		
		
	}
	public void solutionner(int intVitesse, Grille grille, int intNb, Text txtNb) {
		//resolution
		Resolution resolution = new Resolution(grille);
		List<Grille> res = resolution.bfs();
		
		SequentialTransition animations = new SequentialTransition();
		//animation de la resolution
		for(Grille grilleRes : res) {
			if(grilleRes.getDirectionFather()!= null) {
				intNb++;
				txtNb.setText(Integer.toString(intNb) + " mouvements");
				animations.getChildren().add(animation(intVitesse , grilleRes.getVoitureFather(),grilleRes.getDirectionFather()));	
			}
		}
		intNb++;
		txtNb.setText(Integer.toString(intNb) + " mouvements");
		animations.getChildren().add(animation(intVitesse , 0, Direction.R));
		animations.play();
	}
	public TranslateTransition animation(int intVitesse, int i, Direction direction) {
		TranslateTransition translation = new TranslateTransition();
		translation.setDuration(Duration.millis(1500-intVitesse));
		switch (direction) {
			case D:
		        translation.setNode(lstVoituresDebut.get(i));
		        translation.setFromY(lstVoituresDebut.get(i).getTranslateY());
		        translation.setToY(lstVoituresDebut.get(i).getTranslateY()+72);
		        lstVoituresDebut.get(i).setTranslateY(lstVoituresDebut.get(i).getTranslateY()+72);
				break;
			case U:
		        translation.setNode(lstVoituresDebut.get(i));
		        translation.setFromY(lstVoituresDebut.get(i).getTranslateY());
		        translation.setToY(lstVoituresDebut.get(i).getTranslateY()-72);
		        lstVoituresDebut.get(i).setTranslateY(lstVoituresDebut.get(i).getTranslateY()-72);
				break;
			case R:
		        translation.setNode(lstVoituresDebut.get(i));
		        translation.setFromX(lstVoituresDebut.get(i).getTranslateX());
		        translation.setToX(lstVoituresDebut.get(i).getTranslateX()+72);
		        lstVoituresDebut.get(i).setTranslateX(lstVoituresDebut.get(i).getTranslateX()+72);
				break;
			case L:
				
		        translation.setNode(lstVoituresDebut.get(i));
		        translation.setFromX(lstVoituresDebut.get(i).getTranslateX());
		        translation.setToX(lstVoituresDebut.get(i).getTranslateX()-72);
		        lstVoituresDebut.get(i).setTranslateX(lstVoituresDebut.get(i).getTranslateX()-72);
				break;		
		}
		return translation;
	}

}
