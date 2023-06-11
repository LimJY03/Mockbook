/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.mycompany.project.DB.*;
import java.awt.AWTException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
/**
 *
 * @author HuSSon
 */
public class Login extends TraceBack{
    private String username,password,emailAddress;
    
    public TraceBack Main()  throws InterruptedException, AWTException
    {
        Scanner i = new Scanner(System.in);
        System.out.println("****************");
        System.out.println("*   MockBook   *");
        System.out.println("****************\n");
        System.out.println("\t\t\t\t\t-------------------------------------\t\t");
        System.out.println("\t\t\t\t\t|             Login Page            |");
        System.out.println("\t\t\t\t\t-------------------------------------\t\t");
        System.out.println("\n\t\t This is The Login Page! You Will be Presented With Mulitiple Options you May Choose From Them!");

        System.out.println("\n\t\t\t\t\t-------------------------------------\t\t");
        System.out.println("Type 1 to Login Using Your Username\nType 2 to Login Using Your Email\nType 0 To Go Back!");
        System.out.println("\t\t\t\t\t-------------------------------------\t\t");
        TraceBack returnedTraceBack = null;
        
        breaker: while(true)
        {
            String getInt = i.nextLine();
            switch(getInt)
            {
                case "1":
                    Thread.sleep(250);
                    clearConsole();
                    signInUsingUsername();
                    MainProgram.GlobalDataStore.username = this.getUser();
                    System.out.println("");
                    System.out.println("Redirecting You to The Main Page! ...");
                    Thread.sleep(250);
                    clearConsole();
                    returnedTraceBack = new MainPageFeature();
                    break breaker;
                case "2":
                    Thread.sleep(250);
                    clearConsole();
                    signInUsingEmail();
                    System.out.println("");
                    System.out.println("Redirecting You to The Main Page! ...");
                    Thread.sleep(250);
                    clearConsole();
                    returnedTraceBack = new MainPageFeature();
                    break breaker;
                case "0":
                    System.out.println("Redirecting You to The Entrance Page! ...");
                    returnedTraceBack = this.previous;
                    Thread.sleep(250);
                    clearConsole();
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
        DBFactory db = new DBFactory();
        Scanner sc = new Scanner(System.in);
        System.out.println("****************");
        System.out.println("*   MockBook   *");
        System.out.println("****************\n");
        System.out.println("\t\t\t\t\t-------------------------------------\t\t");
        System.out.println("\t\t\t\t\t|        Login Using Username       |");
        System.out.println("\t\t\t\t\t-------------------------------------\t\t");
        System.out.println("\n");
        do{
            System.out.print("Enter your Username: ");
            name = sc.nextLine();
            System.out.print("Enter your Password: ");
            pass = sc.nextLine();
            System.out.println("");
            if(CheckerForAccount(name,pass,"username"))
                break;
            System.out.println("Wrong Username/Password, Try Again!");
        }while(true);
        
        this.username = name;
        this.password = pass;
        System.out.println("You have Logged in Successfully!");
        
        return true;
    }
    
    public boolean signInUsingEmail()
    {
        String email, pass;
        DBFactory db = new DBFactory();
        Scanner sc = new Scanner(System.in);
        System.out.println("****************");
        System.out.println("*   MockBook   *");
        System.out.println("****************\n");
        System.out.println("\t\t\t\t\t-------------------------------------\t\t");
        System.out.println("\t\t\t\t\t|          Login Using Email        |");
        System.out.println("\t\t\t\t\t-------------------------------------\t\t");
        System.out.println("\n");
        do{
        System.out.print("Enter your Email: ");
        email = sc.nextLine();
        System.out.print("Enter your Password: ");
        pass = sc.nextLine();
        System.out.println("");
        if(CheckerForAccount(email,pass,"email"))
            break;
            System.out.println("Wrong Email/Password, Try Again!");
        }while(true);
        
        this.emailAddress = email;
        this.username = getUsernameFromEmail(this.emailAddress);
        this.password = pass;
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
