package SearchFeature;

import java.util.ArrayList;
import java.util.PriorityQueue;

import AccessControl.RegularUser;

public class MutualFriend {

	public static ArrayList<String> getMutualFriends(RegularUser me, String targetFriendList) {
	        
        ArrayList<String> mutualFriends = new ArrayList<>();
        PriorityQueue<String> pq = new PriorityQueue<>((a, b) -> a.compareTo(b) > 0 ? 1 : -1);

        String[] targetFriends = {};
        
        if (targetFriendList != null) targetFriends = targetFriendList.split(",");

        for (int i = 0; i < targetFriends.length; i++) pq.offer(targetFriends[i]);

        int pointer = 0;

        String s1, s2 = pq.poll();
        
        while (pointer < me.getConnection1().size() && s2 != null) {
            
            s1 = me.getConnection1().get(pointer);
            
            if (s1.equals(s2)) if (!mutualFriends.contains(s1)) mutualFriends.add(s1);
            if (s1.compareTo(s2) <= 0) pointer++;       // [..., mutualfriend, ...] vs [mutualfriend, ...]
            if (s1.compareTo(s2) >= 0) s2 = pq.poll();  // [mutualfriend, ...] vs [..., mutualfriend, ...]
        }
        return mutualFriends;
    }
}
