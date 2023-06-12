package AccessControl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import MainProgram.MainProgram;;

public class ConnectionNet {

	public static void buildGraph(RegularUser me) {

		ArrayList<String> visited = me.getConnection1();
		ResultSet rs = buildGraphHelper();
		
		visited.add(me.getUsername());

		try {
			while (rs.next()) {

				if (rs.getString("Username").equals(me.getUsername()))
					continue;

				String friendHolder = rs.getString("Friend");
				String[] theirFriends = {};
				
				if (friendHolder != null) theirFriends = friendHolder.split(",");
				
				if (me.getConnection1().contains(rs.getString("Username"))) {
					for (String theirFriend : theirFriends) {
						if (visited.contains(theirFriend))
							continue;
						me.getConnection2().add(theirFriend);
						visited.add(theirFriend);
					}
				} else {
					boolean is2degree = false;
					for (String theirFriend : theirFriends) {
						if (me.getConnection1().contains(theirFriend)) {
							me.getConnection2().add(theirFriend);
							is2degree = true;
							break;
						}
					}
					if (!is2degree)
						me.getConnection3plus().add(rs.getString("Username"));
				}

			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static RegularUser getAllConnection(String myName) {

		try {
				
			PreparedStatement stmt = MainProgram.connection.prepareStatement("SELECT * FROM User WHERE Username = '"+myName+"'");
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
			    // Retrieve data from ResultSet
			    String password = rs.getString("Password");
			    String email = rs.getString("Email");
			    String contact = rs.getString("PhoneNumber");
			    Integer age = rs.getInt("Age");
			    String gender = rs.getString("Gender");
			    String job = rs.getString("Job");
			    String hobbies = rs.getString("Hobbies");
			    String address = rs.getString("Address");
			    String friendHolder = rs.getString("Friend");
			    LocalDate birthday = rs.getDate("Birthday")==null? null : rs.getDate("Birthday").toLocalDate();
			    String[] friends = {};
			    
			    if (friendHolder != null) friends = friendHolder.split(",");		    

			    RegularUser me = new RegularUser(myName, email, contact, age, gender, password);

			    for (String friend : friends)
			        me.getConnection1().add(friend);
			    
			    if(!me.getConnection1().isEmpty())
			    	me.getConnection1().remove(me.getConnection1().size()-1);
			    
			    return me;
			} else {
			    System.out.println("No user found with the username: " + myName);
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static ResultSet buildGraphHelper() {
		try {

			PreparedStatement stmt = MainProgram.connection.prepareStatement("SELECT Friend,Username FROM User");
			ResultSet rs = stmt.executeQuery();

			return rs;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

}
