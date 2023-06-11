package AdminGUI;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import AccessControl.RegularUser;
import MainProgram.MainProgram;


public class AdminPanelController implements Initializable {
	
	@FXML
    private TableView<RegularUser> userTable;	

    @FXML
    private TableColumn<RegularUser, String> usernameCol;

    @FXML
    private TableColumn<RegularUser, String> emailCol;
    
    @FXML
    private TableColumn<RegularUser, Integer> ageCol;

    @FXML
    private TableColumn<RegularUser, String> contactCol;

    @FXML
    private TableColumn<RegularUser, String> genderCol;

    @FXML
    private Button logoutButton;
    
    @FXML
    private Button addUserButton;
    
    @FXML
    private Button refreshButton;
    
    @FXML
    private Button deleteUserButton;
    
    @FXML
    private Button updateUserButton;
    
    ObservableList<RegularUser> userList = FXCollections.observableArrayList();


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		loadTable();

		userTable.setRowFactory(uT -> {
			TableRow<RegularUser> row = new TableRow<>();
			row.setMouseTransparent(true); // Disable mouse interaction for the row
			return row;
		});

		setButtonHandler(logoutButton, "AdminLoginGUI.fxml");
		setButtonHandler(addUserButton, "AddUserPage.fxml");
		setButtonHandler(deleteUserButton, "DeleteUserPage.fxml");
		setButtonHandler(updateUserButton, "UpdateUserPage.fxml");

		
		refreshButton.setOnMouseClicked(e->{
			if(e.getButton()==MouseButton.PRIMARY)
			{
				refresh();
			}
		});

	}
	
	
	
	private void setButtonHandler(Button button, String fxmlFile) {
	    button.setOnMouseClicked(e -> {
	        if (e.getButton() == MouseButton.PRIMARY) {
	            MainApplication.changeRoot(fxmlFile);
	        }
	    });
	}
	
	
	
	
	private void loadTable() {
	    retrieveData();
	    setupTableColumns();
	}

	
	
	private void refresh() {
	    retrieveData();
	}

	
	
	private void setupTableColumns() {
	    emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
	    usernameCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
	    contactCol.setCellValueFactory(new PropertyValueFactory<>("Contact"));
	    ageCol.setCellValueFactory(new PropertyValueFactory<>("Age"));
	    genderCol.setCellValueFactory(new PropertyValueFactory<>("Gender"));
	}
	
	
	
	private void retrieveData() {
	    userList.clear();


	    try {
	        String query = "SELECT * FROM User";
	        PreparedStatement stmt = MainProgram.connection.prepareStatement(query);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            userList.add(new RegularUser(
	                    rs.getString("Username"),
	                    rs.getString("Email"),
	                    rs.getString("Contact"),
	                    rs.getInt("Age"),
	                    rs.getString("Gender"),
	                    rs.getString("Password")
	            ));
	        }

	        userTable.setItems(userList);

	        stmt.close();
	        rs.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
		
		
}
