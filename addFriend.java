/**
 * @author zahra  
 */
import java.util.*;

public class addFriend {
    
    public static Stack<String> getFriendRecommendation(ArrayList<String> list1, ArrayList<String> list2, String username) {
        
        //declare stack
        Stack<String> friendRecommendation = new Stack<>();

        // push additional elements into stack find friend recommendations based on list1 or for user1
        
        if(list2.contains(username)){
            list1.add(username);
        }  
        
        for(String s : list2){
            if(!list1.contains(s)){
                friendRecommendation.push(s);
            }
        }
        
        return friendRecommendation;
    }
    
    
    public static ArrayList<String> sentFriendRequestList(ArrayList<String> sentFriendRequests, Stack<String> friendRecommendation, int index, String username) {
    
        if(friendRecommendation.isEmpty()){
            
            System.out.println("No friend recommendations");
            
        } else if(index >= 1 && index <= friendRecommendation.size()) {
            
            int position = index - 1;
            String friend = friendRecommendation.get(position);
            
            if (sentFriendRequests.contains(friend)) {
                System.out.println("You have already sent a friend request to " + friend);
            } else {
                sentFriendRequests.add(friend);
                System.out.println("Friend request sent by " + username + " to " + friend);
                System.out.println("");
            }
        } else {
            
            System.out.println("Invalid index.");
        }
        return sentFriendRequests;
    }
   
  
    public static ArrayList<String> receiveFriendRequestList(ArrayList<String> receiveFriendRequests, String username){
        receiveFriendRequests.add(username);
        return receiveFriendRequests;
    }

    
    public static void processFriendRequest(ArrayList<String> sentFriendRequests, ArrayList<String> receiveFriendRequests, String input, int option) {
        
        int position = option - 1;
        
        if (input.equalsIgnoreCase("accept")) {
    
                receiveFriendRequests.remove(position);
                
                System.out.println("Accept friend: successful");
                
                System.out.println("updated received friend requests list: " + receiveFriendRequests);
    
        } else if (input.equalsIgnoreCase("decline")) {

                receiveFriendRequests.remove(position);
                
                System.out.println("Decline friend: successful");
                
                System.out.println("updated received friend requests list: " + receiveFriendRequests);

        } else {
            System.out.println("Invalid input. Please enter 'accept' or 'decline'.");
        }

    }

    
    public static void main(String[] args) {
        
        //generate test friend list
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        String username = "me";
        
        list1.add("A");
        list1.add("B");
        list1.add("C");
        list1.add("D");
        
        list2.add("A");
        list2.add("B");
        list2.add("Y");
        list2.add("Z");
        list2.add("me");
        
        //declare for friend recommendation, sent friend req list, received friend req list
        Stack<String> reco = getFriendRecommendation(list1, list2, username);
        ArrayList<String> sent = new ArrayList<>();
        ArrayList<String> receive = new ArrayList<>();
        
        //generate test for sent friend req list
        sent.add("Q");
        sent.add("R");
        
        //generate test for received friend req list
        receive.add("J");
        receive.add("K");
        
        
        //display friend recommendation table
        System.out.println("Friend recommendations: ");
        int p = 1;
        for (String s : reco) {
            System.out.println(p + "\t" + s);
            p++;
        }
        
        System.out.println("\n");
        
        
        //get index of friend to add based on friend recommendation table
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter index of friend to add: ");
        int index = Integer.parseInt(sc.nextLine());
        
        System.out.println("\n");
        
        //send request and display sent friend req list for user/me
        sent = sentFriendRequestList(sent, reco, index, username);
        System.out.println("Display sent friend request list for me");
        int k = 1;
        for (String s : sent) {
            System.out.println(k + ":\t" + s );
            k++;
        }       
        
        System.out.println("\n");
        
        //show received list for friend
        receive = receiveFriendRequestList(receive, username);
        System.out.println("Display received friend request list for " + reco.get(index-1));
        int j = 1;
        for (String s : receive) {
            System.out.println(j + ":\t" + s );
            j++;
        }   
        
        System.out.println("\n");
        
        //accept or decline
        System.out.print("Enter 'accept' or 'decline' to process the friend request (for " + reco.get(index-1) + "): ");
        String input = sc.nextLine();
        System.out.println("Enter index of friend to accept/decline: ");
        int option = Integer.parseInt(sc.nextLine());
        list1.add(receive.get(option-1));
        
        System.out.println("\n");
        
        //update sent and received friend request list
        processFriendRequest(sent, receive, input, option);
        
        //display updated friends list for user/me
        System.out.println("Friend's list for me: ");
        if(list1.contains("me")){
            list1.remove("me");
            System.out.print(list1.toString());
        }
        
        
    }
    
}




