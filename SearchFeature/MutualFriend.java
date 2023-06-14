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

        for (int i = 0; i < targetFriends.length - 1; i++) pq.offer(targetFriends[i]);

        String s1 = pq.poll();

        while (!pq.isEmpty()) {

            String s2 = pq.poll();

            if (s1.equals(s2)) {

                if (!mutualFriends.contains(s1)) mutualFriends.add(s1);

                s1 = pq.poll();
                continue;
            }

            s1 = s2;
        }

        return mutualFriends;
    }
}
