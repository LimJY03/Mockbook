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

import Email.SendEmail;
import AccessControl.Admin;
import MyHashMap.MyHashMap;
import Registration.Login;
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

		confirmButton.setOnMouseClicked(e -> {
			requiredField.setText("");
			requiredField1.setText("");
			requiredField2.setText("");
			requiredField3.setText("");

			if (usernameField.getText() == "")
				requiredField.setText("This field is required!");
			else if(Login.getPrivateKey(usernameField.getText())==null)
				requiredField.setText("Must wait until the user obtained the private key");
			else {
				String username = usernameField.getText();

				MyHashMap<String, String> map = new MyHashMap<>();

				if (emailTickBox.isSelected()) {
					if (isValidEmail(emailField.getText()))
						map.put("Email", emailField.getText());
				}

				if (contactTickBox.isSelected()) {
					if (isValidPhoneNumber(contactField.getText()))
						map.put("PhoneNumber", contactField.getText());
				}

				if (newUsernameTickBox.isSelected()) {
					if (isValidUsername(newUsernameField.getText()))
						map.put("Username", newUsernameField.getText());
				}

				if (!map.isEmpty()) {
					String originalUserEmail = Admin.getEmail("Username", username);
					int rowAffected = AdminLoginController.admin.guiUpdateUser(username, map);

					if (rowAffected > 0) {
						MainApplication.generateAlert("Success", "Success", "User updated Successfully",
								"Please kindly refresh the database");
						String alteredUserEmail = map.get("Email") == null ? originalUserEmail : map.get("Email");

						if (map.keySet().contains("Username") || map.keySet().contains("Email")) {

							if (map.keySet().contains("Username")) {
								String emailSubject = "Updated Username in MockBook";
								String emailText = "The MockBook Admin have changed your username\n"
										+ "Please kindly login with new username: " + map.get("Username")
										+ "\nPlease reset your password using the previously obtained private key otherwise you will not able to login.";

								SendEmail.sendEmail(alteredUserEmail, emailSubject, emailText);
							}

							if (map.keySet().contains("Email")) {
								String emailSubject = "Updated Username in MockBook";
								String emailText = "The MockBook Admin have changed your email.\n" + "Previous email: "
										+ originalUserEmail + "\nCurrent Email: " + map.get("Email")
										+ "\nPlease reply to this email if there is any issue";

								SendEmail.sendEmail(originalUserEmail, emailSubject, emailText);
								SendEmail.sendEmail(alteredUserEmail, emailSubject, emailText);
							}
						}

						String emailSubject = "Updated attributed in MockBook";
						String emailText = "The MockBook Admin have changed your account attributes : \n";
						for (String attribute : map.keySet())
							emailText += attribute + " : " + map.get(attribute);
						emailText += "Please kindly look through it and contact us if any issue.";

						alteredUserEmail = map.get("Email") == null ? originalUserEmail : map.get("Email");
						SendEmail.sendEmail(alteredUserEmail, emailSubject, emailText);

					} else
						MainApplication.generateAlert("Error", "Failed", "User not found",
								"Please try in a valid username");
				} else {
					MainApplication.generateAlert("Nothing", "None", "Nothing to update",
							"Please kindly refresh the database");

				}

				contactField.clear();
				usernameField.clear();
				newUsernameField.clear();
			}

		});

		goBackButton.setOnMouseClicked(e -> {

			if (e.getButton() == MouseButton.PRIMARY) {
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
				isValid = isValid && false;
			}
			// Check Upper Case Match
			if (!textPattern.matcher(Username).matches()) {
				requiredField3.setText("Must have atleast one uppercase/ one lowercase character");
				isValid = isValid && false;
			}
			if (Username.contains(" ")) {
				requiredField3.setText("Username Should NOT Contain Spaces!");
				isValid = isValid && false;
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
				requiredField1.setText("This field can't be alter !!!!!");

			return result;

		}
	}

	private boolean isValidPhoneNumber(String number) {

		String regex = "\\d{10,11}";

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
