package Registration;

import java.awt.AWTException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Display.Display;
import MainProgram.MainPageFeature;
import MainProgram.MainProgram;
import TraceBack.TraceBack;


public class Login extends TraceBack{
    private String username,password,emailAddress;
    
    
    private boolean loginHelperMethod(String type) throws InterruptedException, AWTException
    {
    	Thread.sleep(500);
    	
        if(type=="Username"||type=="Email")
        {
            clearConsole();
            if(type=="Username")
            {
                boolean flag = signInUsingUsername();
                MainProgram.GlobalDataStore.username = this.getUser();
                return flag;
            }
            else if(type=="Email")
            {
            	boolean flag = signInUsingEmail();
                return flag;
            }

            System.out.println("\nRedirecting You to The Main Page! ...");
            return false;
        }
        else
        {
            System.out.println("Redirecting You to The Entrance Page! ...");
        }
        Thread.sleep(500);
        clearConsole();
        return false;
    }

    public TraceBack Main() throws InterruptedException, AWTException
    {   
        Display.displayWelcomeLines("Login Page", "Login Page", "");
        Display.displayUserOption("Username", "Email", "Reset Password");
        
        TraceBack returnedTraceBack = null;
        
        breaker: while (true) {
            String getInt = MainProgram.sc.nextLine();
            switch (getInt) {
                case "1":
                    boolean flag = loginHelperMethod("Username");
                    if(flag == false)
                    {
                        this.previous.isPrevious = true;
                        returnedTraceBack = this.previous;
                        break breaker;
                    }
                    returnedTraceBack = new MainPageFeature();
                    break breaker;
                case "2":
                    boolean flag2 = loginHelperMethod("Email");
                    if(flag2 == false)
                    {
                        this.previous.isPrevious = true;
                        returnedTraceBack = this.previous;
                        break breaker;
                    }
                    returnedTraceBack = new MainPageFeature();
                    break breaker;
                case "3":
                    returnedTraceBack = new PasswordReset();
                    break breaker;
                case "0":
                	loginHelperMethod("0");
                    this.previous.isPrevious = true;
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
            System.out.print("Enter your Username: -1 to Exit ");
            name = MainProgram.sc.nextLine();
            if(name.equalsIgnoreCase("-1"))
                return false;
            System.out.print("Enter your Password: -1 to Exit ");
            pass = MainProgram.sc.nextLine();
            if(pass.equalsIgnoreCase("-1"))
                return false;
            System.out.println("");
            if(CheckerForAccount(name,PasswordEncrypt.encryptSHA256(pass, name),"username"))
                break;
            System.out.println("Wrong Username/Password, Try Again!");
        }while(true);
        
        this.username = name;
        this.password = pass;
        MainProgram.GlobalDataStore.username = name;
        System.out.println("You have Logged in Successfully!");
        
        if(getPrivateKey(name)==null)
        {
        	PrivateKey.createPrivateKey(name);
        	PasswordReset.resetPassword(name);
        }
        
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
        this.username = getUsernameFromEmail(email);
        System.out.println("Hello "+this.username);
        if(CheckerForAccount(email,PasswordEncrypt.encryptSHA256(pass, this.username),"email"))
            break;
            System.out.println("Wrong Email/Password, Try Again!");
        }while(true);
        
        this.emailAddress = email;
        this.password = pass;
        MainProgram.GlobalDataStore.username = this.username;
        System.out.println("You have Logged in Successfully!");
        
        if(getPrivateKey(this.username)==null)
        {
        	PrivateKey.createPrivateKey(this.username);
        	PasswordReset.resetPassword(this.username);
        }
        return true;
    }
    
    
    public String getUsernameFromEmail(String email)
    {
        String name = "";
        try{
            Statement stmt = MainProgram.connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Username FROM User WHERE Email = '" + email.trim() +"'");

            if (rs.next()) {
                name = rs.getString("Username");
                return name;
            }
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
        try{
            Statement stmt = MainProgram.connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE " + type +" ='"+ user +"' AND Password = '" + password+ "'");

            if (rs.next()) {
                return true;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        return false;
    }
    
    public static String getPrivateKey(String username) {
        String privateKey = null;
        try {
            PreparedStatement stmt = MainProgram.connection.prepareStatement("SELECT PrivateKey FROM User WHERE Username = ?");
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                privateKey = rs.getString("PrivateKey");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return privateKey;
    }

}