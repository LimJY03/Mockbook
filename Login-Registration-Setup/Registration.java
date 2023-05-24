/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import com.mycompany.project.DB.DBFactory;
import java.awt.AWTException;
import java.util.Scanner;
import java.util.regex.*;

public class Registration extends TraceBack{

    // Properties
    private String username, emailAddress, phoneNumber, password;

    // Constructor
    public Registration() {
    }

    // Methods
    public void setUserName() {
        
        DBFactory db = new DBFactory();
        Scanner sc = new Scanner(System.in);
        boolean firstPass = true;
        boolean isValid;
        System.out.print("Enter Username: ");
        String userName = sc.nextLine();
        System.out.println("");

        // Validate Username Requirements
        do {
            isValid = false;

            if (!firstPass) {
                System.out.print("Username : "); userName  = sc.nextLine();
            }

            firstPass = false;
            
            // This username has been taken
            if (db.searchTable("Username", userName)) {
                System.out.println("Username has been taken");
                continue;
            }

            // Username Accepted
            if (!isValidUsername(userName)) {
                continue;
            }

            isValid = true;

        } while (!isValid);
        
        
        System.out.println("Username Accepted !");
        db.addNewUser(userName);
        this.username = userName;
    }
    
    private boolean isValidUsername(String Username) {
        
        boolean isValid = true;
        final Pattern textPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z]).+$");
        

        if (Username.length() > 25 || Username.length() < 5) {
            System.out.println("Username must be less than 25 and more than 5 characters in length.");
            isValid = false;
        }

        // Check Upper Case Match
        if (!textPattern.matcher(Username).matches()) {
            System.out.println("Username must have atleast one uppercase/ one lowercase character");
            isValid = false;
        }

        
//        if (!Username.matches("(.[A-Z].)")) {
//            System.out.println("Username must have atleast one uppercase character");
//            isValid = false;
//        }
//
//        // Check Lower Case Match
//        if (!Username.matches( "(.[a-z].)")) {
//            System.out.println("Username must have atleast one lowercase character");
//            isValid = false;
//        }

        return isValid;
    }

    public void setEmailAddress() {

        // Validating Email
        // if ((email.split("@").length == 2) && ...)
        
        Scanner sc = new Scanner(System.in);
        DBFactory db = new DBFactory();
        
        String email = "";
        boolean valid = true;
        
        do{
            System.out.print("Enter your Email Address: ");
            email = sc.next();

            System.out.println("\nYour Email address : " + email);

            System.out.println("\nIs the Email address valid ?" + isValidEmail(email));
            
            System.out.println("\nis this Email in the system? " + db.searchTable("Email", email));
            
            if(isValidEmail(email) && !db.searchTable("Email", email))
                break;

            System.out.println("\nTry Again");
        }while(valid);
        System.out.println("Email Accepted !");
        db.updateTable("Email", email, this.username);
        this.emailAddress = email;
    }
    
    private static boolean isValidEmail(String email){

        String regex = "[\\w-\\.+]*[\\w-\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
         
        return email.matches(regex);
    }

    public void setPhoneNumber() {
        DBFactory db = new DBFactory();

        Scanner sc = new Scanner (System.in) ;
        String number = "";

            do{
                System.out.print("Enter your phone number : ");
                number = sc.next();

                String regex = "\\d{10}";

                System.out.println("Your Number Phone is :" + number);
                System.out.println("Is the phone number Valid ? " + number.matches(regex));
                System.out.println("is the phone number in the system? " + db.searchTable("PhoneNumber", number));
                
                if(!number.matches(regex))
                {
                    System.out.println("Try Again");
                    continue;
                }
                
                if(!db.searchTable("PhoneNumber", number))
                    break;
            }while(true);
            System.out.println("Phone number Accepted !");
            db.updateTable("PhoneNumber", number, this.username);
        this.phoneNumber = number;
    }     

    public void setPassword() {
        DBFactory db = new DBFactory();
        Scanner sc = new Scanner(System.in);
        boolean firstPass = true;
        boolean isValid;
        System.out.print("Enter Password: ");
        String password = sc.nextLine();
        System.out.println("");
        System.out.print("Retype Password: ");
        String passwordRetype = sc.nextLine();
        System.out.println("");

        // Validate Password Requirements
        do {
            isValid = false;

            if (!firstPass) {
                System.out.print("Enter Password: "); password = sc.nextLine();
                System.out.print("Retype Password: "); passwordRetype = sc.nextLine();
            }

            firstPass = false;
            
            // Password Do Not Match
            if (!password.equals(passwordRetype)) {
                System.out.println("Passwords Do Not Match");
                continue;
            }

            // Password Is Invalid
            if (!isValidPassword(password)) {
                System.out.println("Password is invalid");
                continue;
            }

            isValid = true;

        } while (!isValid);
        
        this.password = password;
        db.updateTable("Password", password, this.username);
        System.out.println("Password Saved Successfully!");
    }

    private static boolean isValidPassword(String password) {
        
        boolean isValid = true;

        if (password.length() > 20 || password.length() < 8) {
            System.out.println("Password must be less than 20 and more than 8 characters in length.");
            isValid = false;
        }

        // Check Upper Case Match
        if (!password.matches("(.*[A-Z].*)")) {
            System.out.println("Password must have atleast one uppercase character");
            isValid = false;
        }

        // Check Lower Case Match
        if (!password.matches( "(.*[a-z].*)")) {
            System.out.println("Password must have atleast one lowercase character");
            isValid = false;
        }

        // Check Number Match
        if (!password.matches("(.*[0-9].*)")) {
            System.out.println("Password must have atleast one number");
            isValid = false;
        }
        
        // Check Special Characters Match
        // if (!password.matches("(.*[@,#,$,%].*$)")) {
        if (!password.matches("(.*[~,!,@,#,$,%,^,&,*,_,-,+,=,`,|,(,),{,},[,],:,;,\",\',<,>,,,.,?,/].*$)")) {
            System.out.println("Password must have atleast one special character among @#$%");
            isValid = false;
        }

        return isValid;
    }

    // Main
    public static void main(String[] args) throws InterruptedException, AWTException {
        
//        Scanner sc = new Scanner(System.in);
//        Registration u1 = new Registration();
//
//        u1.setPassword("xdfcgvhbjnk543i9&(&)", "xdfcgvhbjnk543i9&(&)");
//
//        sc.close();
    Registration r = new Registration();
    r.Main();
    r.clsNetbeans();
            
    
    }
    
    @Override
    public void Main()
    {
        Scanner sc = new Scanner(System.in);
        Registration register = new Registration();
        System.out.println("Welcome to The Register Page!");
        System.out.println("Please Provide The following Informations :D");
        register.setUserName();
        register.setPassword();
        register.setEmailAddress();
        register.setPhoneNumber();
        
        
        
        
        
        
    
        
    }
}
