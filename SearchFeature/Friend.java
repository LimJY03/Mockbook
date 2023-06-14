package SearchFeature;

import java.util.ArrayList;

public class Friend {
	
	private int connectionDegree;
	private String username;
	private ArrayList<String> mutualFriends = new ArrayList<>();
	
	// Constructor
	public Friend(String username, int connectionDegree) {
		this.username = username;
		this.connectionDegree = connectionDegree;
	}

	// Methods
	public void setConnectionDegree(int connectionDegree) { this.connectionDegree = connectionDegree; }
	public void setUsername(String username) { this.username = username; }
	public void setMutualFriends(ArrayList<String> mutualFriends) { this.mutualFriends = mutualFriends; }
	public void addMutualFriends(String friend) { this.mutualFriends.add(friend); }
	public int getConnectionDegree() { return this.connectionDegree; }
	public String getUsername() { return this.username; }
	public ArrayList<String> getMutualFriends() { return this.mutualFriends; }
}