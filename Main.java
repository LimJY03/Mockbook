package assignmentds;

import java.time.LocalDate;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "Atsukomajitenshi.27";
        
        /**try {
            Connection connection = DriverManager.getConnection(url, username, password);
            FriendshipManager friendshipManager = new FriendshipManager(connection);

            // Example usage of createFriendConnectionByUsername
            friendshipManager.createFriendConnectionByUsername("John", "Jane");

            // Close the database connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } **/
        
        System.out.println("*******************************************************");
        
        User mockUser = MockDataCreator.createMockUser();

        mockUser.viewAccount();
        
        MySQLStorage.saveUser(mockUser);

        
        
        System.out.println("*******************************************************");

        AccountFinder accountFinder = new AccountFinder();
        accountFinder.findAccount();

    }
}
