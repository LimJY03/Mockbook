import java.util.ArrayList;
import java.util.Collections;

public class MutualFriends {

    public static ArrayList<String> getMutualFriends(String list1, String list2) {
        
        ArrayList<String> friendList1 = new ArrayList<>();
        ArrayList<String> friendList2 = new ArrayList<>();
        
        Collections.addAll(friendList1, (list1.split(",")));
        Collections.addAll(friendList2, (list2.split(",")));
        
        Collections.sort(friendList1);
        Collections.sort(friendList2);
        
        friendList1.retainAll(friendList2);
        return friendList1;
    }

    // Test
    public static void main(String[] args) {

        String list1 = "d,f,a,c,e,b,g";
        String list2 = "c,g,e,l,i,j,a";
        
        System.out.println(getMutualFriends(list1, list2));
    }
}