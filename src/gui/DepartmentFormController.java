package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable {

	private Department entity;
	
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
	
	@FXML
	public void OnBtSavaAction() {// metodo para os eventos do botão save
		System.out.println("onBtSaveAction");
	}

	@FXML
	public void onBtCacelAction() {// metodo para os eventos do botão cancel
		System.out.println("onBtCancelAction");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {//aqui é o controlado basico dos eventos
		initializaNodes();

	}

	private void initializaNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");//programação defenciva caso a entidade estiver com o valor nulo
		}
		txtId.setText(String.valueOf(entity.getId()));//aqui esta convertendo o o entity que é inteiro para string
		txtName.setText(entity.getName());
	}

}
