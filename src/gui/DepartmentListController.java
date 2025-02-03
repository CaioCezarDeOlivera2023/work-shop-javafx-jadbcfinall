package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable {// essa classe ira controlar os eventos do
																// Departmentlist

	private DepartmentService service;
	
	@FXML
	private TableView<Department> tableViewDepartment;// referencia para a table view

	@FXML
	private TableColumn<Department, Integer> tableColumnId;

	@FXML
	private TableColumn<Department, String> tableColumnName;

	@FXML
	private Button btNew;
	
	private ObservableList<Department> obsList;//aqui é onde sera que os departments serão carregados

	@FXML
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
	}
	
	public void setDepartmentService(DepartmentService service) {//aqui é a inversão de controle solid para ingesão da classe DepartmentService
		this.service = service;
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
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());//aqui esta acompanhado o tamanho da tableView

	}
	
	public void UpdateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Department> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsList);
	}
}