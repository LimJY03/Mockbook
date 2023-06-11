package AdminGUI;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

public class MainApplication extends Application implements Runnable{

	public static MainApplication mainApp;
	public static Stage stage;
	private static double xOffSet = 0,yOffSet = 0;
	

	
	public void start(Stage primaryStage)
	{

		stage= primaryStage;
		changeRoot("AdminLoginGUI.fxml");
		stage.initStyle(StageStyle.UNDECORATED);
		stage.centerOnScreen();
		stage.show();
	}

	
	public static void changeRoot(String fileName)
	{
		try
		{
			Parent root = FXMLLoader.load(MainApplication.class.getResource(fileName));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(MainApplication.class.getResource("style.css").toExternalForm());
			
			
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
			
			
			stage.setScene(scene);

		}
		
		catch(IOException e)
		{
			e.printStackTrace();
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

	@Override
	public void run() {
		
//		stage.show();
		Platform.runLater(()->{
	        Platform.exit();

			System.exit(0);
		});
	}

}
