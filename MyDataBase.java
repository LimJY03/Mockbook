import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {

	static final String URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12617775";
	static final String USERNAME = "sql12617775";
	static final String PASSWORD = "DDcbsswPnf";
	
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
