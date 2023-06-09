package assignmentds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class FriendshipManager {
    private Connection connection;

    public FriendshipManager(Connection connection) {
        this.connection = connection;
    }

    public void createRandomFriendConnection() {
        try {
            String username1 = getRandomUsername();
            String username2;

            int userId1 = getUserIdByUsername(username1);
            int userId2;

            do {
                username2 = getRandomUsername();
                userId2 = getUserIdByUsername(username2);
            } while (userId1 == userId2);

            System.out.println("Creating random friend connection between: " + username1 + " and " + username2);

            System.out.println("User ID for " + username1 + ": " + userId1);
            System.out.println("User ID for " + username2 + ": " + userId2);

            if (userId1 != -1 && userId2 != -1) {
                createFriendConnection(userId1, userId2);
                System.out.println("Friend connection created successfully!");
                System.out.println("Usernames: " + username1 + ", " + username2);
            } else {
                System.out.println("One or both usernames do not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getRandomUsername() throws SQLException {
        String sql = "SELECT username FROM account_data ORDER BY RAND() LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString("username");
            }
        }
        return ""; // No username found
    }

    private int getUserIdByUsername(String username) throws SQLException {
        String sql = "SELECT id FROM account_data WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        }
        return -1; // User not found
    }

    private void createFriendConnection(int userId1, int userId2) throws SQLException {
        String sql = "INSERT INTO friendship (userId1, userId2) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId1);
            statement.setInt(2, userId2);
            statement.executeUpdate();
        }
    }

    public ArrayList<String> getMutualRandomFriends() {
        try {
            String username1 = getRandomUsername();
            String username2 = getRandomUsername();

            System.out.println("Finding mutual friends between: " + username1 + " and " + username2);

            int userId1 = getUserIdByUsername(username1);
            int userId2 = getUserIdByUsername(username2);

            System.out.println("User ID for " + username1 + ": " + userId1);
            System.out.println("User ID for " + username2 + ": " + userId2);

            if (userId1 != -1 && userId2 != -1) {
                username1 = getUsernameById(userId1);
                username2 = getUsernameById(userId2);

                ArrayList<String> friendList1 = getFriendList(username1);
                ArrayList<String> friendList2 = getFriendList(username2);

                System.out.println("Friend List for " + username1 + ": " + friendList1);
                System.out.println("Friend List for " + username2 + ": " + friendList2);

                ArrayList<String> mutualFriends = getMutualFriends(friendList1, friendList2);
                System.out.println("Mutual friends: " + mutualFriends);
                System.out.println("************************************************************************");
                return mutualFriends;
            } else {
                System.out.println("One or both usernames do not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(); // Return an empty list if there's an error
    }

    ArrayList<String> getFriendList(String username) throws SQLException {
        ArrayList<String> friendList = new ArrayList<>();
        String sql = "SELECT username FROM account_data WHERE id IN " +
                "(SELECT userId2 FROM friendship WHERE userId1 = " +
                "(SELECT id FROM account_data WHERE username = ?))";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String friendUsername = resultSet.getString("username");
                    friendList.add(friendUsername);
                }
            }
        }
        return friendList;
    }

    private ArrayList<String> getMutualFriends(ArrayList<String> list1, ArrayList<String> list2) {
        ArrayList<String> mutualFriends = new ArrayList<>(list1);
        mutualFriends.retainAll(list2);
        Collections.sort(mutualFriends);
        return mutualFriends;
    }

    private String getUsernameById(int userId) throws SQLException {
        String sql = "SELECT username FROM account_data WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("username");
                }
            }
        }
        return null; // User not found
    }
}
