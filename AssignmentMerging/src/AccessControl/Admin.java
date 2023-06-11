package AccessControl;
import java.sql.*;
import MyDataBase.MyDataBase;
import MyHashMap.MyHashMap;

public class Admin extends User{

	
	public Admin(String n,String p)
	{
		super(n,p);
	
	}
	
	
	public String getName()
	{
		return super.getUsername();
	}
	
	
	public String getPassword()
	{
		return super.getPassword();
	}
	
	
	public static boolean isValidAdminId(String adminId) {
	    try (Connection conn = MyDataBase.establishConnection();
	         PreparedStatement stmt = conn.prepareStatement("SELECT Admin_id FROM Admin WHERE Admin_id = ?")) {

	        stmt.setString(1, adminId);
	        ResultSet rs = stmt.executeQuery();
	        return rs.next();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}
	
	

	public static boolean isValidPassword(String adminId, String password) {
	    try (Connection conn = MyDataBase.establishConnection();
	         PreparedStatement stmt = conn.prepareStatement("SELECT Password FROM Admin WHERE Admin_id = ?")) {

	        stmt.setString(1, adminId);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            return rs.getString("Password").equals(password);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}
	
	
	
	
	
	public int guiAddUser(String username,String userEmail,String userContact,String userPassword)
	{
		try (Connection conn = MyDataBase.establishConnection();
	             PreparedStatement stmt = conn.prepareStatement("INSERT INTO User(Username, Email, Contact, Password) VALUES (?, ?, ?, ?)")) {
	            stmt.setString(1, username);
	            stmt.setString(2, userEmail);
	            stmt.setString(3, userContact);
	            stmt.setString(4, userPassword);

	            int rowAffected = stmt.executeUpdate();

	            return rowAffected;
	            
	        } catch (SQLException sqlE) {
	            sqlE.printStackTrace();
	        }
		
		return 0;
	}
	
	
	
	
	public int guiDeleteUser(String attribute,String valueToDelete)
	{
		Connection conn = MyDataBase.establishConnection();
		try
		{
			String query = "DELETE FROM USER WHERE "+attribute+"= ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			
			stmt.setString(1, valueToDelete);
			
			return stmt.executeUpdate();
		}
		catch(SQLException e)
		{
			System.out.println("Error on deleting user attribute");
			e.printStackTrace();
		}
		
		return 0;
	}
	
	
	
	
	public int guiUpdateUser(String username,MyHashMap<String,String>map)
	{
		Connection conn = MyDataBase.establishConnection();
		try
		{
			String query = "UPDATE User SET ";
			for (String s : map.keySet()) {
			    query += s.trim() + " = '" + map.get(s).trim() + "',";
			}
			query = query.substring(0, query.length()-1); 
			query += " WHERE Username = '" + username.trim() + "'";

			PreparedStatement stmt = conn.prepareStatement(query);
			
			int rowAffected = stmt.executeUpdate();
			
			return rowAffected;
		}
		catch(SQLException e)
		{
			System.out.println("Error on updating user");
			e.printStackTrace();
		}
		
		return 0;
	}
	
	
	

}
