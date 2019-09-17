package application;

import donnee.Grille;
import donnee.ListeVoitures;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * Scene javaFX de la selection de niveau
 *
 * Bugs: aucun connu
 *
 * @author       Vincent Constantin 
 * @nom			 LevelSelection.java
 */
public class LevelSelection {
	private String strNiveau = null;
	LevelSelection(Stage primaryStage){
		
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setSpacing(40);
		root.setPadding(new Insets(10));
		Scene scene = new Scene(root,625,475);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("Rush Hour - par Vincent Constantin");
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("file:.\\images\\mini_facile.png")); 
		
		//mettre une image en background
		Image img = new Image("file:.\\images\\jaunenoir.gif");
		BackgroundImage bgImg = new BackgroundImage(img, 
		    BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
		    BackgroundPosition.CENTER, 
		    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
		root.setBackground(new Background(bgImg));
		//mettre le logo
		ImageView imgLogo = new ImageView("file:.\\images\\logo.gif");
		
		
		Button btnJouer = new Button("Jouer");
		btnJouer.setMinHeight(45);
		btnJouer.setMinWidth(250);
		btnJouer.setDisable(true);
		btnJouer.setOnAction(e -> {
			GrilleDeJeu grille = new GrilleDeJeu(primaryStage, strNiveau);
		});
		
		
		HBox hBoxLevel = new HBox();
		hBoxLevel.setAlignment(Pos.CENTER);
		hBoxLevel.setSpacing(20);
		
		ImageView imgSlectedDiff = new ImageView("file:.\\images\\selected.png");;
		ImageView imgSlectedMoyen = new ImageView("file:.\\images\\selected.png");;;
		ImageView imgSlectedFacile = new ImageView("file:.\\images\\selected.png");;;
		
			VBox vBoxFacile = new VBox();
			imgSlectedFacile.setFitWidth(50);
			imgSlectedFacile.setFitHeight(50);
			imgSlectedFacile.setVisible(false);
			Button btnFacile = new  Button();
				btnFacile.setGraphic(new ImageView("file:.\\images\\mini_facile.png"));
				btnFacile.setOnAction(e -> {
					btnJouer.setDisable(false);
					strNiveau = "f1.txt";
					imgSlectedFacile.setVisible(true);
					imgSlectedMoyen.setVisible(false);
					imgSlectedDiff.setVisible(false);
				});
			Text txtFacile = new Text("FACILE");
			txtFacile.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
			txtFacile.setFill(Color.WHITE);
			vBoxFacile.getChildren().addAll(imgSlectedFacile, btnFacile,txtFacile);
			vBoxFacile.setAlignment(Pos.CENTER);
			
			Separator sep1 = new Separator();
			sep1.setOrientation(Orientation.VERTICAL);
			
			VBox vBoxMoyen = new VBox();
			imgSlectedMoyen.setFitWidth(50);
			imgSlectedMoyen.setFitHeight(50);
			imgSlectedMoyen.setVisible(false);
			Button btnMoyen = new  Button();
				btnMoyen.setGraphic(new ImageView("file:.\\images\\mini_moyen.png"));
				btnMoyen.setOnAction(e -> {
					btnJouer.setDisable(false);
					strNiveau = "f2.txt";
					imgSlectedFacile.setVisible(false);
					imgSlectedMoyen.setVisible(true);
					imgSlectedDiff.setVisible(false);
				});
			Text txtMoyen = new Text("MOYEN");
			txtMoyen.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
			txtMoyen.setFill(Color.WHITE);
			vBoxMoyen.getChildren().addAll(imgSlectedMoyen,btnMoyen,txtMoyen);
			vBoxMoyen.setAlignment(Pos.CENTER);
			
			Separator sep2 = new Separator();
			sep2.setOrientation(Orientation.VERTICAL);
			
			VBox vBoxDiff = new VBox();
			imgSlectedDiff.setFitWidth(50);
			imgSlectedDiff.setFitHeight(50);
			imgSlectedDiff.setVisible(false);
			Button btnDiff = new  Button();
				btnDiff.setGraphic(new ImageView("file:.\\images\\mini_diff.png"));
				btnDiff.setOnAction(e -> {
					btnJouer.setDisable(false);
					strNiveau = "f3.txt";
					imgSlectedFacile.setVisible(false);
					imgSlectedMoyen.setVisible(false);
					imgSlectedDiff.setVisible(true);
				});
			Text txtDiff = new Text("DIFFICILE");
			txtDiff.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
			txtDiff.setFill(Color.WHITE);
			vBoxDiff.getChildren().addAll(imgSlectedDiff,btnDiff,txtDiff);
			vBoxDiff.setAlignment(Pos.CENTER);
			
			
		hBoxLevel.getChildren().addAll(vBoxFacile, sep1, vBoxMoyen, sep2, vBoxDiff);
		
	
		root.getChildren().addAll(imgLogo, hBoxLevel, btnJouer);

	}
}
