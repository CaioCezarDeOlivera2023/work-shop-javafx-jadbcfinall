package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;

public class DepartmentListController implements Initializable {// essa classe ira controlar os eventos do
																// Departmentlist

	@FXML
	private TableView<Department> tableViewDepartment;// referencia para a table view

	@FXML
	private TableColumn<Department, Integer> tableColumnId;

	@FXML
	private TableColumn<Department, String> tableColumnName;

	@FXML
	private Button btNew;

	@FXML
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();// metodo auxiliar para manipular a tabela

	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));// aqui esta iniciando o comportamento da
																			// coluna id
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));// aqui esta iniciando o comportamento
																				// da coluna name

		Stage stage = (Stage) Main.getMainScene().getWindow();//aqui esta configurando o alinhamento da tabela, chamando a classe principal e utilizando o metodo da super classe
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());//aqui vai acompanhar a altura da janela da tableView

	}
}