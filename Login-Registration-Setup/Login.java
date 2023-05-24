/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.mycompany.project.DB.*;
import java.util.Scanner;

/**
 *
 * @author HuSSon
 */
public class Login extends TraceBack{
    private String username,password,emailAddress;
    
    public void Main()
    {
        // Login l = new Login();
        // l.signInUsingUsername();
    }
    
    public void signInUsingUsername()
    {
        String name, pass;
        DBFactory db = new DBFactory();
        Scanner sc = new Scanner(System.in);
        do{
        System.out.print("Enter your Username: ");
        name = sc.nextLine();
        System.out.println("");
        System.out.println("Enter your Password: ");
        pass = sc.nextLine();
        if(db.searchTable("Username", name) && db.searchTable("Password", pass))
            break;
            System.out.println("Wrong Username/Password, Try Again");
        }while(true);
        
        this.username = name;
        this.password = pass;
        System.out.println("You have Logged in Successfully!");
        
    }
    
    public void signInUsingEmail()
    {
        String email, pass;
        DBFactory db = new DBFactory();
        Scanner sc = new Scanner(System.in);
        do{
        System.out.print("Enter your Email: ");
        email = sc.nextLine();
        System.out.println("");
        System.out.println("Enter your Password: ");
        pass = sc.nextLine();
        if(db.searchTable("Email", email) && db.searchTable("Password", pass))
            break;
            System.out.println("Wrong Email/Password");
        }while(true);
        
        this.emailAddress = email;
        this.password = pass;
        System.out.println("You have Logged in Successfully!");
        
    }
}
