/**
 * @author HuSSon
 */

package Registration;

import com.mycompany.project.DB.*;
import java.awt.AWTException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Login extends TraceBack {

    private String username, password, emailAddress;

    public TraceBack Main() throws InterruptedException, AWTException {

        Scanner sc = new Scanner(System.in);

        System.out.println("****************");
        System.out.println("*   MockBook   *");
        System.out.println("****************\n");
        System.out.println("\t\t\t\t\t-------------------------------------\t\t");
        System.out.println("\t\t\t\t\t|             Login Page            |");
        System.out.println("\t\t\t\t\t-------------------------------------\t\t");
        System.out.println("\n\t\t This is The Login Page! You Will be Presented With Multiple Options you May Choose From Them!");
        System.out.println("\n\t\t\t\t\t-------------------------------------\t\t");
        System.out.println("Type 1 to Login Using Your Username\nType 2 to Login Using Your Email\nType 0 To Go Back");
        System.out.println("\t\t\t\t\t-------------------------------------\t\t");
        
        TraceBack returnedTraceBack = null;

        breaker: while (true) {
            String getInt = sc.nextLine();
            switch (getInt) {
                case "1":
                    Thread.sleep(500);
                    clearConsole();
                    signInUsingUsername();
                    MainProgram.GlobalDataStore.username = this.getUser();
                    System.out.println("\nRedirecting You to The Main Page! ...");
                    Thread.sleep(500);
                    clearConsole();
                    returnedTraceBack = new MainPageFeature();
                    break breaker;
                case "2":
                    Thread.sleep(500);
                    clearConsole();
                    signInUsingEmail();
                    System.out.println("\nRedirecting You to The Main Page! ...");
                    Thread.sleep(500);
                    clearConsole();
                    returnedTraceBack = new MainPageFeature();
                    break breaker;
                case "0":
                    System.out.println("Redirecting You to The Entrance Page! ...");
                    returnedTraceBack = this.previous;
                    Thread.sleep(500);
                    clearConsole();
                    break breaker;
                default:
                    System.out.println("This is NOT One of The Choices Given! Type Again :D");
                    break;
            }
        }

        return returnedTraceBack;
    }

    public String getUser() {
        return this.username;
    }

    public boolean signInUsingUsername() {
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
        do {
            System.out.print("Enter your Username: ");
            name = sc.nextLine();
            System.out.print("Enter your Password: ");
            pass = sc.nextLine();
            System.out.println("");
            if (CheckerForAccount(name, pass, "username"))
                break;
            System.out.println("Wrong Username/Password, Try Again!");
        } while (true);

        this.username = name;
        this.password = pass;
        System.out.println("You have Logged in Successfully!");

        return true;
    }

    public boolean signInUsingEmail() {
        
        String email, pass;
        DBFactory db = new DBFactory();
        Scanner sc = new Scanner(System.in);

        System.out.println("****************");
        System.out.println("*   MockBook   *");
        System.out.println("****************\n");
        System.out.println("\t\t\t\t\t-------------------------------------\t\t");
        System.out.println("\t\t\t\t\t|          Login Using Email        |");
        System.out.println("\t\t\t\t\t-------------------------------------\t\t\n");

        do {
            System.out.print("Enter your Email: "); email = sc.nextLine();
            System.out.print("Enter your Password: "); pass = sc.nextLine();

            if (CheckerForAccount(email, pass, "email")) break;

            System.out.println("\nWrong Email/Password, Try Again!");

        } while (true);

        System.out.println("\nYou have Logged in Successfully!");
        
        this.emailAddress = email;
        this.password = pass;
        
        return true;
    }

    public boolean CheckerForAccount(String user, String password, String type) {
        
        type = type.equalsIgnoreCase("email") ? "Email" : "Username";

        Connection connection = MyDataBase.establishConnection();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE " + type + " ='" + user + "' AND Password = '" + password + "'");

            if (rs.next()) return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
