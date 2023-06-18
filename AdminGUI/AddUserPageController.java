package AdminGUI;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import Email.SendEmail;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;

import MainProgram.MainProgram;

public class AddUserPageController implements Initializable {

	@FXML
	private Text userContactLabel;

	@FXML
	private Text userEmailLabel;

	@FXML
	private Text userPasswordLabel;

	@FXML
	private Text usernameLabel;

	@FXML
	private TextField usernameText;

	@FXML
	private TextField userContactText;

	@FXML
	private TextField userEmailText;

	@FXML
	private PasswordField userPasswordText;

	@FXML
	private Button confirmButton;

	@FXML
	private Button goBackButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		goBackButton.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				MainApplication.changeRoot("newAdminPanel.fxml");
			}
		});

		confirmButton.setOnMouseClicked(e -> {
			String username = usernameText.getText();
			String userEmail = userEmailText.getText();
			String userContact = userContactText.getText();
			String userPassword = userPasswordText.getText();


			// Clear previous error messages
			usernameLabel.setText("");
			userEmailLabel.setText("");
			userContactLabel.setText("");
			userPasswordLabel.setText("");

			boolean isValidUsername = isValidUsername(username);
			boolean isValidEmail = isValidEmail(userEmail);
			boolean isValidContact = isValidPhoneNumber(userContact);
			boolean isValidPassword = isValidPassword(userPassword);

			if (isValidUsername&&isValidEmail&&isValidContact&&isValidPassword) {
				int rowAffected = AdminLoginController.admin.guiAddUser(username, userEmail, userContact, userPassword);

				if (rowAffected > 0) {

					MainApplication.generateAlert("Information", "Success", "User added Successfully",
							"" + "Please kindly refresh the database");
					
					String emailSubject = "Added you to MockBook Application";
					String emailText = 
							String.format("MockBook admin have added you to their application."
									+ "Please kindly login with\nUsername: %s\nPassword: %s\n",username,userPassword);
					
					SendEmail.sendEmail(userEmail,emailSubject,emailText);

				} else
					MainApplication.generateAlert("Error", "Error", "Failed to add user",
							"Please contact technician or try again");
			}

			usernameText.clear();
			userEmailText.clear();
			userContactText.clear();
			userPasswordText.clear();
		});

	}

	private boolean isValidUsername(String Username) {

		boolean isValid = true;
		final Pattern textPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z]).+$");

		if (MainProgram.db.searchTable("Username", Username)) {
			usernameLabel.setText("Username has been taken");
			isValid = false;
		}

		else {

			if (Username.length() > 25 || Username.length() < 5) {
				usernameLabel.setText("Must less than 25 and more than 5 characters.");
				isValid = false;
			}
			// Check Upper Case Match
			if (!textPattern.matcher(Username).matches()) {
				usernameLabel.setText("Must have atleast one uppercase/ one lowercase character");
				isValid = false;
			}if (Username.contains(" ")) {
				usernameLabel.setText("Username Should NOT Contain Spaces!");
				isValid = false;
			}

		}

		return isValid;
	}

	private boolean isValidEmail(String email) {

		if (email.isEmpty()) {
			userEmailLabel.setText("Email can't be empty");
			return false;
		} else {
			
			if (MainProgram.db.searchTable("Email", userEmailText.getText())) {
				userEmailLabel.setText("Email already exist");
				return false;
			}
			else
			{
				String regex = "[\\w-\\.+]*[\\w-\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

				boolean result = email.matches(regex);
				if (!result)
					userEmailLabel.setText("Invalid format");
				
				return result;
			}
		}
	}

	private boolean isValidPassword(String password) {

		if (password.isEmpty()) {
			userPasswordLabel.setText("Password can't be empty");
			return false;
		}
		if (password.length() > 20 || password.length() < 8) {
			userPasswordLabel.setText("Must less than 20 and more than 8 characters.");
			return false;
		}

		// Check Upper Case Match
		if (!password.matches("(.*[A-Z].*)")) {
			userPasswordLabel.setText("Must have atleast one uppercase character");
			return false;
		}

		// Check Lower Case Match
		if (!password.matches("(.*[a-z].*)")) {
			userPasswordLabel.setText("Must have atleast one lowercase character");
			return false;
		}

		// Check Number Match
		if (!password.matches("(.*[0-9].*)")) {
			userPasswordLabel.setText("Must have atleast one number");
			return false;
		}

		// Check Special Characters Match
		// if (!password.matches("(.*[@,#,$,%].*$)")) {
		if (!password.matches("(.*[~,!,@,#,$,%,^,&,*,_,-,+,=,`,|,(,),{,},[,],:,;,\",\',<,>,,,.,?,/].*$)")) {
			userPasswordLabel.setText("Must have atleast one special character among @#$%");
			return false;
		}
		return true;

	}

	private boolean isValidPhoneNumber(String number) {

		String regex = "\\d{10,11}";

		if (!number.matches(regex)) {
			userContactLabel.setText("Invalid input");
			return false;
		}

		if (MainProgram.db.searchTable("PhoneNumber", number)) {
			userContactLabel.setText("Phone number already exist");
			return false;
		}

		return true;
	}

}
