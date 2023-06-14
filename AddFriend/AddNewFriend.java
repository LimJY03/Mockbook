package AddFriend;

import java.awt.AWTException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.PriorityQueue;

import AccessControl.ConnectionNet;
import AccessControl.RegularUser;
import MainProgram.MainProgram;
import TraceBack.TraceBack;
import ViewAccount.AccountFinder;

public class AddNewFriend extends TraceBack {
	
	RegularUser me;
	
	public AddNewFriend(RegularUser me) {
		this.me = me;
	}
	
	public TraceBack Main() throws InterruptedException, AWTException {
		addFriend(this.me);
		this.previous.isPrevious = true;
		return this.previous;
	}
	

	public static ArrayList<String> sentFriendRequestList(ArrayList<String> sentFriendRequests,
		ArrayList<String> friendRecommendation, int index, String username) {

		int position = index - 1;
		String friendPos = friendRecommendation.get(position);

		if (sentFriendRequests.contains(friendPos)) {
			System.out.println("You have already sent a friend request to " + friendPos);
		} else {
			sentFriendRequests.add(friendPos);
			System.out.println("Friend request sent by " + username + " to " + friendPos);
			System.out.println("");
			updateDBAfterSent(username, friendPos, sentFriendRequests);
		}
		return sentFriendRequests;
	}

