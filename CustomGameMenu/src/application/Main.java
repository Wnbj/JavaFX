package application;
	
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Main extends Application {
	
	private GameMenu gameMenu;
	
	@Override
	public void start(Stage primaryStage) throws Exception{

			Pane root = new Pane();
			root.setPrefSize(800, 600);
			
			InputStream iStream = Files.newInputStream(Paths.get("res/background.jpg"));	
			Image img = new Image(iStream);
			iStream.close();
			
			ImageView imgView = new ImageView(img);
			imgView.setFitWidth(800);
			imgView.setFitHeight(600);
			
			this.gameMenu = new GameMenu();
			this.gameMenu.setVisible(false);
			
			root.getChildren().addAll(imgView, gameMenu);
					
			Scene scene = new Scene(root);
			scene.setOnKeyPressed(event -> {
				if (event.getCode() == KeyCode.SPACE) {
					if (!gameMenu.isVisible() ) {
						FadeTransition fTransition = new FadeTransition(Duration.seconds(0.5), gameMenu );
						fTransition.setFromValue(0);
						fTransition.setToValue(1);
						
						gameMenu.setVisible(true);
						fTransition.play();
					}
					else {
						FadeTransition fTransition = new FadeTransition(Duration.seconds(0.5), gameMenu);
						fTransition.setFromValue(1);
						fTransition.setToValue(0);
						fTransition.setOnFinished(evt -> gameMenu.setVisible(false));
						fTransition.play();
					}
				}
			});
			
			primaryStage.setScene(scene);
			primaryStage.show();
	}
	
	private class GameMenu extends Parent{
		public GameMenu(){
			VBox menu0 = new VBox(10);
			VBox menu1 = new VBox(10);
			
			menu0.setTranslateX(100);
			menu0.setTranslateY(200);
			
			menu1.setTranslateX(100);
			menu1.setTranslateY(200);
			
			final int offset = 400;
			
			menu1.setTranslateX(offset);
			
			MenuButton btnNewGame = new MenuButton("NEW GAME");
			btnNewGame.setOnMouseClicked(event -> {
				FadeTransition fTransition = new FadeTransition(Duration.seconds(0.5), this);
				fTransition.setFromValue(1);
				fTransition.setToValue(0);
				fTransition.setOnFinished(evt -> gameMenu.setVisible(false));
				fTransition.play();
			});
			
			MenuButton btnOptions = new MenuButton("OPTIONS");
			btnOptions.setOnMouseClicked(event -> {
				this.getChildren().add(menu1);
				
				TranslateTransition transition = new TranslateTransition(Duration.seconds(0.25), menu0);
				transition.setToX(menu0.getTranslateX() - offset);
				TranslateTransition transition2 = new TranslateTransition(Duration.seconds(0.5), menu1);
				transition2.setToX(menu0.getTranslateX());
				
				transition.play();
				transition2.play();
				
				transition.setOnFinished(evt -> {
					this.getChildren().remove(menu0);
				});
				
			});
			
			MenuButton btnExit = new MenuButton("EXIT");
			btnExit.setOnMouseClicked(event -> {
				System.exit(0);
			});
			
			MenuButton btnSound = new MenuButton("SOUND");
			MenuButton btnVideo = new MenuButton("VIDEO");
			
			MenuButton btnBack = new MenuButton("BACK");
			btnBack.setOnMouseClicked(event -> {
				this.getChildren().add(menu0);
				
				TranslateTransition transition = new TranslateTransition(Duration.seconds(0.25), menu1);
				transition.setToX(menu1.getTranslateX() - offset);
				TranslateTransition transition2 = new TranslateTransition(Duration.seconds(0.5), menu0);
				transition2.setToX(menu1.getTranslateX());
				
				transition.play();
				transition2.play();
				
				transition.setOnFinished(evt -> {
					this.getChildren().remove(menu1);
				});
				
			});
			
			menu0.getChildren().addAll(btnNewGame, btnOptions, btnExit);
			menu1.getChildren().addAll(btnSound, btnVideo, btnBack);
			
			Rectangle bg = new Rectangle(800, 600);
			bg.setFill(Color.GREY);
			bg.setOpacity(0.4);
			
			this.getChildren().addAll(bg, menu0);
			
		}
	}
	
	private static class MenuButton extends StackPane{
		
		private Text text;
		
		public MenuButton(String name){
			this.text = new Text(name);
			text.getFont();
			this.text.setFont(Font.font(20));
			this.text.setFill(Color.WHITE);

			Rectangle bg = new Rectangle(250, 30);
			bg.setOpacity(0.7);
			bg.setFill(Color.BLACK);
			GaussianBlur blur = new GaussianBlur(3.5);
			bg.setEffect(blur);
			
			this.setAlignment(Pos.CENTER_LEFT);
			setRotate(-0.5);
			getChildren().addAll(bg, text);
			
			this.setOnMouseEntered(event -> {
				bg.setTranslateX(20);
				text.setTranslateX(20);
				bg.setFill(Color.WHITE);
				text.setFill(Color.BLACK);	
			});
			
			this.setOnMouseExited(event ->{
				bg.setTranslateX(0);
				text.setTranslateX(0);
				bg.setFill(Color.BLACK);
				text.setFill(Color.WHITE);
			});
			
			DropShadow dropShadow = new DropShadow(50, Color.WHITE);
			dropShadow.setInput(new Glow());
			
			this.setOnMousePressed(event -> {
				setEffect(dropShadow);
			});
			this.setOnMouseReleased(event -> {
				setEffect(null);
			});
			
		}
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
