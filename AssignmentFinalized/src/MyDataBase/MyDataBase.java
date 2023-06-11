package MyDataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {

//	static final String URL = "jdbc:mysql://db4free.net:3306/assignmentbase";
//	static final String USERNAME = "iamadmin";
//	static final String PASSWORD = "ADMINPASSWORD";
	
    static final String URL = "jdbc:mysql://localhost:3306/MyDatabase";
    static final String USERNAME = "root";
    static final String PASSWORD = "Khai0000.";
	
	public static Connection establishConnection() {
		
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error on establishing connection: " + e.getMessage());
        }
        return connection;
        
        
    }
	
}
