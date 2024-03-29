package Registration;

import MainProgram.MainProgram;
import TraceBack.TraceBack;
import Display.Display;
import java.awt.AWTException;
import java.util.regex.*;

public class Registration extends TraceBack{

    // Properties
    private String username, emailAddress, phoneNumber, password;

    // Methods
    public void setUserName() {
        boolean firstPass = true;
        boolean isValid;
        System.out.print("Enter Username: ");
        String userName = MainProgram.sc.nextLine();

        // Validate Username Requirements
        do {
            isValid = false;

            if (!firstPass) {
                System.out.print("Username : "); 
                userName = MainProgram.sc.nextLine();
            }

            firstPass = false;

            // Username Accepted
            if (!isValidUsername(userName)) {
                continue;
            }

            isValid = true;

        } while (!isValid);
        
        System.out.print("Username Accepted !");
        MainProgram.db.addNewUser(userName);
        this.username = userName;
    }
    
    public static boolean isValidUsername(String Username) {
        
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

        if(Username.contains(" ")) {
            System.out.println("Username Should NOT Contain Spaces!");
            isValid = false;
        }
            
        // This username has been taken
        if (MainProgram.db.searchTable("Username", Username)) {
            System.out.println("Username has been taken");
            isValid = false;
        }

        return isValid;
    }

    public void setEmailAddress() {
        String email = "";
        boolean valid = true;
        
        do{
            System.out.print("\nEnter your Email Address: ");
            email = MainProgram.sc.nextLine();
            if (!isValidEmail(email)) {
                System.out.println("Invalid Email Address!");
                System.out.println("\nTry Again");
            } else if (MainProgram.db.searchTable("Email", email.toLowerCase())) {
                System.out.println("Email Already Registered! Login or Use A New Email");
                System.out.println("\nTry Again");
                continue;
            } else break;

        } while (valid);

        System.out.print("Email Accepted !");
        MainProgram.db.updateTable("Email", email, this.username);
        
        this.emailAddress = email;
    }
    
    public static boolean isValidEmail(String email){
        return email.matches("[\\w-\\.+]*[\\w-\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
    }

    public void setPhoneNumber() {
        String number = "";

        do{
            System.out.print("\nEnter your phone number : ");
            number = MainProgram.sc.nextLine();

            String numRegex = "\\d{10,11}";

            if (!number.matches(numRegex)) {
                System.out.println("Phone Number Should Contain Only 10 to 11 Digits!!");
                System.out.println("\nTry Again");
            } else if (MainProgram.db.searchTable("PhoneNumber", number)) {
                System.out.println("Number Already Registered! Please Enter A New Number");
                System.out.println("\nTry Again");
            } else break;

        } while (true);

        System.out.print("Phone number Accepted !");
        MainProgram.db.updateTable("PhoneNumber", number, this.username);
        
        this.phoneNumber = number;
    }     

    public void setPassword() {      

        this.password = setNewPassword(this.username);
    }

    public static String setNewPassword(String username) {
        
        boolean firstPass = true;
        boolean isValid = false;
        System.out.print("\nEnter Password: ");
        String password = MainProgram.sc.nextLine();
        System.out.print("Retype Password: ");
        String passwordRetype = MainProgram.sc.nextLine();

        // Validate Password Requirements
        do {
            if (!firstPass) {
                System.out.print("\nEnter Password: "); password = MainProgram.sc.nextLine();
                System.out.print("Retype Password: "); passwordRetype = MainProgram.sc.nextLine();
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
        
        password = PasswordEncrypt.encryptSHA256(password, username);
        
        MainProgram.db.updateTable("Password",password, username);
        System.out.print("Password Saved Successfully!");

        return password;
    }

    public static boolean isValidPassword(String password) {
        
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
        
        Registration register = new Registration();
        TraceBack returnedTraceBack = null;
        Display.displayWelcomeLines("Registration Page", "Registration Page", "");
        Display.displayUserOption("Start Registering", "", "");
        String getInt = MainProgram.sc.nextLine();

        breaker: while(true)
        {
            if(getInt.equals("1"))
            {
                register.setUserName();
                register.setPassword();

                // Create new private key for user
                PrivateKey.createPrivateKey(register.getUsername());

                register.setEmailAddress();
                register.setPhoneNumber();
                AccountSetUp setUpAccount = new AccountSetUp(register.getUsername());
                returnedTraceBack = setUpAccount;
                Thread.sleep(250);
                clearConsole();
                break breaker;
            }
            else if(getInt.equals("0")) {
                System.out.println("Redirecting You to The Entrance Page! ...");
                this.previous.isPrevious = true;
                returnedTraceBack = this.previous;
                Thread.sleep(250);
                clearConsole();
                break breaker;
            }
            else {
                System.out.println("This is NOT One of The Choices Given! Type Again :D");
                getInt = MainProgram.sc.nextLine();
            }
        }
        
        return returnedTraceBack;  
    }
}
