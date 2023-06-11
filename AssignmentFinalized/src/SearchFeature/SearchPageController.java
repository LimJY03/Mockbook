//package SearchFeature;
//
//import java.net.URL;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ResourceBundle;
//
//import AccessControl.RegularUser;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
//import javafx.scene.control.ChoiceBox;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.input.MouseButton;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//
//public class SearchPageController implements Initializable{
//
//
//
//    @FXML
//    private ChoiceBox<String> attributeSelector;
//
//    @FXML
//    private TextField attributeValue;
//
//    @FXML
//    private Text requiredField1;
//
//    @FXML
//    private Text requiredField2;
//    
//    @FXML
//    private Text resultText;
//
//    @FXML
//    private TableView<RegularUser> searchTable;
//
//    @FXML
//    private TableColumn<RegularUser, String> usernameCol;
//    
//    @FXML
//    private TableColumn<RegularUser, String> contactCol;
//    
//    @FXML
//    private TableColumn<RegularUser, String> emailCol;
//    
//    @FXML
//    private TableColumn<RegularUser, String> genderCol;
//    
//    @FXML
//    private TableColumn<RegularUser, String> ageCol;
//    
//    @FXML
//    private Button confirmButton;
//
//    @FXML
//    private Button goBackButton;
//
//
//	@Override
//	public void initialize(URL arg0, ResourceBundle arg1) {
//
//		String selections[] = {"Username","Email","Contact","Age","Gender"};
//		attributeSelector.getItems().addAll(selections);
//		
//		goBackButton.setOnMousePressed(e->{
//			
//			if(e.getButton()==MouseButton.PRIMARY)
//			{
//				Stage stage = (Stage)goBackButton.getScene().getWindow();
//				stage.close();
//				System.exit(0);
//			}
//		});
//		
//		confirmButton.setOnMousePressed(e->{
//			
//			if(e.getButton()==MouseButton.PRIMARY)
//			{
//				requiredField1.setText("");
//				requiredField2.setText("");
//				resultText.setText("");
//								
//				if(attributeSelector.getValue()==null)
//				{
//					requiredField1.setText("Must Select an Attribute!");
//				}
//				else
//				{
//					if(attributeValue.getText()=="")
//					{
//						requiredField2.setText("Can't enter empty value!");
//					}
//					else
//					{
//						String attribute = attributeSelector.getValue();
//						String value = attributeValue.getText();
//						
//						ResultSet rs = Search.guiSearch(attribute, value);
//						
//						try {
//							if(!rs.next())
//							{
//								resultText.setText("No matching result");
//								MainApplication.generateAlert("Error", "None", "No Match Result", "Please try another value");
//							}
//							else
//							{
//								loadTable(rs,attribute);
//								MainApplication.generateAlert("Success", "Found", "Match Result Found", "Please see the table");
//							}
//						} catch (SQLException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//					}
//				}
//				attributeValue.clear();
//			}
//
//		});
//		
//	}
//	
//	private void loadTable(ResultSet rs,String attribute)
//	{
//		resultText.setText("Matching result sorted in "+attribute);
//		
//		usernameCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
//		emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
//		contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
//		
//		ageCol.setCellValueFactory(new PropertyValueFactory<>("Age"));
//		genderCol.setCellValueFactory(new PropertyValueFactory<>("Gender"));
//		
//		ObservableList<RegularUser> userList = FXCollections.observableArrayList();
//		
//		try {
//			while(rs.next())
//			{
//				String name = rs.getString("Username");
//				String email= rs.getString("Email");
//				String contact = rs.getString("Contact");
//				int age = rs.getInt("Age");
//				String gender= rs.getString("Gender");
//				String password= rs.getString("Password");
//				
//				userList.add(new RegularUser(name,email,contact,age,gender,password));
//			}
//			
//			searchTable.setItems(userList);
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//}
