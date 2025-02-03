package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable{//essa classe ira controlar o painel

	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuitemAbout;
	
	@FXML
	public void onMenuItemSellerAction() {//esse metho ira tratar os eventos do seller
		System.out.println("onMenuItemSellerAction");
	}
	@FXML
	public void onMenuItemDepartmentAction() {//esse metodo ira tratar os eventos do department
		System.out.println("onMenuItemDepartmentAction");
	}
	@FXML
	public void onMenuItemAboutAction() {//esse metodo ira tratar os eventos do about
		System.out.println("onMenuItemAboutAction");
	}
	
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {//esse metodo é da interface Initializable
		
		
	}

}
