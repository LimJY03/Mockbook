package Registration;

import java.awt.AWTException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Display.Display;
import MainProgram.MainPageFeature;
import MainProgram.MainProgram;
import MyDataBase.MyDataBase;
import TraceBack.TraceBack;


public class Login extends TraceBack{
    private String username,password,emailAddress;
    
    
    private void loginHelperMethod(String type) throws InterruptedException, AWTException
    {
    	Thread.sleep(500);
    	
        if(type=="Username"||type=="Email")
        {
            clearConsole();
            if(type=="Username")
            {
                signInUsingUsername();
                MainProgram.GlobalDataStore.username = this.getUser();
            }
            else
            {
            	signInUsingEmail();
            }

            System.out.println("\nRedirecting You to The Main Page! ...");


        }
        else
        {
            System.out.println("Redirecting You to The Entrance Page! ...");
        }
        Thread.sleep(500);
        clearConsole();
    }

    public TraceBack Main()  throws InterruptedException, AWTException
    {   
        Display.displayWelcomeLines("Login Page", "Login Page", "");
        Display.displayUserOption("Username", "Email", "");
        
        TraceBack returnedTraceBack = null;
        
        breaker: while (true) {
            String getInt = MainProgram.sc.nextLine();
            switch (getInt) {
                case "1":
                	loginHelperMethod("Username");
                    returnedTraceBack = new MainPageFeature();
                    break breaker;
                case "2":
                	loginHelperMethod("Email");
                    returnedTraceBack = new MainPageFeature();
                    break breaker;
                case "0":
                	loginHelperMethod("0");
                    this.isPrevious = true;
                    returnedTraceBack = this.previous;
                    break breaker;
                default:
                    System.out.println("This is NOT One of The Choices Given! Type Again :D");
                    break;
            }
        }
        
        return returnedTraceBack;
    }
    
    public String getUser()
    {
        return this.username;
    }
    
      public boolean signInUsingUsername()
    {
        String name, pass;

        Display.displayProgramPage("Login Using Username");

        do{
            System.out.print("Enter your Username: ");
            name = MainProgram.sc.nextLine();
            System.out.print("Enter your Password: ");
            pass = MainProgram.sc.nextLine();
            System.out.println("");
            if(CheckerForAccount(name,PasswordEncrypt.encryptSHA256(pass, name),"username"))
                break;
            System.out.println("Wrong Username/Password, Try Again!");
        }while(true);
        
        this.username = name;
        this.password = pass;
        MainProgram.GlobalDataStore.username = name;
        System.out.println("You have Logged in Successfully!");
        
        return true;
    }
    
    public boolean signInUsingEmail()
    {
        String email, pass;
        Display.displayProgramPage("Login Using Email");

        do{
        System.out.print("Enter your Email: ");
        email =MainProgram.sc.nextLine();
        System.out.print("Enter your Password: ");
        pass = MainProgram.sc.nextLine();
        System.out.println("");
        this.username = getUsernameFromEmail(this.emailAddress);
        if(CheckerForAccount(email,PasswordEncrypt.encryptSHA256(pass, this.username),"email"))
            break;
            System.out.println("Wrong Email/Password, Try Again!");
        }while(true);
        
        this.emailAddress = email;
        this.password = pass;
        MainProgram.GlobalDataStore.username = this.username;
        System.out.println("You have Logged in Successfully!");
        return true;
    }
    
    
    public String getUsernameFromEmail(String email)
    {
        String name = "";
        Connection connection = MyDataBase.establishConnection();
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Username FROM User WHERE Email = '" + email +"'");

            if (rs.next()) {
                name = rs.getString("Username");
                return name;
            }
            connection.close();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        return name;
    }
    
    public boolean CheckerForAccount(String user, String password , String type)
    {
        if(type.equalsIgnoreCase("username"))
            type = "Username";
        else if(type.equalsIgnoreCase("email"))
            type = "Email";
        else{
            System.out.println("give propa type");
        }
        Connection connection = MyDataBase.establishConnection();
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE " + type +" ='"+ user +"' AND Password = '" + password+ "'");

            if (rs.next()) {
                return true;
            }
            connection.close();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        return false;
    }
}
