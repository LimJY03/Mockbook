import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AccountFinder {
    private String previousSearchOption = "";

    public void findAccount() {
        // JDBC connection parameters
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "Atsukomajitenshi.27";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Do you want to view an account? (yes/no): ");
            String viewOption = scanner.nextLine().trim().toLowerCase();

            if (viewOption.equals("yes")) {
                while (true) {
                    if (previousSearchOption.isEmpty()) {
                        printSearchOptions();
                    } else {
                        System.out.println("\nPrevious search option: " + previousSearchOption);
                    }

                    System.out.print("Enter your option to search for Users (or '0' to exit): ");
                    String option = scanner.nextLine().trim();

                    if (option.equals("0")) {
                        System.out.println("Exit successfully");
                        return;
                    } else if (isValidSearchOption(option)) {
                        previousSearchOption = option;
                        System.out.print("Enter the search term: ");
                        String searchTerm = scanner.nextLine().trim();
                        performSearch(option, searchTerm, statement, scanner);
                    } else {
                        System.out.println("\nInvalid option. Please choose a valid search option or enter '0' to exit.");
                    }
                }
            } else {
                System.out.println("Exit successfully");
            }
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

    private void performSearch(String searchType, String searchTerm, Statement statement, Scanner scanner) {
    try {
        String query = "";
        switch (searchType) {
            case "1":
                query = "SELECT * FROM account_data WHERE username = '" + searchTerm + "'";
                break;
            case "2":
                query = "SELECT * FROM account_data WHERE email = '" + searchTerm + "'";
                break;
            case "3":
                query = "SELECT * FROM account_data WHERE age = " + searchTerm;
                break;
            case "4":
                query = "SELECT * FROM account_data WHERE contact_number = '" + searchTerm + "'";
                break;
            case "5":
                query = "SELECT * FROM account_data WHERE gender = '" + searchTerm + "'";
                break;
            case "6":
                query = "SELECT * FROM account_data WHERE name LIKE '%" + searchTerm + "%'";
                break;
            default:
                System.out.println("Invalid search type.");
                return;
        }

        ResultSet resultSet = statement.executeQuery(query);
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

            query = "SELECT * FROM account_data WHERE name = '" + selectedName + "'";
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                System.out.println("\nAccount Information:");
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Username: " + resultSet.getString("username"));
                System.out.println("Email Address: " + resultSet.getString("email"));
                System.out.println("Contact Number: " + resultSet.getString("contact_number"));
                System.out.println("Birthday: " + resultSet.getDate("birthday").toLocalDate());
                LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
                int age = Period.between(birthday, LocalDate.now()).getYears();
                System.out.println("Age: " + age);
                System.out.println("Address: " + resultSet.getString("address"));
                System.out.println("Gender: " + resultSet.getString("gender"));
                System.out.println("Number of Friends: " + resultSet.getInt("num_friends"));
                System.out.println("Hobbies: " + Arrays.asList(resultSet.getString("hobbies").split(", ")));
                System.out.println("Jobs: " + Arrays.asList(resultSet.getString("jobs").split(", ")));
                
                // Retrieve the username of the selected account
                String selectedUsername = resultSet.getString("username");

                // Create an instance of FriendshipManager
                FriendshipManager friendshipManager = new FriendshipManager(statement.getConnection());

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
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (NumberFormatException e) {
        System.out.println("Invalid input for age. Please enter a valid number.");
    }
}

    private void recommendJobs(String jobs) {
        // Recommendation system implementation goes here
        System.out.println("Recommended Jobs: [Job1, Job2, Job3]");
    }

    private String getSearchTypeName(String searchType) {
        switch (searchType) {
            case "1":
                return "Username";
            case "2":
                return "Email";
            case "3":
                return "Age";
            case "4":
                return "Contact";
            case "5":
                return "Gender";
            case "6":
                return "Name";
            default:
                return "Unknown";
        }
    }
}

