package paint.FXML;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Notifications {

	public static void showAboutAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);

		alert.setTitle("About");
		alert.setHeaderText("SIMPLE PAINT APPLICATION");
		alert.setContentText("From OOLT.ICT.20192.Group8\nMembers: Duc, Hung, Hoang");

		alert.showAndWait();
	}

	public static int showExitAlert() {
		Alert alert = new Alert(AlertType.WARNING);

		alert.setTitle("Exit application");
		alert.setHeaderText("ARE YOU SURE TO EXIT?");
		alert.setContentText("Your progress will NOT be saved!");

		ButtonType yesBtn = new ButtonType("Yes");
		ButtonType noBtn = new ButtonType("No");

		alert.getButtonTypes().setAll(yesBtn, noBtn);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == yesBtn) {
			return 1;
		} else return 0;
	}

	public static int showNewFileAlert() {
		Alert alert = new Alert(AlertType.CONFIRMATION);

		alert.setTitle("New file confirmation.");
		alert.setHeaderText("CREATE NEW ART WITHOUT SAVING?");
		alert.setContentText("Press:\t"
				+ "\"OK\" to create new file without saving.\n\t\t"
				+ "\"Save\" to save the current file then create a new one.\n\t\t"
				+ "\"Cancel\" to continue painting.\n");

		ButtonType OKBtn = new ButtonType("OK");
		ButtonType saveBtn = new ButtonType("Save");
		ButtonType cancelBtn = new ButtonType("Cancel");

		alert.getButtonTypes().setAll(OKBtn, saveBtn, cancelBtn);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == saveBtn) {
			return 1;
		} else if (result.get() == OKBtn) {
			return 2;
		} else {
			return 3;
		}
	}

}
