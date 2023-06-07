import java.util.ArrayList;
// import java.util.Collection;
// import java.util.Collections;
import java.util.PriorityQueue;

public class MutualFriends {

    public static ArrayList<String> getMutualFriends(String list1, String list2) {
        
        ArrayList<String> mutualFriends = new ArrayList<>();

        // ArrayList<String> friendList1 = new ArrayList<>();
        // ArrayList<String> friendList2 = new ArrayList<>();
        
        // Collections.addAll(friendList1, (list1.split(",")));
        // Collections.addAll(friendList2, (list2.split(",")));
        
        // Collections.sort(friendList1);
        // Collections.sort(friendList2);
        
        // friendList1.retainAll(friendList2);
        // return friendList1;

        PriorityQueue<String> pq = new PriorityQueue<>((a, b) -> a.compareTo(b) > 0 ? 1 : -1);
        String[] friends = (list1 + "," + list2).split(",");

        System.out.println();

        for (String friend: friends) pq.offer(friend);

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

    // Test
    public static void main(String[] args) {

        String list1 = "John,Emily,Michael,Sarah,David,Elizabeth,Christopher,Alexander,Olivia,Nicholas,Samantha,Matthew,Isabella,Jonathan,Victoria,Benjamin,Natalie,Anthony,Grace,Andrew,Hailey,Nathan,Sophia,William,Ava,Daniel,Emma,Ethan,Abigail,James,Madison,Aiden,Chloe,Mason,Emily,Joshua,Elizabeth,David,Addison,Christopher,Avery,Andrew,Scarlett,Liam,Zoe";
        String list2 = "Michael,Emily,Liam,Emma,Alexander,Olivia,Nicholas,Samantha,Matthew,Isabella,Jonathan,Victoria,Benjamin,Natalie,Anthony,Grace,Andrew,Hailey,Nathan,Sophia,William,Ava,Daniel,Emma,Ethan,Abigail,James,Madison,Aiden,Chloe,Mason,Emily,Joshua,Elizabeth,David,Addison,Christopher,Avery,Andrew,Scarlett,Liam,Zoe,Henry,Lily,Jack,Sophie";
        
        System.out.println(getMutualFriends(list1, list2));
    }
}