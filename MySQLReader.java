/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentds;

/**
 *
 * @author mhmdd
 */
import java.sql.*;

public class MySQLReader {
    
    private String url;
    private String username;
    private String password;

    public MySQLReader(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void readData(String query) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String column1Value = resultSet.getString("column1");
                int column2Value = resultSet.getInt("column2");

                System.out.println("Column 1: " + column1Value);
                System.out.println("Column 2: " + column2Value);
                System.out.println("-----------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
