/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import com.mycompany.project.DB.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author HuSSon
 */
public class AccountSetUp {
    private String name, gender, job;
    private int birthDay, birthMonth, birthYear;

    public AccountSetUp() {
        // We can implement The CLI here if u want " or we can do it in on other file by using the functions of this class (more control) ".
        // I can add a choice if the person doesn't want to answer some of the questions to make it in the DB as null instead if u want. 
    }

        
    public void setName(String name, String username) {
        // no need to check if in the system since it's for show only not the username for making the account.
        this.name = username;
        DBFactory tableManager = new DBFactory();
        tableManager.columnAdder("Name", "varchar(255)"); // checks if there is a table for the Name if not make one.
        tableManager.updateTable("Name", name, username); // update the table if there is one with the new name for the username
    }
    
    public String getName()
    {
        return this.name;
    }

    public void setGender() {
        // Validate Gender:
        Scanner inStream = new Scanner(System.in);
        System.out.println("Please Provide Your Gender: Male, Female and Other: ");
        String gender = inStream.nextLine();
        
        
        while(true){
            if(!(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female") || gender.equalsIgnoreCase("other")))
            {
                System.out.println("Please Enter an Appropiate Gender: Male , Female or Other: ");
                gender = inStream.nextLine();
            }
            else
                break;
        }
        this.gender = gender;
        
        DBFactory tableManager = new DBFactory();
        tableManager.columnAdder("Gender", "varchar(255)"); // checks if there is a table for the Gender if not make one.
        tableManager.updateTable("Gender", this.gender, this.name); // update the table if there is one with the new gender for the username
        
    }
    
    public String getGender()
    {
        return this.gender;
    }

    public void setBirthday() {
        Scanner inStream = new Scanner(System.in);
        System.out.println("Please Provide Your Birthday in this format: day/month/year All in integers");
        String holder = inStream.nextLine();
        int birthday = Integer.parseInt(holder.substring(0, holder.indexOf("/"))); // takes the first integers before the first / 
        int birthmonth = Integer.parseInt(holder.substring(holder.indexOf("/"), holder.lastIndexOf("/"))); // takes the integers between the first / and the second /
        int birthyear = Integer.parseInt(holder.substring(holder.lastIndexOf("/"))); // takes the integers after the second //

        if(!birthValidator(birthday , birthmonth , birthyear)) // check function below
        {
            System.out.println("There is an issue with your birthday ! try again");
            return;
        }
        
        int age = ageCalc(this.birthDay,this.birthMonth,this.birthYear); // check function below
        
        DBFactory tableManager = new DBFactory();
        tableManager.columnAdder("Age", "INT"); // checks if there is a table for the Age if not make one.
        tableManager.updateTable("Age", age, this.name); // update the table if there is one with the new age for the username
        
    }
    
    public String getBirthday()
    {
        return this.birthDay + "/" + this.birthMonth + "/" + this.birthYear;
    }


    // i will remake those when i see the implementaion of the add friends feature.
//    public int getNoOfFriends()
//    {
//        return this.NoOfFriends;
//    }
//    
//    // Increment every time the user adds a friend
//    public void NoOfFriendsAdder() {
//        this.NoOfFriends =+ getNoOfFriends();
//    }


    public void setJob(String jobs) {
        DBFactory tableManager = new DBFactory();
        tableManager.columnAdder("Job", "varchar(255)"); // checks if there is a table for the Name if not make one.
        tableManager.updateTable("Job", jobs, this.name); // update the table if there is one with the new job for the username
        this.job = jobs;
    }
    
    public String getJob()
    {
        return this.job;
    }
    
    
    public boolean birthValidator(int birthday, int birthmonth, int birthyear)
    {
        //Scanner inStream = new Scanner(System.in);
        try{
            if(birthmonth < 10){ // if birth month is bigger than 10 we don't add 0 before the month. ex: 01 02 but not 011
                LocalDate valid = LocalDate.parse(birthyear+"-0"+ birthmonth + "-"+birthday);
            }
            LocalDate valid = LocalDate.parse(birthyear+"-"+ birthmonth + "-"+birthday);
            return true;
        }
        catch(DateTimeParseException e) // throws an exception if the date is wrong
        {
            System.out.println("Your date is not Valid!! check again");
        }
        return false;
        
        // this is another implementaion that i did but i didn't finish the days . if u want we can use this instead ?
        
        // Month Validation
//        while(true)
//        {
//            
//            if(birthmonth > 12 || birthmonth < 1)
//            {
//                System.out.println("Please Enter an Valid Number for Your Month Between 1 and 12");
//                birthmonth = inStream.nextInt();
//            }
//            else
//                break;
//        }        
//       
//        
//        // year validation
//        while(true)
//        {
//            
//            if(birthyear > 2023 || birthyear < 1907)
//            {
//                System.out.println("Please Enter an Valid Number for Your Year Between 1907 and 2023");
//                birthyear = inStream.nextInt();
//            }
//            else
//                break;
//        }        
//        
//        // day validation
//        LocalDate year = LocalDate.ofYearDay(birthyear, birthday);
//        while(true)
//        {
//            if(birthday < 0 || birthday > 31)
//            {
//                System.out.println("Please Enter an Valid Number for Your Day Between 1 and 31");
//                birthday = inStream.nextInt();
//            }
//            else if(birthday > 28 && birthmonth == 2 && !year.isLeapYear())
//            {
//                System.out.println("Please Enter an Valid Number for Your Month Between 1 and 28");
//                birthday = inStream.nextInt();
//            }
//            else if(!(birthday < 30) && birthmonth == 2 && year.isLeapYear())
//            {
//                System.out.println("Please Enter an Valid Number for Your Month Between 1 and 29");
//                birthday = inStream.nextInt();
//            }
//            else
//                break;
//        }
    }
 
    
    public int ageCalc(int birthday, int birthmonth, int birthyear)
    {
        LocalDate today = LocalDate.now(); // Today's date so we can combare between
        LocalDate birth = LocalDate.of(birthyear, birthmonth, birthday); // Birth date
        Period age = Period.between(birth, today); // gets the period between the birthdate and the date of today
        return age.getYears(); // gets only the years. " no days or months" 
    }
    
    
    
    
    
}
