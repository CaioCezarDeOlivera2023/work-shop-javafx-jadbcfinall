package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable, DataChangeListener {// essa classe ira controlar os
																					// eventos do
	// Departmentlist

	private DepartmentService service;

	@FXML
	private TableView<Department> tableViewDepartment;// referencia para a table view

	@FXML
	private TableColumn<Department, Integer> tableColumnId;

	@FXML
	private TableColumn<Department, String> tableColumnName;

	@FXML
	private TableColumn<Department, Department> tableColumnEDIT;

	@FXML
	TableColumn<Department, Department> tableColumnREMOVE;

	@FXML
	private Button btNew;

	private ObservableList<Department> obsList;// aqui é onde sera que os departments serão carregados

	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Department obj = new Department();
		createDialogForm(obj, "/gui/DepartmentForm.fxml", parentStage);
	}

	public void setDepartmentService(DepartmentService service) {// aqui é a inversão de controle solid para ingesão da
																	// classe DepartmentService
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

		Stage stage = (Stage) Main.getMainScene().getWindow();// aqui esta configurando o alinhamento da tabela,
																// chamando a classe principal e utilizando o metodo da
																// super classe
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());// aqui esta acompanhado o tamanho da
																				// tableView

	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Department> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsList);
		initEditButtons();// aqui esta chamado o metodo para editar o depatamento
		initRemoveButtons();//aqui esta chamando o metodo para remover o departamento
		
	}

	private void createDialogForm(Department obj, String absoluteName, Stage parentStage) {
		try {// essa função esta sendo para carregar a janela formulario para preencher o
				// novo departamento
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			DepartmentFormController controller = loader.getController();
			controller.setDepartment(obj);// aqui esta carregando o obj no formulario
			controller.setDepartmentService(new DepartmentService());// aqui esta injetando a dependencia do service
																		// para o botam save
			controller.subscribeDataChangeListener(this);// aqui esta recendo o evento dataChange
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Department Data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}

	}

	@Override
	public void onDataChanged() {// aqui esta sendo executado o metodo para receber o evento qundo for disparado.
		updateTableView();

	}

	private void initEditButtons() {// esse metodo esta criando o botao de edição para cada linha da tabela
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Department, Department>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Department obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);// aqui esta configurando os eventos do botão
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/DepartmentForm.fxml", Utils.currentStage(event)));// aqui  esta criando uma tela de cadastro
																										
			}
		});
	}

	private void initRemoveButtons() {//metodo responsavel por deletar um deparmento
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Department, Department>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Department obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);//configuração do evento 
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void  removeEntity(Department obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete? ");//aqui é um alerta de confirção para deletar o deparmento
		
		if (result.get() == ButtonType.OK) {
			if (service == null) {//promação defenciva para uma posivel exceção caso o valor for nulo
				throw new IllegalStateException("Service wa null");
			}
			try {
			service.romeve(obj);
			updateTableView();
			}
			catch(DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}

}