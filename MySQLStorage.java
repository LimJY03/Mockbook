import java.sql.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MySQLStorage {
    private static final String URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Atsukomajitenshi.27";

    public static void saveUser(User account) {
    try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
        int newId = account.getId();
        
        // Check if the ID already exists in the table
        while (idExists(connection, newId)) {
            newId++; // Increment the ID until a unique ID is found
        }

        // Insert the new record
        String query = "INSERT INTO account_data (id, name, username, email, contact_number, birthday, age, address, gender, num_friends, hobbies, jobs) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, newId);
            statement.setString(2, account.getName());
            statement.setString(3, account.getUsername());
            statement.setString(4, account.getEmail());
            statement.setString(5, account.getContactNumber());
            statement.setInt(7, account.calculateAge()); 
            statement.setString(6, account.getBirthday().toString());
            statement.setString(8, account.getAddress());
            statement.setString(9, account.getGender());
            statement.setInt(10, account.getNumberOfFriends());
            statement.setString(11, listToString(account.getHobbies()));
            statement.setString(12, listToString(account.getJobs()));

            statement.executeUpdate();
            System.out.println("User saved successfully with ID: " + newId);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    private static boolean idExists(Connection connection, int id) throws SQLException {
        String query = "SELECT COUNT(*) FROM account_data WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

    private static String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String item : list) {
            sb.append(item).append(", ");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }
}
