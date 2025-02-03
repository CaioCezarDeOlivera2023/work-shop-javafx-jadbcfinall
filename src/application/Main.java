package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Scene mainScene;
	
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));//aqui esta sendo instaciado o objeto FXMLLoader loader
			ScrollPane scrollPane = loader.load();//aqui esta chamando o loader.load
			
			scrollPane.setFitToHeight(true);//aqui esta corrigindo a barra do painel para chegar até o final
			scrollPane.setFitToWidth(true);//aqui esta corrigindo a barra do painel para chegar até o final
			
			
			mainScene = new Scene(scrollPane);//aqui esta sendo criado o objeto Scene passando como argumento o parent
			primaryStage.setScene(mainScene);//aqui esta dando um set no palco principal
			primaryStage.setTitle("Sample JavaFX application");//aqui esta dando um titulo.
			primaryStage.show();//aqui vai mostrar o palco principal
		} catch (IOException e) {//aqui esta tratando uma possivel exceção
			e.printStackTrace();
		}
	}
	
	public static Scene getMainScene() {
		return mainScene;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
