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
    private boolean flag;

    // Constructor
    public Registration() {
    }

    // Methods
    public void setUserName() {
        PostFeature pf = new PostFeature();
        DBFactory db = new DBFactory();
        Scanner sc = new Scanner(System.in);
        boolean firstPass = true;
        boolean isValid;
        System.out.println("\t\t\t\t\t-------------------------------------\t\t");
        System.out.println("\n");
        System.out.print("Enter Username: ");
        String userName = sc.nextLine();

        // Validate Username Requirements
        do {
            isValid = false;

            if (!firstPass) {
                System.out.print("Username : "); userName  = sc.nextLine();
            }
            firstPass = false;
            // This username has been taken
            if (db.searchTable("Username", userName)) {
                System.out.println("Username is Already in The System! Choose Another");
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
        pf.addNewUserToPost(userName);
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
        
        do{
            System.out.print("Enter your Email Address: ");
            email = sc.next();
            if(isValidEmail(email) && !db.searchTable("Email", email))
                break;

            System.out.println("The Email you Inputted is Either Used or Is not a Valid Email!");
            
        }while(true);
        
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
                
                if(!number.matches(regex))
                {
                    System.out.println("The Phone Number you Inputted is Either Longer/Smaller");
                    continue;
                }
                else if(db.searchTable("PhoneNumber", number))
                {
                    System.out.println("This Phone Number is Already Used Try Again!");
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
        System.out.print("Retype Password: ");
        String passwordRetype = sc.nextLine();

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

    public String getUsername()
    {
        return this.username;
    }
    
    public TraceBack Main() throws InterruptedException, AWTException
    {
        System.out.println("****************");
        System.out.println("*   MockBook   *");
        System.out.println("****************\n");
        System.out.println("\t\t\t\t\t-------------------------------------\t\t");
        System.out.println("\t\t\t\t\t|          Registration Page         |");
        System.out.println("\t\t\t\t\t-------------------------------------\t\t");
        System.out.println("\n\t\t This is The Registeration Page! You Will be Presented With Two Options you May Choose From Them!");
        System.out.println("\n\t\t\t\t\t-------------------------------------\t\t");
        Registration register = new Registration();
        System.out.println("Type 1 to Start Registering your Account\nType 0 to Go Back to the Previous Page");
        System.out.println("\t\t\t\t\t-------------------------------------\t\t");
        Scanner in = new Scanner(System.in);
        String getInt = in.nextLine();
        TraceBack returnedTraceBack = null;
        
        breaker: while(true)
        {
            if(getInt.equals("1"))
            {
                register.setUserName();
                register.setPassword();
                register.setEmailAddress();
                register.setPhoneNumber();
                AccountSetUp setUpAccount = new AccountSetUp(register.getUsername());
                returnedTraceBack = setUpAccount;
                Thread.sleep(250);
                clearConsole();
                break breaker;
            }
            else if(getInt.equals("0"))
            {
                System.out.println("Redirecting You to The Entrance Page! ...");
                returnedTraceBack = this.previous;
                Thread.sleep(250);
                clearConsole();
                break breaker;
            }
            else
                System.out.println("This is NOT One of The Choices Given! Type Again :D");
            }
        
        
        return returnedTraceBack;
        
    }
    
}
