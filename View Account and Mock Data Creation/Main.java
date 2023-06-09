package assignmentds;

import java.time.LocalDate;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "Atsukomajitenshi.27";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            FriendshipManager friendshipManager = new FriendshipManager(connection);

            Scanner scanner = new Scanner(System.in);

            System.out.println("Choose an option:");
            System.out.println("1. Create random friend connection");
            System.out.println("2. Create mock user");
            System.out.println("3. View account");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    try {
                        // Create a random friend connection
                        friendshipManager.createRandomFriendConnection();

                        // Get mutual random friends
                        friendshipManager.getMutualRandomFriends();

                        // Close the database connection
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    User mockUser = MockDataCreator.createMockUser();
                    mockUser.viewAccount();
                    MySQLStorage.saveUser(mockUser);
                    break;
                case 3:
                    AccountFinder accountFinder = new AccountFinder();
                    accountFinder.findAccount();
                    break;
                default:
                    System.out.println("Invalid choice");
            }

            // Close the database connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
