package MockDataCreation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import AccessControl.Admin;
import AccessControl.RegularUser;
import MainProgram.MainProgram;
import Registration.PasswordEncrypt;
import Registration.PrivateKey;

public class MockDataStore {

    public static void saveUser(RegularUser account,boolean toPopulateMock) {
        try  
        {
        	Connection connection = MainProgram.connection;
            String newId = account.getUsername();

            // Check if the ID already exists in the table
            // Insert the new record
            
            while(!idExists(newId)&&!adminExists(newId))
            {
                String firstName = MockDataCreator.getRandomElement(MockDataCreator.NAMES1);
                String lastName = MockDataCreator.getRandomElement(MockDataCreator.NAMES2);
                String name = firstName + " " + lastName;
                
                newId = MockDataCreator.generateUsername(name);
            	account.setUsername(name);
            	
            }
            
            String query = "INSERT INTO User (Username,Email,PhoneNumber,Birthday, Age, Address, Gender,Hobbies, Job, Password) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
                
                
                if(!toPopulateMock)
                {
                	System.out.println("User saved successfully with Username: " + newId);
                	System.out.println("User password is :"+account.getPassword());                	
                	PrivateKey.createPrivateKey(account.getUsername());
                }
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void saveAdmin(Admin admin)
    {
        String newId = admin.getUsername();

        while(!idExists(newId)&&!adminExists(newId))
        {
            String firstName = MockDataCreator.getRandomElement(MockDataCreator.NAMES1);
            String lastName = MockDataCreator.getRandomElement(MockDataCreator.NAMES2);
            String name = firstName + " " + lastName;
            
            newId = MockDataCreator.generateUsername(name);
        	admin.setUsername(name);
        }
        	
            String query = "INSERT INTO Admin (Admin_id,Password) VALUES (?, ?)";
            try
            {
            	PreparedStatement statement = MainProgram.connection.prepareStatement(query);
                statement.setString(1, admin.getUsername());
                statement.setString(2, PasswordEncrypt.encryptSHA256(admin.getPassword(), admin.getUsername()));
                statement.executeUpdate();
                
            	System.out.println("Admin saved successfully with Admin_ID: " + newId);
            	System.out.println("Admin password is :"+admin.getPassword());                	
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
            
    }

    private static boolean idExists(String username){
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
    
    
    private static boolean adminExists(String username)
    {
    	String query = "SELECT * FROM Admin WHERE Admin_id = ?";
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