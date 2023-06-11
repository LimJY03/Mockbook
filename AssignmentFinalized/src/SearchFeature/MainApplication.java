package SearchFeature;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

public class MainApplication extends Application implements Initializable {

	private double xOffSet = 0, yOffSet = 0;


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void start(Stage stage) {
		
		stage.initStyle(StageStyle.UNDECORATED);
		settingStage("SearchPageController.fxml",stage);
		stage.show();

	}
	
	public void settingStage(String fileName,Stage stage)
	{
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fileName));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			stage.setScene(scene);
			
			

			root.setOnMousePressed(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {

					xOffSet = event.getSceneX();
					yOffSet = event.getSceneY();
				}

			});

			root.setOnMouseDragged(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {

					stage.setX(event.getScreenX() - xOffSet);
					stage.setY(event.getScreenY() - yOffSet);
				}

			});
			
			
		} catch (IOException error) {
			error.printStackTrace();
		}
	}
	
	
	public static void generateAlert(String type,String title,String header,String content)
	{
		Alert alert;
		if(type.equals("Error"))
			alert = new Alert(Alert.AlertType.ERROR);
		else
			alert = new Alert(Alert.AlertType.INFORMATION);
		
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
	}
	
}
