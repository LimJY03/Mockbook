package assignmentds;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AccountFinder {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Atsukomajitenshi.27";

    private String previousSearchOption = "";

    public void findAccount() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            String viewOption = "";

            do {
                System.out.print("Do you want to view an account? (y/n): ");
                viewOption = scanner.nextLine().trim().toLowerCase();

                if (viewOption.equals("y") || viewOption.equalsIgnoreCase("yes")) {
                    while (true) {
                        if (previousSearchOption.isEmpty()) {
                            printSearchOptions();
                        } else {
                            System.out.println("\nPrevious search option: " + previousSearchOption);
                        }

                        System.out.print("Enter your option to search for users (or '0' to exit): ");
                        String option = scanner.nextLine().trim();

                        if (option.equals("0")) {
                            System.out.println("Exit successfully");
                            return;
                        } else if (isValidSearchOption(option)) {
                            previousSearchOption = option;
                            System.out.print("Enter the search term: ");
                            String searchTerm = scanner.nextLine().trim();
                            System.out.print("Enter the connection degree (1, 2, or 3): ");
                            int connectionDegree = Integer.parseInt(scanner.nextLine().trim());
                            performSearch(option, searchTerm, connectionDegree, connection, scanner);
                            break; // Exit the inner loop after performing the search
                        } else {
                            System.out.println("\nInvalid option. Please choose a valid search option or enter '0' to exit.");
                        }
                    }
                } else if (!viewOption.equals("n")) {
                    System.out.println("\nInvalid option. Please enter 'y' for yes or 'n' for no.");
                }
            } while (!viewOption.equals("n"));

            System.out.println("Exit successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void printSearchOptions() {
        System.out.println("\nSearch Options:");
        System.out.println("1. Username");
        System.out.println("2. Email");
        System.out.println("3. Age");
        System.out.println("4. Contact");
        System.out.println("5. Gender");
        System.out.println("6. Name");
    }

    private boolean isValidSearchOption(String option) {
        return option.matches("[1-6]");
    }

    private void performSearch(String searchType, String searchTerm, int connectionDegree, Connection connection, Scanner scanner) {
        try (Statement statement = connection.createStatement()) {
            String query = "";
            switch (searchType) {
                case "1":
                    query = "SELECT * FROM account_data WHERE Username = ?";
                    break;
                case "2":
                    query = "SELECT * FROM account_data WHERE Email = ?";
                    break;
                case "3":
                    query = "SELECT * FROM account_data WHERE Age = ?";
                    break;
                case "4":
                    query = "SELECT * FROM account_data WHERE Contact = ?";
                    break;
                case "5":
                    query = "SELECT * FROM account_data WHERE Gender = ?";
                    break;
                case "6":
                    query = "SELECT * FROM account_data WHERE Name LIKE ?";
                    searchTerm = "%" + searchTerm + "%";
                    break;
                default:
                    System.out.println("Invalid search type.");
                    return;
            }

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, searchTerm);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> matchingNames = new ArrayList<>();

            while (resultSet.next()) {
                matchingNames.add(resultSet.getString("name"));
            }

            if (!matchingNames.isEmpty()) {
                System.out.println("\nMatching Accounts:");
                for (String name : matchingNames) {
                    System.out.println("- " + name);
                }

                System.out.print("\nEnter the name to view account information: ");
                String selectedName = scanner.nextLine().trim();

                query = "SELECT * FROM account_data WHERE Name = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, selectedName);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    System.out.println("\nAccount Information:");

                    // Retrieve the values from the result set
                    String name = resultSet.getString("Name");
                    String username = resultSet.getString("Username");
                    String gender = resultSet.getString("Gender");
                    int numberOfFriends = resultSet.getInt("Num_Friends");
                    String hobbies = resultSet.getString("Hobbies");
                    String job = resultSet.getString("Jobs");
                    String email = resultSet.getString("Email");
                    String contact = resultSet.getString("Phone_Number");
                    int age = resultSet.getInt("Age");
                    String address = resultSet.getString("Address");
                    LocalDate birthday = resultSet.getDate("Birthday").toLocalDate();
                    String p = resultSet.getString("Password");

                    // Create an instance of ViewAccountConnection
                    ViewAccountConnection viewAccountConnection = new ViewAccountConnection(
                            name, username, gender, numberOfFriends, hobbies, job, email, contact, age, address, birthday, p
                    );
                    // Call the appropriate view account method based on the connection degree
                    switch (connectionDegree) {
                        case 1:
                            viewAccountConnection.viewAccount1(connectionDegree);
                            break;
                        case 2:
                            viewAccountConnection.viewAccount2(connectionDegree);
                            break;
                        case 3:
                            viewAccountConnection.viewAccount3(connectionDegree);
                            break;
                    }

                    // Retrieve the username of the selected account
                    String selectedUsername = resultSet.getString("Username");

                    // Create an instance of FriendshipManager
                    FriendshipManager friendshipManager = new FriendshipManager(connection);

                    // Get the list of friends for the selected account
                    List<String> friendList = friendshipManager.getFriendList(selectedUsername);

                    System.out.println("Friend List:");
                    for (String friend : friendList) {
                        System.out.println("- " + friend);
                    }

                } else {
                    System.out.println("\nNo accounts found with the provided name.");
                }
            } else {
                System.out.println("\nNo accounts found with the provided search criteria.");
            }

            System.out.println("\nDo you want to view another account? (1 = yes, 0 = exit)");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();
            if (choice.equals("0")) {
                System.out.println("Exit successfully");
                return;
            } else if (!choice.equals("1")) {
                System.out.println("Invalid choice. Exit successfully");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
