package SearchFeature;

import java.util.Scanner;
import AccessControl.RegularUser;
import Display.Display;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import MyDataBase.MyDataBase;

public class Search {

	public static void main(String[]args)
	{
		new Search().main();
	}
	

	public void main()
	{
		search();
	}
	
	
	private void search() {

		Scanner sc = new Scanner(System.in);

		outerLoop: while (true) {
			
			Display.displaySearchOption();

			System.out.print("Enter your option to search for Users: ");
			
			
				String option = sc.nextLine();
	
				
				switch (option) {
				case "0":
					System.out.println("Exit successfully");
					break outerLoop;
	
				case "1":
					System.out.print("Enter the Username: ");
					Display.displaySearchResult( prepareSearchList(sc.nextLine().trim(),"Username"), "Username");
					break;
				
				
				case "2":
					System.out.print("Enter the Email: ");
					Display.displaySearchResult( prepareSearchList(sc.nextLine().trim(),"Email"), "Email");
					break;
				
				case "3":
					System.out.print("Enter the Age: ");
					Display.displaySearchResult( prepareSearchList(sc.nextLine().trim(),"Age"), "Age");
				break;
				
				case "4":
					System.out.print("Enter the Contact: ");
					Display.displaySearchResult( prepareSearchList(sc.nextLine().trim(),"Contact"), "Contact");
					break;
					
				case "5":
					System.out.print("Enter the Gender: ");
					Display.displaySearchResult( prepareSearchList(sc.nextLine().trim(),"Email"), "Email");
					break;

					
				default:
					System.out.println("\nInvalid option. Re-enter an option\n");
					break;
					
				
			}
			

		}
		
		sc.close();
	}
	

	private static ArrayList<RegularUser> prepareSearchList(String toSearch,String type) {
		
		Connection conn = MyDataBase.establishConnection();

		ArrayList<RegularUser> list = new ArrayList<>();
		
		try 
		{	
			
			String query = String.format("SELECT * FROM User WHERE %s LIKE '%%%s%%' ORDER BY %s",type,toSearch,type);
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				String name = rs.getString("Username");
				String email = rs.getString("Email");
				Integer age = rs.getInt("Age");
				String contact = rs.getString("Contact");
				String gender = rs.getString("Gender");
				String password = rs.getString("Password");
				
				list.add(new RegularUser(name,email,contact,age, gender, password));
			}
			
			conn.close();
			
		}
		catch (SQLException e)
		{
			System.out.println("Error on obtaining info." + e.getMessage());
		}
		
		return list;
	}

}
