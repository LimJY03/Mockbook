/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//NOT FINALIZED YET, STILL CANT BE USED
/**
 *
 * @author mhmdd
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendshipManager {
    Connection connection;

    public FriendshipManager(Connection connection) {
        this.connection = connection;
    }

    public void createFriendConnectionByUsername(String username1, String username2) {
        try {
            // Retrieve user IDs based on the usernames
            int userId1 = getUserIdByUsername(username1);
            int userId2 = getUserIdByUsername(username2);

            if (userId1 != -1 && userId2 != -1) {
                // Create the friend connection
                createFriendConnection(userId1, userId2);
                System.out.println("Friend connection created successfully!");
            } else {
                System.out.println("One or both usernames do not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getUserIdByUsername(String username) throws SQLException {
        String sql = "SELECT id FROM account_data WHERE username = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("id");
        } else {
            return -1; // User not found
        }
    }

    private void createFriendConnection(int userId1, int userId2) throws SQLException {
        String sql = "INSERT INTO friendship (user_id1, user_id2) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userId1);
        statement.setInt(2, userId2);
        statement.executeUpdate();
    }
}
