package Registration;

import MyDataBase.DBFactory;
import PostFeature.PostFeature;
import TraceBack.TraceBack;

import java.awt.AWTException;
import java.util.Scanner;
import java.util.regex.*;

public class Registration extends TraceBack{

    // Properties
    private String username, emailAddress, phoneNumber, password;
    private boolean flag;

    // Methods
    public void setUserName() {
        PostFeature pf = new PostFeature();
        DBFactory db = new DBFactory();
        Scanner sc = new Scanner(System.in);
        boolean firstPass = true;
        boolean isValid;
        System.out.print("Enter Username: ");
        String userName = sc.nextLine();
        System.out.println();

        // Validate Username Requirements
        do {
            isValid = false;

            if (!firstPass) {
                System.out.print("Username : "); 
                userName  = sc.nextLine();
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
        pf.addNewUserToPost(userName);
        this.username = userName;
        System.out.println(this.username);
    }
    
    private static boolean isValidUsername(String Username) {
        
        boolean isValid = true;
        final Pattern textPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z]).+$");
        
        if (Username.length() > 25 || Username.length() < 5) {
            System.out.println("Username must be less than 25 and more than 5 characters in length.");
            isValid = false;
        }

        // Check Upper Case Match
        if (!textPattern.matcher(Username).matches()) {
            System.out.println("Username must have at least one uppercase / one lowercase character");
            isValid = false;
        }

        return isValid;
    }

    public void setEmailAddress() {
        
        Scanner sc = new Scanner(System.in);
        DBFactory db = new DBFactory();
        
        String email = "";
        boolean valid = true;
        
        do{
            System.out.print("Enter your Email Address: ");
            email = sc.nextLine();

            System.out.println("\nYour Email address : " + email);

            if (!isValidEmail(email)) {
                System.out.println("Invalid Email Address!");
                System.out.println("\nTry Again");
            } else if (db.searchTable("Email", email)) {
                System.out.println("Email Already Registered! Login or Use A New Email");
                System.out.println("\nTry Again");
            } else break;

        } while (valid);

        System.out.println("Email Accepted !");
        db.updateTable("Email", email, this.username);
        
        this.emailAddress = email;
    }
    
    private static boolean isValidEmail(String email){
        return email.matches("[\\w-\\.+]*[\\w-\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
    }

    public void setPhoneNumber() {

        DBFactory db = new DBFactory();

        Scanner sc = new Scanner (System.in) ;
        String number = "";

        do{
            System.out.print("Enter your phone number : ");
            number = sc.nextLine();

            String numRegex = "\\d{10}";

            System.out.println("Your Number Phone is :" + number);

            if (!number.matches(numRegex)) {
                System.out.println("Phone Number Contains Only 10 Digits!!");
                System.out.println("\nTry Again");
            } else if (db.searchTable("PhoneNumber", number)) {
                System.out.println("Number Already Registered! Please Enter A New Number");
                System.out.println("\nTry Again");
            } else break;

        } while (true);

        System.out.println("Phone number Accepted !");
        db.updateTable("PhoneNumber", number, this.username);
        
        this.phoneNumber = number;
    }     

    public void setPassword() {

        DBFactory db = new DBFactory();
        Scanner sc = new Scanner(System.in);
        boolean firstPass = true;
        boolean isValid = false;
        System.out.print("Enter Password: ");
        String password = sc.nextLine();
        System.out.println("");
        System.out.print("Retype Password: ");
        String passwordRetype = sc.nextLine();
        System.out.println("");

        // Validate Password Requirements
        do {
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
        
        db.updateTable("Password", password, this.username);
        System.out.println("Password Saved Successfully!");

        this.password = password;
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
        if (!password.matches("(.*[~,!,@,#,$,%,^,&,*,_,-,+,=,`,|,(,),{,},[,],:,;,\",\',<,>,,,.,?,/].*$)")) {
            System.out.println("Password must have atleast one special character among @#$%");
            isValid = false;
        }

        return isValid;
    }

    public String getUsername() {
        return this.username;
    }
    
    public TraceBack Main() throws InterruptedException, AWTException {
        
        Scanner sc = new Scanner(System.in);
        Registration register = new Registration();
        
        System.out.println("Welcome to The Register Page!");
        System.out.println("Please Provide The following Informations :D so we can register you as a new member!");
        
        register.setUserName();
        register.setPassword();
        register.setEmailAddress();
        register.setPhoneNumber();
        
        System.out.println("Done! Going Back to The previous page.....");
        System.out.println(register.getUsername());

        AccountSetUp setUpAccount = new AccountSetUp(register.getUsername());
        
        return setUpAccount;   
    }
}