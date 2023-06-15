package AdminGUI;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class MainApplication extends Application{

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
		
		TextArea textArea = new TextArea(content);
		textArea.setEditable(false);
		textArea.setWrapText(true);
		GridPane gridPane = new GridPane();
		gridPane.setMaxWidth(Double.MAX_VALUE);
		gridPane.add(textArea, 0, 0);

		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.getDialogPane().setContent(gridPane);
		alert.showAndWait();
	}


}
