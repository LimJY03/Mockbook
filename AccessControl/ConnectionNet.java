package AccessControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import MainProgram.MainProgram;
import MyHashMap.MyHashMap;
import SearchFeature.Friend;
import SearchFeature.MutualFriend;

public class ConnectionNet {

	public static MyHashMap<String, Friend> map = new MyHashMap<>();

	public static void buildGraph(RegularUser me) {

		ArrayList<String> visited = me.getConnection1();
		ResultSet rs = buildGraphHelper();

		visited.add(me.getUsername());

		try {
			while (rs.next()) {

				String username = rs.getString("Username");

				// This is me
				if (username.equals(me.getUsername()))
					continue;

				String friendHolder = rs.getString("Friend");
				String[] theirFriends = {};

				// This user has friend
				if (friendHolder != null)
					theirFriends = friendHolder.split(",");

				// Username is my friend -> 1st degree
				if (me.getConnection1().contains(username)) {

					ConnectionNet.map.put(username, new Friend(username, 1));
					ConnectionNet.map.get(username).setMutualFriends(MutualFriend.getMutualFriends(me, rs.getString("Friend")));

					// Explore my friend’s friend
					for (String theirFriend: theirFriends) {
						if (visited.contains(theirFriend))
							continue;

						// My friend’s friend not in my friend list
						me.addConnection2(theirFriend);
						ConnectionNet.map.put(theirFriend, new Friend(theirFriend, 2));
						ConnectionNet.map.get(theirFriend).setMutualFriends(MutualFriend.getMutualFriends(me, rs.getString("Friend")));

						visited.add(theirFriend);
					}
				}

				// This is not my friend -> 2nd / 3rd degree
				else {

					if (visited.contains(username))
						continue;

					ArrayList<String> userMutualFriends = MutualFriend.getMutualFriends(me, rs.getString("Friend"));

					// Username don't have mutual friend with me -> 3rd degree
					if (userMutualFriends.isEmpty()) {
						me.addConnection3plus(username);
						ConnectionNet.map.put(username, new Friend(username, 3));
					} 
					
					// Username have mutual friend with me -> 2nd degree
					else {
						me.addConnection2(username);
						ConnectionNet.map.put(username, new Friend(username, 2));
						ConnectionNet.map.get(username).setMutualFriends(userMutualFriends);
					}
					visited.add(username);
				}
			}
		} catch (SQLException e) { System.out.println(e.getMessage()); }
	}

	public static RegularUser getAllConnection(String myName) {

		try {

			
			ResultSet rs = MainProgram.connection.prepareStatement("SELECT * FROM User WHERE Username = '" + myName + "'").executeQuery();

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
				LocalDate birthday = rs.getDate("Birthday") == null ? null : rs.getDate("Birthday").toLocalDate();
				String[] friends = {};

				if (friendHolder != null)
					friends = friendHolder.split(",");
					
				RegularUser me = new RegularUser(myName, email, contact, age, gender, password, job, hobbies, address, birthday);

				for (int i = 0; i < friends.length; i++)
					me.addConnection1(friends[i]);

				return me;
			} 
			else { System.out.println("No user found with the username: " + myName); }
		} 
		catch (SQLException e) { e.printStackTrace(); }

		return null;
	}

	private static ResultSet buildGraphHelper() {

		try {
			return MainProgram.connection.prepareStatement("SELECT Friend, Username FROM User").executeQuery();
		} 
		catch (SQLException e) { e.printStackTrace(); }

		return null;
	}
}