	private static void updateDBAfterSent(String username, String targetUsername,
			ArrayList<String> newSentRequestList) {

		try {

			PreparedStatement stmt = MainProgram.connection.prepareStatement(
					"SELECT ReceivedFriendRequest FROM User WHERE Username = '" + targetUsername + "'");
			ResultSet rs = stmt.executeQuery();
			String newReceivedList = "", newSentList = "";

			// Update target's received list
			if (rs.next()) {
				newReceivedList = rs.getString("ReceivedFriendRequest");
				if (newReceivedList == null)
					newReceivedList = username;
				else
					newReceivedList += username + ",";
			}

			stmt = MainProgram.connection.prepareStatement("UPDATE User SET ReceivedFriendRequest = '" + newReceivedList
					+ "' WHERE Username = '" + targetUsername + "'");
			stmt.executeUpdate();

			// Update my sent list
			newSentList = newSentRequestList.toString().replaceAll(" ", "");
			newSentList = newSentList.substring(1, newSentList.length() - 1);

			stmt = MainProgram.connection.prepareStatement(
					"UPDATE User SET SentFriendRequest = '" + newSentList + "' WHERE Username = '" + username + "'");
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void updateDBAfterReceived(String username, String targetUsername,
			ArrayList<String> newReceivedRequestList, boolean accept) {

		try {

			PreparedStatement stmt = MainProgram.connection
					.prepareStatement("SELECT SentFriendRequest FROM User WHERE Username = '" + targetUsername + "'");
			ResultSet rs = stmt.executeQuery();
			String newReceivedList = "", newSentList = "", myFriendList = "", targetFriendList = "";

			// Update my received list
			newReceivedList = newReceivedRequestList.toString().replaceAll(" ", "");
			newReceivedList = newReceivedList.substring(1, newReceivedList.length() - 1);

			MainProgram.connection.prepareStatement("UPDATE User SET ReceivedFriendRequest = '" + newReceivedList + "' WHERE Username = '" + username + "'").executeUpdate();

			// Update target's sent list
			if (rs.next()) {

				String[] tempSentList = rs.getString("SentFriendRequest").split(",");

				for (String sent : tempSentList) {
					if (sent.equals(username))
						continue;
					newReceivedList += sent + ",";
				}
			}

			MainProgram.connection.prepareStatement("UPDATE User SET SentFriendRequest = '" + newSentList + "' WHERE Username = '" + targetUsername + "'").executeUpdate();
			
			if (accept) {
			
				// Update my friend list
				rs = MainProgram.connection.prepareStatement("SELECT Friend from User WHERE Username = '" + username + "'").executeQuery();
				if (rs.next()) myFriendList = rs.getString("Friend");
				myFriendList += targetUsername + ",";
				MainProgram.connection.prepareStatement("UPDATE User SET Friend = '" + myFriendList + "' WHERE Username = '" + username + "'").executeUpdate();
				
				// Update target friend list
				rs = MainProgram.connection.prepareStatement("SELECT Friend from User WHERE Username = '" + targetUsername + "'").executeQuery();
				if (rs.next()) targetFriendList = rs.getString("Friend");
				targetFriendList += username + ",";
				MainProgram.connection.prepareStatement("UPDATE User SET Friend = '" + targetFriendList + "' WHERE Username = '" + targetUsername + "'").executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static ArrayList<String> processReceiveFriendRequest(RegularUser me,
			ArrayList<String> receivedFriendRequestList, int acceptOrDecline, int option) {

		int position = option - 1;
		String friend = receivedFriendRequestList.get(position);

		switch (acceptOrDecline) {
		case 1 -> {
			me.getConnection1().add(friend); // add to my friend list
			if (me.getConnection2().contains(friend)) me.getConnection2().remove(me.getConnection2().indexOf(friend));
			else me.getConnection3plus().remove(me.getConnection3plus().indexOf(friend));
			receivedFriendRequestList.remove(position); // remove from my received friend requests list
			System.out.println("Accept friend: successful");
			System.out.println("updated received friend requests list for me: " + receivedFriendRequestList);
			updateDBAfterReceived(me.getUsername(), friend, receivedFriendRequestList, true);
		}
		case 2 -> {
			receivedFriendRequestList.remove(position);
			System.out.println("Decline friend: successful");
			System.out.println("updated received friend requests list for me: " + receivedFriendRequestList);
			updateDBAfterReceived(me.getUsername(), friend, receivedFriendRequestList, false);
		}
		case 3 -> {
			String targetUsername = receivedFriendRequestList.get(position);
			// View User from Daniel
		}
		default -> System.out.println("");
		}

		return receivedFriendRequestList;
	}
	
	private static int sort(String a, String b) {
		return ConnectionNet.map.get(b).mutualFriends.size() - ConnectionNet.map.get(a).mutualFriends.size();
	}

	private static void addFriend(RegularUser me) {
		
		ArrayList<String> recommendation = new ArrayList<>();
		PriorityQueue<String> recommendationPq = new PriorityQueue<>((a, b) -> sort(a, b));
		recommendationPq.addAll(me.getConnection2());

		ArrayList<ArrayList<String>> sentReceivedRequest = getSentReceivedRequest(me);
		ArrayList<String> sentRequest = sentReceivedRequest.get(0);
		ArrayList<String> receivedRequest = sentReceivedRequest.get(1);

		// display friend recommendation table
		System.out.println("Friend recommendations:\n");

		if (recommendationPq.isEmpty()) {
			recommendationPq.addAll(me.getConnection3plus());
		}

		int p = 1;
		while (!recommendationPq.isEmpty()) {
			String recommend = recommendationPq.poll();
			recommendation.add(recommend);
			System.out.println(p + ": \t" + recommend + "\t\t" + ConnectionNet.map.get(recommend).mutualFriends.size());
			p++;
		}

		System.out.println("\n");

		// get index of friend to add based on friend recommendation table
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter index of friend to add: ");
		int index = Integer.parseInt(sc.nextLine());

		System.out.println("\n");

		// send request and display sent friend req list for user/me
		while (index > recommendation.size()) {
			System.out.println("Invalid index. Enter a valid index.");
			index = Integer.parseInt(sc.nextLine());
		}
		
		// Add or view

		

		
		while(true)
		{
			System.out.println("Enter 1 to add friend, 2 to view account, -1 to quit");
			int addOrView = Integer.parseInt(sc.nextLine());
			System.out.println("\n");
			
			if (addOrView == 1) {
				
				sentRequest = sentFriendRequestList(sentRequest, recommendation, index, me.getUsername());
				System.out.println("Pending Sent Request(s)");
				int k = 1;
				for (String s : sentRequest) {
					System.out.println(k + ":\t" + s);
					k++;
				}
		
				System.out.println("---\n");
			}
			else if(addOrView == 2){
				AccountFinder.displayAccountInformation(recommendation.get(index - 1));
				
			}
			else if(addOrView == -1)
				break;
			else
				System.out.println("Invalid option");
		}
		


		// show received list
		System.out.println("Pending Accept Request(s)");
		int j = 1;
		
		if (!receivedRequest.isEmpty()) {
			
			for (String s : receivedRequest) {
				System.out.println(j + ":\t" + s);
				j++;
			}

			System.out.println("---\n");

			// choose who to accept or decline
			System.out.println("Enter index of friend to accept/decline: ");
			int option = Integer.parseInt(sc.nextLine());
			while ((option > receivedRequest.size()) || option < 1) {
				System.out.println("Invalid index. Enter a valid index.");
				option = Integer.parseInt(sc.nextLine());
			}

			// accept or decline
			System.out.println("Enter 1 to accept friend request, 2 to decline friend request for me, 3 to view "
					+ receivedRequest.get(option - 1));
			
			int acceptOrDeclineOrView = Integer.parseInt(sc.nextLine());

			while (!(acceptOrDeclineOrView == 1 || acceptOrDeclineOrView == 2 || acceptOrDeclineOrView == 3)) {
				// not (1 or 2) -> not 1 and not 2
				System.out.println("Invalid index. Enter 1 or 2 or 3.");
				acceptOrDeclineOrView = Integer.parseInt(sc.nextLine());
			}

			System.out.println("\n");

			receivedRequest = processReceiveFriendRequest(me, receivedRequest, acceptOrDeclineOrView, option);

		}
		else {
			System.out.println("No pending request to be reviewed");
			System.out.println("---\n");
		}

		// display updated friends list for user/me
		if(me.getConnection1().contains(me.getUsername()))
			me.getConnection1().remove(me.getConnection1().indexOf(me.getUsername()));
		System.out.println("Friend's list for me: " + me.getConnection1());
	}

	// Get sentRequest list from db
	private static ArrayList<ArrayList<String>> getSentReceivedRequest(RegularUser me) 
    {
        
        ArrayList<String> sentRequest = new ArrayList<>();
        ArrayList<String> receivedRequest = new ArrayList<>();
        
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        
        try {
            
            PreparedStatement stmt = MainProgram.connection.prepareStatement("SELECT SentFriendRequest, ReceivedFriendRequest FROM User WHERE Username = '" + me.getUsername() + "'");
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String sentRequestHolder = rs.getString("SentFriendRequest");
                String receivedRequestHolder = rs.getString("ReceivedFriendRequest");
                String[] sentRequestArr = {};
                String[] receivedRequestArr = {};
                
                if (sentRequestHolder != null) {
                    sentRequestArr = sentRequestHolder.split(",");
                    for (String sent: sentRequestArr) sentRequest.add(sent);
                }
                if (receivedRequestHolder != null) {
                    receivedRequestArr = receivedRequestHolder.split(",");
                    for (String sent: receivedRequestArr) receivedRequest.add(sent);
                }
                
                result.add(sentRequest);
                result.add(receivedRequest);
            }
        }
        catch (SQLException e) { System.out.println(e.getMessage()); }
        
        return result;
    }
}
