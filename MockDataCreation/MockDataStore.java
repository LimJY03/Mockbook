package MockDataCreation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import AccessControl.RegularUser;
import MainProgram.MainProgram;
import Registration.PasswordEncrypt;
import Registration.PrivateKey;

public class MockDataStore {


    public static void saveUser(RegularUser account) {
        try  
        {
        	Connection connection = MainProgram.connection;
            String newId = account.getUsername();

            // Check if the ID already exists in the table
            // Insert the new record
            
            while(!idExists(newId))
            {
                String firstName = MockDataCreator.getRandomElement(MockDataCreator.NAMES1);
                String lastName = MockDataCreator.getRandomElement(MockDataCreator.NAMES2);
                String name = firstName + " " + lastName;
                
                newId = MockDataCreator.generateUsername(name);
            	account.setUsername(name);
            	
            }
            
            String query = "INSERT INTO User (Username,Email,PhoneNumber,Birthday, Age, Address, Gender,Hobbies, Job, Password) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            System.out.println(account.getBirthday());
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, account.getUsername());
                statement.setString(2, account.getEmail());
                statement.setString(3, account.getContact());
                statement.setString(4, account.getBirthday().toString());
                statement.setInt(5, account.calculateAge());
                statement.setString(6, account.getAddress());
                statement.setString(7, account.getGender());
                statement.setString(8, account.getHobbies());
                statement.setString(9, account.getJob());
                statement.setString(10, PasswordEncrypt.encryptSHA256(account.getPassword(), account.getUsername()));
                statement.executeUpdate();
                
                
                System.out.println("User saved successfully with Username: " + newId);
                System.out.println("User password is :"+account.getPassword());
                PrivateKey.createPrivateKey(account.getUsername());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean idExists(String username) throws SQLException {
        String query = "SELECT * FROM User WHERE Username = ?";
        try {
        	PreparedStatement statement = MainProgram.connection.prepareStatement(query);
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return false;
                }
            }
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        }
        return true;
    }

}
