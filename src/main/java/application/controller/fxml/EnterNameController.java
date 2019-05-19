package application.controller.fxml;

import java.util.Optional;

import com.sp.fxutils.validation.FXUtils;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EnterNameController {

	@FXML
	private TextField nameTextField;
	
	private Stage windowStage = null;
	
	private Optional<String> name;
	
	@FXML
	private void initialize() {
		name = Optional.empty();
	}
	
	@FXML
	private void applyChanges() {
		boolean valid = FXUtils.textfieldTextIsNotNullOrEmpty(nameTextField);
		if(valid) {
			name = Optional.of(nameTextField.getText());
			windowStage.close();
		}
	}
	
	public Optional<String> getName() {
		return name;
	}

	public void setWindowStage(Stage windowStage) {
		this.windowStage = windowStage;
	}

	@FXML
	private void discardChanges() {
		windowStage.close();
	}
}
