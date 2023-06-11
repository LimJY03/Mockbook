package AccessControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionNet {
    
    public static void buildGraph(RegularUser me, ResultSet rs) {

        ArrayList<String> visited = me.getConnection1();
        visited.add(me.getUsername());
        
        try{
            while (rs.next()) {

                if (rs.getString("Username").equals(me.getUsername())) continue;
                
                String[] theirFriends = rs.getString("Friend").split(",");
                if (me.getConnection1().contains(rs.getString("Username"))) {
                    for (String theirFriend: theirFriends) {
                        if (visited.contains(theirFriend)) continue;
                        me.getConnection2().add(theirFriend);
                        visited.add(theirFriend);
                    }
                }
                else {
                    boolean is2degree = false;
                    for (String theirFriend: theirFriends) {
                        if (me.getConnection1().contains(theirFriend)) {
                            me.getConnection2().add(theirFriend);
                            is2degree = true;
                            break;
                        }
                    }
                    if (!is2degree) me.getConnection3plus().add(rs.getString("Username"));
                }


            }
        }
        catch (SQLException e) { System.out.println(e.getMessage()); }
    }
}