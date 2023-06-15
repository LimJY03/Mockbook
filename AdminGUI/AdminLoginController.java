 		package AdminGUI;

import java.net.URL;
import java.util.ResourceBundle;

import AccessControl.Admin;
import Registration.PasswordEncrypt;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;

public class AdminLoginController implements Initializable {

    @FXML
    private Text adminIDWrong;
    
    @FXML
    private Text passwordWrong;

    @FXML
    private TextField adminInput;

    @FXML
    private PasswordField adminPassword;

    @FXML
    private Button exitButton;
    
    @FXML
    private Button loginButton;
    
    private int chanceRemain=5;
    
    static Admin admin;
    
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
    		exitButton.setOnMouseClicked(e->{
    			if (e.getButton() == MouseButton.PRIMARY) 
    			{
    				MainApplication.stage.setOpacity(0);
    			}
    		});
    	
    		
			loginButton.setOnMouseClicked(e->{
				
				if (e.getButton() == MouseButton.PRIMARY) { // Check if left mouse button is pressed
			        callValidLogin();
			    }
			});
			
			
	    	Parent root = loginButton.getParent();
	        


			root.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
				
			    if (event.getCode() == KeyCode.ENTER) {
			        callValidLogin();
			    }
			});
		
	}
    
    
    
    private void callValidLogin()
    {
    	String adminID = adminInput.getText().trim();
        String password = adminPassword.getText().trim();
        validLogin(adminID,password);
    }
    
    
    private void validLogin(String adminID,String password)
	{
		  
        boolean validId = Admin.isValidAdminId(adminID);
        boolean validPassword = Admin.isValidPassword(adminID, PasswordEncrypt.encryptSHA256(password,adminID));
        
        if(validId&&validPassword)
        {
        	admin = new Admin(adminID,PasswordEncrypt.encryptSHA256(password,adminID));
        	MainApplication.changeRoot("newAdminPanel.fxml");
        	
        }
        
       if(!validId)
    	   adminIDWrong.setText("Invalid Admin ID !");
       else
    	   adminIDWrong.setText("");
    	   
       
       if(!validPassword)
       {
    	   if(--chanceRemain==0)
    	   {
    		   MainApplication.stage.close();
    		   MainApplication.generateAlert
	        	("Error","Error","Failed To Login","Program terminated abnormally.");
    		   
    	   }
    	   passwordWrong.setText(String.format("Invalid password! You have %d times left", chanceRemain));
       }
       else
    	   passwordWrong.setText("");
       
       
        adminInput.clear();
        adminPassword.clear();
	}

}
