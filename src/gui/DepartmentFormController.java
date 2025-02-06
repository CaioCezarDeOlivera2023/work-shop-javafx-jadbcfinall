package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

	private Department entity;

	private DepartmentService service;
	
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

	public void setDepartment(Department entity) {
		this.entity = entity;
	}

	public void setDepartmentService(DepartmentService service) {
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
			Utils.currentStage(event).close();//aqui esta fechando a janela após inserir um novo departamento
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

	private Department getFormData() {// metodo para salvar os valores no formulario
		Department obj = new Department();

		obj.setId(Utils.tryParsetoInt(txtId.getText()));// aqui esta convertendo o valor para inteiro e caso não tiver
														// valo ro mesmo sera nulo
		obj.setName(txtName.getText());

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

}
