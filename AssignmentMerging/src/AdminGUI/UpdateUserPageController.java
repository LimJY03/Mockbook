package AdminGUI;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;

import MyHashMap.MyHashMap;
import MainProgram.MainProgram;

public class UpdateUserPageController implements Initializable {


    @FXML
    private TextField contactField;

    @FXML
    private TextField emailField;
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private TextField newUsernameField;

    @FXML
    private CheckBox newUsernameTickBox;
    
    @FXML
    private CheckBox contactTickBox;

    @FXML
    private CheckBox emailTickBox;

    @FXML
    private CheckBox usernameTickBox;    

    @FXML
    private Button goBackButton;
    
    @FXML
    private Button confirmButton;
    
    @FXML
    private Text requiredField;
    
    @FXML
    private Text requiredField1;
    
    @FXML
    private Text requiredField2;
    
    @FXML
    private Text requiredField3;
    
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		usernameField.isFocused();
		usernameTickBox.setMouseTransparent(true);
		usernameTickBox.setSelected(true);
		
		confirmButton.setOnMouseClicked(e->
		{
			requiredField.setText("");
			requiredField1.setText("");
			requiredField2.setText("");
			requiredField3.setText("");
			
			if(usernameField.getText()=="")
				requiredField.setText("This field is required!");
			else
			{
				String username = usernameField.getText();
				
				MyHashMap<String,String>map = new MyHashMap<>();
				
				if(emailTickBox.isSelected())
				{
					if(isValidEmail(emailField.getText()) )
						map.put("Email", emailField.getText());
				}
				
				if(contactTickBox.isSelected())
				{
					if(isValidPhoneNumber(contactField.getText()) )
						map.put("Contact", contactField.getText());
				}
				 
				if(newUsernameTickBox.isSelected())
				{
					if(isValidUsername(newUsernameField.getText()))
						map.put("Username", newUsernameField.getText());
				}
				
				
				if(!map.isEmpty())
				{
					
					int rowAffected = AdminLoginController.admin.guiUpdateUser(username,map);
					
					if(rowAffected>0)
						MainApplication.generateAlert
						("Success","Success","User updated Successfully","Please kindly refresh the database");
					else
						MainApplication.generateAlert
						("Error","Failed","User not found","Please try in a valid username");
				}
				else
				{
					MainApplication.generateAlert
					("Nothing","None","Nothing to update","Please kindly refresh the database");

				}
				
				contactField.clear();
				usernameField.clear();
				newUsernameField.clear();
				emailField.clear();
			}
				
		});
		
		
		goBackButton.setOnMouseClicked(e->{
			
			if(e.getButton()==MouseButton.PRIMARY)
			{
				MainApplication.changeRoot("newAdminPanel.fxml");
			}
		});
	}
	
	
	
	private boolean isValidUsername(String Username) {

		boolean isValid = true;
		final Pattern textPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z]).+$");

		if (MainProgram.db.searchTable("Username", Username)) {
			requiredField3.setText("Username has been taken");
			isValid = false;
		}

		else {

			if (Username.length() > 25 || Username.length() < 5) {
				requiredField3.setText("Must less than 25 and more than 5 characters.");
				isValid = false;
			}
			// Check Upper Case Match
			else if (!textPattern.matcher(Username).matches()) {
				requiredField3.setText("Must have atleast one uppercase/ one lowercase character");
				isValid = false;
			}

		}

		return isValid;
	}

	private boolean isValidEmail(String email) {

		if (email.isEmpty()) {
			requiredField1.setText("Email can't be empty");
			return false;
		} else {
			String regex = "[\\w-\\.+]*[\\w-\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

			boolean result = email.matches(regex);
			if (!result)
				requiredField1.setText("Invalid format");

			return result;

		}
	}

	private boolean isValidPhoneNumber(String number) {

		String regex = "\\d{10}";

		if (!number.matches(regex)) {
			requiredField2.setText("Invalid input");
			return false;
		}

		if (MainProgram.db.searchTable("PhoneNumber", number)) {
			requiredField2.setText("Phone number already exist");
			return false;
		}

		return true;
	}
}
