package SearchFeature;

import java.util.Scanner;
import AccessControl.RegularUser;
import Display.Display;
import MainProgram.MainProgram;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import TraceBack.TraceBack;
import ViewAccount.AccountFinder;

import java.awt.AWTException;

public class Search extends TraceBack {


	public TraceBack Main() throws InterruptedException, AWTException {
		search();
		this.previous.isPrevious = true;
		return this.previous;
	}

	private void search() throws InterruptedException, AWTException {
		Scanner sc = MainProgram.sc;

		outerLoop: while (true) {

			Display.displaySearchOption();

			System.out.print("\nEnter your option to search for Users:");

			String option = sc.nextLine();

			switch (option) {
			case "0":
				System.out.println("Exit successfully");
				break outerLoop;

			case "1":
				System.out.print("Enter the Username: ");
				viewAccount("Username");
				
				break;

			case "2":
				System.out.print("Enter the Email: ");
				viewAccount("Email");

				break;

			case "3":
				System.out.print("Enter the Age: ");
				viewAccount("Age");

				break;

			case "4":
				System.out.print("Enter the Contact: ");
				viewAccount("PhoneNumber");

				break;

			case "5":
				System.out.print("Enter the Gender: ");
				viewAccount("Gender");

				break;
				
			case "6":
				System.out.print("Enter the Job: ");
				viewAccount("Job");

				break;
			case "7":
				System.out.print("Enter the Hobby: ");
				viewAccount("Hobbies");

				break;
				
			case "8":
				System.out.print("Enter the Address: ");
				viewAccount("Address");

				break;

			default:
				System.out.println("\nInvalid option. Re-enter an option\n");
				break;

			}

		}

	}

	private static ArrayList<RegularUser> prepareSearchList(String toSearch, String type) {

		ArrayList<RegularUser> list = new ArrayList<>();

		try {

			String query = String.format("SELECT * FROM User WHERE %s LIKE '%%%s%%' ORDER BY %s", type, toSearch, type);
			PreparedStatement stmt = MainProgram.connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String username = rs.getString("Username");
				String email = rs.getString("Email");
				int age = rs.getInt("Age");
				String contact = rs.getString("PhoneNumber");
				String gender = rs.getString("Gender");
				String password = rs.getString("Password");
				String job = rs.getString("Job");
				String hobbies = rs.getString("Hobbies");
				String address = rs.getString("Address");

				RegularUser newUser = new RegularUser(username, email, contact, age, gender, password, job, hobbies, address);
				list.add(newUser);

			}

		}

		catch (SQLException e) {
			System.out.println("Error on obtaining info." + e.getMessage());
		}

		return list;
	}
	
	private static void viewAccount(String optionType)
	{
		ArrayList<RegularUser> list = prepareSearchList(MainProgram.sc.nextLine().trim(),optionType);
		Display.displaySearchResult(list,"Username");
		
		if(list.isEmpty())
			System.out.println("\nNo available account to be view\n");
		else
		{
			viewAccountLoop: while(true)
			{
				System.out.println("\nBased on the number given, enter the desired account number to view, '-1' to quit\n");
				String accountOption = MainProgram.sc.nextLine();
				switch(accountOption)
				{
					case "-1": break viewAccountLoop;
					default: 
						try {
							int accountNo = Integer.parseInt(accountOption);
							
							if(accountNo<0||accountNo>=list.size())
								System.out.println("\nInvalid number. Try again\n");
							else
							{
								RegularUser selectedUser = list.get(accountNo);
								AccountFinder.displayAccountInformation(selectedUser.getUsername());
							}
								
						}
						catch(Exception e){
							System.out.println("\nInvalid option. Try again.\n");
					}
				}
			}
		}
	}

}
