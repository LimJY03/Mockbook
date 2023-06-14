package AccessControl;

import java.awt.AWTException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import MainProgram.MainPageFeature;
import MainProgram.MainProgram;
import TraceBack.TraceBack;

import java.util.Scanner;
import java.util.regex.Pattern;

public class EditRegularUserAccount extends TraceBack{
	
	static Scanner sc = MainProgram.sc;
	static Connection connection = MainProgram.connection;
	

	
	public TraceBack Main() throws InterruptedException, AWTException {
		editAccount(MainPageFeature.me);
		this.previous.isPrevious = true;
		return this.previous;
	}
	

    public static void editAccount(RegularUser me){
        
        outerLoop: while(true)
        {
            System.out.println("Edit Account:");
            System.out.println("1) Edit Username");
            System.out.println("2) Edit Email");
            System.out.println("3) Edit Current Job Status");
            System.out.println("4) Edit Hobbies");
            System.out.println("5) Edit Contact Number");
            System.out.println("7) Edit Gender");
            System.out.println("8) Edit Age");
            System.out.println("9) Edit Password");
            System.out.println("10) Exit");
            
        	System.out.println("Enter your option");
        	String option = sc.nextLine();
        	
        	switch(option)
        	{
        		case "1" : 
        				   optionLoop: while(true)
        				   {
        					   System.out.println("This is your current Username: " + me.getUsername());
            				   System.out.println("Do you want to edit your username? Press 1 for yes, 2 for no");
            				   String yesOrNo = sc.nextLine();
            				   switch(yesOrNo)
            				   {
            				   	case"1": updateUsername();
            				   	case"2": break optionLoop;
            				   	default: System.out.println("Invalid Option. Please enter again");
            					   
            				   }
        				   }

        				   break;
        	
        	
        	
        	
        		case "10": break outerLoop;
        		default: System.out.println("Invalid option. Please enter again");
        				break;
        	}
        }

    }
    
    
    private static void updateUsername()
    {
    	System.out.println("Enter the new username you want to change");
    	String newUsername = sc.nextLine();
    	
    	while(!isValidUsername(newUsername))
    	{
    		System.out.println("Please Enter a new Username");
    		newUsername=sc.nextLine();
    	}
    	
    	try
    	{
    		String query = "UPDATE User SET Username = ? WHERE Username = ?";
    		
    		PreparedStatement stmt = connection.prepareStatement(query);
    		stmt.setString(1, newUsername);
    		stmt.setString(2, MainPageFeature.me.getUsername());

    		int rowAffected  = stmt.executeUpdate();
    		
    		if(rowAffected>0)
    		{
    			MainPageFeature.me.setUsername(newUsername);
    			System.out.println("Sucessfully change username");
    			System.out.println("Your current username is: "+MainPageFeature.me.getUsername());
    	        MainProgram.GlobalDataStore.username = MainPageFeature.me.getUsername();

    		}
    		else
    			System.out.println("Error on updating username. Please try again");
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    	
    }
    
    private static boolean isValidUsername(String username) {
        
        boolean isValid = true;
        final Pattern textPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z]).+$");
        
        if (MainProgram.db.searchTable("Username", username)) {
            System.out.println("Username has been taken");
            isValid =false;
        }
        else if (username.length() > 25 || username.length() < 5) {
            System.out.println("Username must be less than 25 and more than 5 characters in length.");
            isValid = false;
        }

        // Check Upper Case Match
        else if (!textPattern.matcher(username).matches()) {
            System.out.println("Username must have at least one uppercase / one lowercase character");
            isValid = false;
        }
        
        else if(username.contains(" "))
        {
        	System.out.println("Username can't contains empty space");
        	isValid = false;
        }

        return isValid;
    }
    
}