package gui;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.SellerService;

public class SellerListController implements Initializable, DataChangeListener {// essa classe ira controlar os
																					// eventos do
	// Sellerlist

	private SellerService service;

	@FXML
	private TableView<Seller> tableViewSeller;// referencia para a table view

	@FXML
	private TableColumn<Seller, Integer> tableColumnId;

	@FXML
	private TableColumn<Seller, String> tableColumnName;

	@FXML
	private TableColumn<Seller, Seller> tableColumnEDIT;

	@FXML
	TableColumn<Seller, Seller> tableColumnREMOVE;

	@FXML
	private Button btNew;

	private ObservableList<Seller> obsList;// aqui é onde sera que os Sellers serão carregados

	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Seller obj = new Seller();
		createDialogForm(obj, "/gui/SellerForm.fxml", parentStage);
	}

	public void setSellerService(SellerService service) {// aqui é a inversão de controle solid para ingesão da
																	// classe SellerService
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
		tableViewSeller.prefHeightProperty().bind(stage.heightProperty());// aqui esta acompanhado o tamanho da
																				// tableView

	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Seller> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewSeller.setItems(obsList);
		initEditButtons();// aqui esta chamado o metodo para editar o depatamento
		initRemoveButtons();//aqui esta chamando o metodo para remover o departamento
		
	}

	private void createDialogForm(Seller obj, String absoluteName, Stage parentStage) {
//		try {// essa função esta sendo para carregar a janela formulario para preencher o
//				// novo departamento
//			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
//			Pane pane = loader.load();
//
//			SellerFormController controller = loader.getController();
//			controller.setSeller(obj);// aqui esta carregando o obj no formulario
//			controller.setSellerService(new SellerService());// aqui esta injetando a dependencia do service
//																		// para o botam save
//			controller.subscribeDataChangeListener(this);// aqui esta recendo o evento dataChange
//			controller.updateFormData();
//
//			Stage dialogStage = new Stage();
//			dialogStage.setTitle("Enter Seller Data");
//			dialogStage.setScene(new Scene(pane));
//			dialogStage.setResizable(false);
//			dialogStage.initOwner(parentStage);
//			dialogStage.initModality(Modality.WINDOW_MODAL);
//			dialogStage.showAndWait();
//
//		} catch (IOException e) {
//			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
//		}

	}

	@Override
	public void onDataChanged() {// aqui esta sendo executado o metodo para receber o evento qundo for disparado.
		updateTableView();

	}

	private void initEditButtons() {// esse metodo esta criando o botao de edição para cada linha da tabela
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);// aqui esta configurando os eventos do botão
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/SellerForm.fxml", Utils.currentStage(event)));// aqui  esta criando uma tela de cadastro
																										
			}
		});
	}

	private void initRemoveButtons() {//metodo responsavel por deletar um deparmento
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
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

	private void  removeEntity(Seller obj) {
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