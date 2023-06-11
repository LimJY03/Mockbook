/**
 * @author HuSSon
 */
package Registration;

import com.mycompany.project.DB.*;
import java.awt.AWTException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountSetUp extends TraceBack {

    private String name, gender, job, hobbies, username, address;
    private int birthDay, birthMonth, birthYear, age;

    public AccountSetUp(String user) {

        // We can implement The CLI here if u want " or we can do it in on other file by
        // using the functions of this class (more control) ".
        // I can add a choice if the person doesn't want to answer some of the questions
        // to make it in the DB as null instead if u want.
        setUsername(user);
    }

    public TraceBack Main() throws InterruptedException, AWTException {

        System.out.println("New Member Alert!");
        System.out.println("Redirecting you to Setup your Account!");
        
        setName();
        Thread.sleep(500);
        
        setGender();
        Thread.sleep(500);
        
        while (true) {
            boolean flag = setBirthday();
            Thread.sleep(500);
            if (flag == true) break;
        }

        setJob();
        Thread.sleep(500);
        
        setHobbies(addHobbies());
        Thread.sleep(500);
        
        setAddress();

        return new EnterMockBook();
    }
    
    public void setName() {
        
        DBFactory tableManager = new DBFactory();
        
        System.out.println("\n\n--------------------------------------");
        System.out.print("Enter your Display name: ");
        
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();

        this.name = insertInto("Name", "varchar(255)", name, this.username);
    }

    public void setGender() {
        
        // Validate Gender
        Scanner sc = new Scanner(System.in);
        System.out.print("\n\nPlease Provide Your Gender: Male, Female or Other: ");
        String gender = sc.nextLine();
        System.out.println();
        
        while (true) {
            if (!(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female") || gender.equalsIgnoreCase("other"))) {
                
                System.out.print("Please Enter an Appropiate Gender: Male, Female or Other: ");
                gender = sc.nextLine();
                System.out.println();
                
            } else break;
        }
        
        this.gender = insertInto("Gender", "varchar(255)", gender, this.username);
    }
    
    public void setUsername(String user) { this.username = user; }
    public String getName() { return this.name; }
    public String getGender() { return this.gender; }
    public int getAge() { return this.age; }
    public String getBirthday() { return this.birthDay + "/" + this.birthMonth + "/" + this.birthYear; }
    public String getJob() { return this.job; }

    public boolean setBirthday() {

        Scanner sc = new Scanner(System.in);

        do {            
            System.out.print("\n\nPlease Provide Your Birthday in this format: day/month/year All in integers: ");
            String[] bd = sc.nextLine().replaceAll("\\s", "").split("/");

            if (bd.length == 3) break;

        } while (true);

        this.birthDay = Integer.parseInt(bd[0]);
        this.birthMonth = Integer.parseInt(bd[1]);
        this.birthYear = Integer.parseInt(bd[2]);

        // check function below
        if (!birthValidator(bd, bm, by)) {
            System.out.println("\nThere is an issue with your birthday! Try again");
            return false;
        }

        // check function below
        int age = ageCalc(this.birthDay, this.birthMonth, this.birthYear);

        insertInto("Age", "INT", age, this.username);

        return true;
    }    

    // i will remake those when i see the implementaion of the add friends feature.
    // public int getNoOfFriends()
    // {
    // return this.NoOfFriends;
    // }
    //
    // // Increment every time the user adds a friend
    // public void NoOfFriendsAdder() {
    // this.NoOfFriends =+ getNoOfFriends();
    // }

    public void setJob() {

        Scanner sc = new Scanner(System.in);

        System.out.print("\n\nInput Your Job: ");
        String job = sc.nextLine();
        this.job = insertInto("Job", "varchar(255)", job, this.username);
    }

    public boolean birthValidator(String birthday, String birthmonth, String birthyear) {
        
        // throws an exception if the date is wrong
        try {
            if (Integer.parseInt(birthmonth) < 10) birthmonth = "0" + birthmonth;
            if (Integer.parseInt(birthday) < 10) birthday = "0" + birthday;

            LocalDate valid = LocalDate.parse(birthyear + "-" + birthmonth + "-" + birthday);

            return true;
        } 
        catch (DateTimeParseException e) {
            System.out.println("Your date is not Valid!! check again");
        }
        
        return false;
    }

    public int ageCalc(int birthday, int birthmonth, int birthyear) {

        // Today's date so we can compare between
        LocalDate today = LocalDate.now(); 
        LocalDate birth = LocalDate.of(birthyear, birthmonth, birthday);

        // gets the period between the birthdate and the date of today
        Period age = Period.between(birth, today);

        // gets only the years. " no days or months"
        this.age = age.getYears();
        return this.age; 
    }

    public String addHobbies() {

        ArrayList<String> hobbies = new ArrayList<String>();

        System.out.println("\n\nInput your hobbies: ");

        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Write your hobbies one after the other, q to stop");
            String hobbyInput = sc.nextLine();
            if (hobby.equalsIgnoreCase("q")) break;
            hobbies.add(hobbyInput);
        }

        String hobby = "";

        for (int i = 0; i < hobbies.size(); i++) hobby += hobbies.get(i) + ",";

        return hobby.substring(0, hobby.length() - 1);
    }

    public void setHobbies(String hobbies) {
        this.hobbies = insertInto("Hobbies", "varchar(255)", hobbies, this.username);
    }

    public String getHobbies() { return this.hobbies; }

    public void setAddress() {
        
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\n--------------------------------------");
        System.out.print("Enter your Address: ");
        String address = sc.nextLine();
        this.address = insertInto("Address", "varchar(255)", address, this.username);
    }

    public String getAddress() { return this.address; }

    public static String insertInto(String col, String type, String element, String username) {

        DBFactory tableManager = new DBFactory();

        // checks if there is a table for the element if not make one.
        tableManager.columnAdder(col, type);

        // update the table if there is one with the new job for the username
        tableManager.updateTable(col, element, username);       

        System.out.printf("Your %s Has Been Registered!", col);
        System.out.println("--------------------------------------");

        return element;
    }
}
