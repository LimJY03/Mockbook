package assignmentds;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Scanner;

public class AccountFinder {

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
                    System.out.println("\nSearch Options:");
                    System.out.println("1. Username");
                    System.out.println("2. Email");
                    System.out.println("3. Age");
                    System.out.println("4. Contact");
                    System.out.println("5. Gender");
                    System.out.println("6. Name");
                    System.out.println("0. Exit");
                    System.out.print("Enter your option to search for Users: ");
                    String option = scanner.nextLine().trim();

                    switch (option) {
                        case "0":
                            System.out.println("Exit successfully");
                            return;
                        case "1":
                        case "2":
                        case "3":
                        case "4":
                        case "5":
                        case "6":
                            System.out.print("Enter the full name: ");
                            String fullName = scanner.nextLine().trim();
                            performSearch(option, fullName, statement);
                            break;
                        default:
                            System.out.println("\nInvalid option. Re-enter an option\n");
                            break;
                    }
                }
            } else {
                System.out.println("Exit successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void performSearch(String searchType, String searchTerm, Statement statement) {
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
                    int age = Integer.parseInt(searchTerm);
                    LocalDate dateXYearsAgo = LocalDate.now().minusYears(age);
                    query = "SELECT * FROM account_data WHERE birthday <= '" + dateXYearsAgo + "'";
                    break;
                case "4":
                    query = "SELECT * FROM account_data WHERE contact_number = '" + searchTerm + "'";
                    break;
                case "5":
                    query = "SELECT * FROM account_data WHERE gender = '" + searchTerm + "'";
                    break;
                case "6":
                    query = "SELECT * FROM account_data WHERE name = '" + searchTerm + "'";
                    break;
                default:
                    System.out.println("Invalid search type.");
                    return;
            }

            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                System.out.println("\nAccount Information:");
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Username: " + resultSet.getString("username"));
                System.out.println("Email Address: " + resultSet.getString("email"));
                System.out.println("Contact Number: " + resultSet.getString("contact_number"));
                System.out.println("Birthday: " + resultSet.getDate("birthday"));
                LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
                int age = Period.between(birthday, LocalDate.now()).getYears();
                System.out.println("Age: " + age);
                System.out.println("Address: " + resultSet.getString("address"));
                System.out.println("Gender: " + resultSet.getString("gender"));
                System.out.println("Number of Friends: " + resultSet.getInt("num_friends"));
                System.out.println("Hobbies: " + Arrays.asList(resultSet.getString("hobbies").split(", ")));
                System.out.println("Jobs: " + Arrays.asList(resultSet.getString("jobs").split(", ")));
            } else {
                System.out.println("\nNo accounts found with the provided " + getSearchTypeName(searchType) + ".");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for age. Please enter a valid number.");
        }
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
