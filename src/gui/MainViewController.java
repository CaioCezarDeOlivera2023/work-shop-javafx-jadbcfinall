package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

public class MainViewController implements Initializable {// essa classe ira controlar o painel

	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuitemAbout;

	@FXML
	public void onMenuItemSellerAction() {// esse metho ira tratar os eventos do seller
		System.out.println("onMenuItemSellerAction");
	}

	@FXML
	public void onMenuItemDepartmentAction() {// esse metodo ira tratar os eventos do department
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		});
	}

	@FXML
	public void onMenuItemAboutAction() {// esse metodo ira tratar os eventos do about
		loadView("/gui/About.fxml", x -> {});//esta função não esta teornando nada, para não ter 2 funcções com mesmo code.
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {// esse metodo é da interface Initializable
	}

	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializinAction) {// synchronized esta garantindo que a aplicação não tenha
																// nenhuma interrupção
																//também esta sendo feito uma parametrização com o Consumer T.
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));// aqui esta carregando a view
																						// about
			VBox newVBox = loader.load();

			// aqui esta sendo manipulado os eventos do About view
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

			Node mainManu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainManu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			T controller = loader.getController();
			initializinAction.accept(controller);//aqui esta sendo chamado a função que foi criada no Department

		} catch (IOException e) {// aqui esta sendo tratado uma possivel exceção
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

}
