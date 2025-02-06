package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {

	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}

	public static Integer tryParsetoInt(String str) {//aqui esta convertendo um dado da caixa para String
		try {//trando uma possivel exceção.
			
		return Integer.parseInt(str);
	}
		catch (NumberFormatException e) {
			return null;
		}
	}

}
