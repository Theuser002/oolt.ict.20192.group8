package paint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import paint.FXML.PaintController;

public class PaintMain extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("FXML/PaintFXML.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Paint");
		stage.setScene(scene);
		
//		stage.setMaximized(true);
		stage.setResizable(false);
		stage.getIcons().add(new Image("paint/Images/paint.png"));
		stage.show();
		stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::close);
	}
	
	public void close(WindowEvent event) {
		PaintController pc = new PaintController();
		pc.exitApplication(event);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
