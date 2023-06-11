package AdminGUI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;

public class DeleteUserPageController implements Initializable {
	
	@FXML
	private Text errorText;
	
    @FXML
    private TextField attributeValue;

    @FXML
    private ChoiceBox<String> userAttributesSelector;

    @FXML
    private Button confirmButton;
    
    @FXML
    private Button goBackButton;
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		String choices[]= {"Username","Email","Contact"};
		userAttributesSelector.getItems().addAll(choices);
		
		
		confirmButton.setOnMouseClicked(e->
		{
			if(e.getButton()==MouseButton.PRIMARY)
			{
				errorText.setText("");
				
				String selectedAttribute = userAttributesSelector.getValue();
				if(selectedAttribute==null)
				{
					errorText.setText("Attribute can't be null!");
				}
				else
				{
					String value = attributeValue.getText();
					int rowAffected = AdminLoginController.admin.guiDeleteUser(selectedAttribute,value);
					
					if(rowAffected>0)
					{
						MainApplication.generateAlert
						("Success","Success","User Deleted Successfully","User is deleted. Please kindly refresh the table");
					}
					else
					{
						MainApplication.generateAlert
						("Error","Failed","Fail to Delete User","User not found");
					}
				}
				attributeValue.clear();
			}
		});
		
		
		goBackButton.setOnMouseClicked(e->{
			
			if(e.getButton()==MouseButton.PRIMARY)
			{
				MainApplication.changeRoot("newAdminPanel.fxml");
			}
		});
		
	}

}
