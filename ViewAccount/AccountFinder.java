package ViewAccount;

import java.sql.*;
import AccessControl.ConnectionNet;
import MainProgram.MainProgram;

public class AccountFinder {

	public static void displayAccountInformation(String selectedAccountUsername) {
		String query = "SELECT * FROM User WHERE Username = ?";

		try {
			PreparedStatement preparedStatement = MainProgram.connection.prepareStatement(query);
			preparedStatement.setString(1, selectedAccountUsername);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				System.out.println("\nAccount Information:");
				
				int connectionDegree = ConnectionNet.map.get(selectedAccountUsername).getConnectionDegree();
				
		        System.out.println("Username: " + resultSet.getString("Username") +" Connection Degree: " +connectionDegree);
		        System.out.println("Gender: " + resultSet.getString("Gender"));
		        System.out.println("Hobbies: " + resultSet.getString("Hobbies"));
		        System.out.println("Jobs: " + resultSet.getString("Job"));
		        System.out.println("Email: " + resultSet.getString("Email"));
		        System.out.println("Phone Number: " + resultSet.getString("PhoneNumber"));
		        System.out.println("Age: " + resultSet.getInt("Age"));
		        System.out.println("Address: " + resultSet.getString("Address"));
		        System.out.println("Birthday: " + resultSet.getString("Birthday"));

				
			} 
			else 
			{
				System.out.println("\nNo accounts found with the provided name.");
			}
		} catch (SQLException e) 
		{
			System.out.println();
		}
	}

}
