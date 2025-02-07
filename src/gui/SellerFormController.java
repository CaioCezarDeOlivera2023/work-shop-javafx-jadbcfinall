package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.SellerService;

public class SellerFormController implements Initializable {

	private Seller entity;

	private SellerService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtName;

	@FXML
	private Label labelErroName;

	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;

	public void setSeller(Seller entity) {
		this.entity = entity;
	}

	public void setSellerService(SellerService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@FXML
	public void OnBtSavaAction(ActionEvent event) {// metodo para os eventos do botão save
		if (entity == null) {// programação defenciva caso o valor for nulo.
			throw new IllegalStateException("entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Servive was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);// aqui esta salvando os dados
			notifyDataChageListeners();//aqui esta notoficando os listeners
			Utils.currentStage(event).close();//aqui esta fechando a janela após inserir um novo Seller
		} 
		catch(ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private void notifyDataChageListeners() {//aqui é o metodo para notificar chamado da interface datachagelistener ondatachanged
			for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
		
	}

	private Seller getFormData() {// metodo para salvar os valores no formulario
		Seller obj = new Seller();

		ValidationException exception = new ValidationException("Validation Error");
		
		obj.setId(Utils.tryParsetoInt(txtId.getText()));// aqui esta convertendo o valor para inteiro e caso não tiver
														// valo ro mesmo sera nulo
		if (txtName.getText() == null || txtName.getText().trim().equals("")) {//aqui esta uma possivel exceção caso o campo name esteja vasio
			exception.addError("name", "Field can't be empty");
		}
		obj.setName(txtName.getText());
		
		if(exception.getErrors().size() > 0) {
			throw exception;
		}

		return obj;
	}

	@FXML
	public void onBtCacelAction(ActionEvent event) {// metodo para os eventos do botão cancel
		Utils.currentStage(event).close();//aqui esta fechando a janela após cancelar algum evento
		
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {// aqui é o controlado basico dos eventos
		initializaNodes();

	}

	private void initializaNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");// programação defenciva caso a entidade estiver com o
																// valor nulo
		}
		txtId.setText(String.valueOf(entity.getId()));// aqui esta convertendo o o entity que é inteiro para string
		txtName.setText(entity.getName());
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if(fields.contains("name")) {//fazendo a verificação se o campo name esta vazio 
			labelErroName.setText(errors.get("name"));
		}
	}

}
