package Registration;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import Display.Display;
import MainProgram.MainProgram;
import java.awt.AWTException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import MainProgram.EnterMockBook;
import TraceBack.TraceBack;

/**
 *
 * @author HuSSon
 */
public class AccountSetUp extends TraceBack{
    private String gender, job, hobbies,username, Address;
    private int birthDay, birthMonth, birthYear,age;

    public AccountSetUp(String user) {
        // We can implement The CLI here if u want " or we can do it in on other file by using the functions of this class (more control) ".
        // I can add a choice if the person doesn't want to answer some of the questions to make it in the DB as null instead if u want. 
        setUsername(user); 
    }
   
    
    
    public TraceBack Main() throws InterruptedException, AWTException
    {
        Display.displayProgramPage("Account Setup Page");
        setGender();
        while(true)
        {
            boolean flag = setBirthday();
            if(flag== true)
                break;
        }
        
        setJob();
        setHobbies(addHobbies());
        setAddress();
        System.out.println("Redirecting You to The Entrance Page! ...");
        Thread.sleep(250);
        clearConsole();
        return new EnterMockBook();
    }

    public void setUsername(String user)
    {
        this.username = user;
    }

    public void setGender() {
        // Validate Gender:
        System.out.print("Please Provide Your Gender: Male, Female or Other: ");
        String gender = MainProgram.sc.nextLine();
        System.out.println("");
        
        while(true){
            if(!(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female") || gender.equalsIgnoreCase("other")))
            {
                System.out.print("Please Enter an Appropiate Gender: Male , Female or Other: ");
                gender = MainProgram.sc.nextLine();
                System.out.println("");
            }
            else
                break;
        }
        this.gender = gender;
        
        // MainProgram.db.columnAdder("Gender", "varchar(255)"); // checks if there is a table for the Gender if not make one.
        MainProgram.db.updateTable("Gender", this.gender, this.username); // update the table if there is one with the new gender for the username
        System.out.println("Gender has been registerd!");
        System.out.println("---------------------------------------");
    }
    
    public String getGender()
    {
        return this.gender;
    }
    
    public boolean setBirthday() {
        System.out.print("Please Provide Your Birthday in this format: day/month/year (ALL IN INTEGERS): ");
        String holder = MainProgram.sc.nextLine();
        System.out.println("");
        
        while(true)
        {
            if(!holder.matches("\\d{1,2}/\\d{1,2}/\\d{4}"))
            {
                System.out.print("Invalid Foramt! Correct Formate is Day/Month/Year. Try Again");
                holder = MainProgram.sc.nextLine();
                continue;
            }
            break;
        }
        
        String bd = holder.substring(0, holder.indexOf("/"));
        String by = holder.substring(holder.lastIndexOf("/")+1);
        String bm = holder.substring(holder.indexOf("/")+1, holder.lastIndexOf("/"));
        
        int birthday = Integer.parseInt(bd); // takes the first integers before the first / 
        int birthyear = Integer.parseInt(by); // takes the integers after the second //
        int birthmonth = Integer.parseInt(bm); // takes the integers between the first / and the second /
        this.birthDay = birthday;
        this.birthMonth = birthmonth;
        this.birthYear = birthyear;
        

        if(!birthValidator(bd , bm , by)) // check function below
        {
            System.out.println("There is an issue with your birthday ! try again");
            return false;
        }
        
        int age = ageCalc(this.birthDay,this.birthMonth,this.birthYear); // check function below
        
        //MainProgram.db.columnAdder("Age", "INT"); // checks if there is a Column for the Age if not make one.
        MainProgram.db.updateTable("Age", age, this.username); // update the table if there is one with the new age for the username
        //MainProgram.db.columnAdder("Birthday", "varchar(12)"); // checks if there is a column for the Birthday if not make one.
        MainProgram.db.updateTable("Birthday", getBirthday(), this.username); // update the table if there is one with the new birthday for the username
        System.out.println("Birthday has been Calculated and Registerd!");
        System.out.println("---------------------------------------");
        
        return true;
    }
    
    public boolean birthValidator(String birthday, String birthmonth, String birthyear)
    {
        try{ // check if the month/day is lower than 10 so its going to be single digit we have to add 0 next to it
            // also check if the user typed 0 as a digit as well so we don't add a third one
            if(Integer.parseInt(birthmonth) < 10 && !(birthmonth.substring(0,1).equals("0")))
                birthmonth = "0" + birthmonth;
            if(Integer.parseInt(birthday) < 10 && !(birthday.substring(0,1).equals("0")))
                birthday = "0"+birthday;
            if(Integer.parseInt(birthyear) < 1900)
            {
                System.out.println("Please Provide Different Year!");
                return false;
            }
                
            LocalDate valid = LocalDate.parse(birthyear+"-"+ birthmonth + "-"+birthday);
            return true;
        }
        catch(DateTimeParseException e) // throws an exception if the date is wrong " typed 13 for month for examople or used a date we didn't reach yet
        {
            System.out.println("Your Birthday is not Valid, Try Inputting it Again.");
        }
        return false;
        
    }
 
    
    public int ageCalc(int birthday, int birthmonth, int birthyear)
    {
        LocalDate today = LocalDate.now(); // Today's date so we can combare between
        LocalDate birth = LocalDate.of(birthyear, birthmonth, birthday);
        Period age = Period.between(birth, today); // gets the period between the birthdate and the date of today
        this.age = age.getYears();
        return age.getYears(); // gets only the years. " no days or months" 
       
    }
    
    public int getAge()
    {
        return this.age;
    }
    
    public String getBirthday()
    {
        return this.birthDay + "/" + this.birthMonth + "/" + this.birthYear;
    }

    public void setJob() {
        //MainProgram.db.columnAdder("Job", "varchar(255)"); // checks if there is a Column for Job if not make one.
        System.out.print("Input Your Job: ");
        String getString = MainProgram.sc.nextLine();
        
        MainProgram.db.updateTable("Job", getString, this.username); // update the table if there is one with the new job for the username
        this.job = getString;
        System.out.println("Job has Been Registerd!");
        System.out.println("---------------------------------------");
    }
    
    public String getJob()
    {
        return this.job;
    }

    public String addHobbies(){
        ArrayList<String> hobbie = new ArrayList<String>();
        System.out.println("Write your hobbies one after the other, q to stop");
        while(true)
        {
            String h = MainProgram.sc.nextLine();
            if(h.equalsIgnoreCase("q"))
                break;
            hobbie.add(h);
        }
        String hobby = "";
        for(int i = 0; i < hobbie.size();i++)
        {
            hobby+= hobbie.get(i) +",";
        }
        if(hobby == " ")
            hobby = "None";
        hobby = hobby.substring(0, hobby.length() - 1); 
        System.out.println("Hobbies Have been Registerd!");
        System.out.println("---------------------------------------");
        return hobby;
    }
    
    public void setHobbies(String hobbies)
    {
        //MainProgram.db.columnAdder("Hobbies", "varchar(500)"); // checks if there is a Column for Hobbies if not make one.
        MainProgram.db.updateTable("Hobbies", hobbies, this.username); // update the table if there is one with the new hobbies for the username
        this.hobbies = hobbies;
    }
    
    public String getHobbies()
    {
        return this.hobbies;
    }
    
    public void setAddress()
    {
        System.out.print("Enter your Address: ");
        String in = MainProgram.sc.nextLine();
        System.out.println("");
        System.out.println("Your Address Has Been Setup!");
        System.out.println("---------------------------------------");
        //MainProgram.db.columnAdder("Address", "varchar(255)"); // checks if there is a Column for the Address if not make one.
        MainProgram.db.updateTable("Address", in, this.username); // update the table if there is one with the new address for the username
        this.Address = in;
    }
    
    public String getAddress()
    {
        return this.Address;
    }
    
    
    
    
    
    
}