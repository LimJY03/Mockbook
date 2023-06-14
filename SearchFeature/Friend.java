package SearchFeature;

import java.util.ArrayList;

public class Friend {
	
	public int connectionDegree;
	public String username;
	public ArrayList<String> mutualFriends = new ArrayList<>();
	
	public Friend(String username, int connectionDegree) {
		this.username = username;
		this.connectionDegree = connectionDegree;
	}
}