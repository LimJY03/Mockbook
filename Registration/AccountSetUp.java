/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import com.mycompany.project.DB.*;
import java.awt.AWTException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author HuSSon
 */
public class AccountSetUp extends TraceBack{
    private String name, gender, job, hobbies,username, Address;
    private int birthDay, birthMonth, birthYear,age;

    public AccountSetUp(String user) {
        // We can implement The CLI here if u want " or we can do it in on other file by using the functions of this class (more control) ".
        // I can add a choice if the person doesn't want to answer some of the questions to make it in the DB as null instead if u want. 
        setUsername(user); 
    }
   
    
    
    public TraceBack Main() throws InterruptedException, AWTException
    {
        System.out.println("New Member Alert!");
        System.out.println("Redirecting you to Setup your Account!");
        setName();
        Thread.sleep(500);
        setGender();
        Thread.sleep(500);
        while(true)
        {
            boolean flag = setBirthday();
            Thread.sleep(500);
            if(flag== true)
                break;
        }
        
        setJob();
        Thread.sleep(500);
        setHobbies(addHobbies());
        Thread.sleep(500);
        setAddress();
        
        return new EnterMockBook();
    }

    public void setUsername(String user)
    {
        this.username = user;
    }
    public void setName() {
        DBFactory tableManager = new DBFactory();
        System.out.println("\n\n");
        System.out.println("--------------------------------------");
        System.out.print("Enter your Display name: ");
        Scanner i = new Scanner(System.in);
        String in = i.nextLine();
        System.out.println("");
        tableManager.columnAdder("Name", "varchar(255)"); // checks if there is a table for the Name if not make one.
        tableManager.updateTable("Name", in, this.username); // update the table if there is one with the new name for the username
        System.out.println("Your Display name Has been SetUP!");
        System.out.println("--------------------------------------");
        

    }
    
    public String getName()
    {
        return this.name;
    }

    public void setGender() {
        // Validate Gender:
        Scanner inStream = new Scanner(System.in);
        System.out.println("\n\n");
        System.out.print("Please Provide Your Gender: Male, Female or Other: ");
        String gender = inStream.nextLine();
        System.out.println("");
        
        while(true){
            if(!(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female") || gender.equalsIgnoreCase("other")))
            {
                System.out.print("Please Enter an Appropiate Gender: Male , Female or Other: ");
                gender = inStream.nextLine();
                System.out.println("");
            }
            else
                break;
        }
        this.gender = gender;
        
        DBFactory tableManager = new DBFactory();
        tableManager.columnAdder("Gender", "varchar(255)"); // checks if there is a table for the Gender if not make one.
        tableManager.updateTable("Gender", this.gender, this.username); // update the table if there is one with the new gender for the username
        System.out.println("Gender has been registerd!");
        System.out.println("--------------------------------------");
    }
    
    public String getGender()
    {
        return this.gender;
    }

    public boolean setBirthday() {
        Scanner inStream = new Scanner(System.in);
        System.out.println("\n\n");
        System.out.print("Please Provide Your Birthday in this format: day/month/year All in integers: ");
        String holder = inStream.nextLine();
        System.out.println("");
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
        
        DBFactory tableManager = new DBFactory();
        tableManager.columnAdder("Age", "INT"); // checks if there is a table for the Age if not make one.
        tableManager.updateTable("Age", age, this.username); // update the table if there is one with the new age for the username
        System.out.println("Birthday has been Calculated and Registerd!");
        System.out.println("--------------------------------------");
        return true;
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


    public void setJob() {
        DBFactory tableManager = new DBFactory();
        tableManager.columnAdder("Job", "varchar(255)"); // checks if there is a table for the Name if not make one.
        Scanner i = new Scanner(System.in);
        System.out.println("\n\n");
        System.out.print("Input Your Job: ");
        String getString = i.nextLine();
        
        tableManager.updateTable("Job", getString, this.username); // update the table if there is one with the new job for the username
        this.job = getString;
        System.out.println("Job has Been Registerd!");
        System.out.println("--------------------------------------");
    }
    
    public String getJob()
    {
        return this.job;
    }
    
    
    public boolean birthValidator(String birthday, String birthmonth, String birthyear)
    {
        //Scanner inStream = new Scanner(System.in);
        try{
            if(Integer.parseInt(birthmonth) < 10)
                birthmonth = "0" + birthmonth;
            if(Integer.parseInt(birthday) < 10)
                birthday = "0"+birthday;
                
            LocalDate valid = LocalDate.parse(birthyear+"-"+ birthmonth + "-"+birthday);
            return true;
        }
        catch(DateTimeParseException e) // throws an exception if the date is wrong
        {
            System.out.println("Your date is not Valid!! check again");
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
    
    public String addHobbies(){
        ArrayList<String> hobbie = new ArrayList<String>();
        System.out.println("\n\n");
        System.out.println("Input your hobbies: ");
        while(true)
        {
            Scanner i = new Scanner(System.in);
            System.out.println("Write your hobbies one after the other, q to stop");
            String h = i.nextLine();
            if(h.equalsIgnoreCase("q"))
                break;
            hobbie.add(h);
        }
        String hobby = "";
        for(int i = 0; i < hobbie.size();i++)
        {
            hobby+= hobbie.get(i) +",";
        }
        hobby = hobby.substring(0, hobby.length() - 1); 
        System.out.println("Hobbies Have been Registerd!");
        System.out.println("--------------------------------------");
        return hobby;
    }
    
    public void setHobbies(String hobbies)
    {
        DBFactory tableManager = new DBFactory();
        tableManager.columnAdder("Hobbies", "varchar(255)"); // checks if there is a table for the Name if not make one.
        tableManager.updateTable("Hobbies", hobbies, this.username); // update the table if there is one with the new job for the username
        this.hobbies = hobbies;
    }
    
    public String getHobbies()
    {
        return this.hobbies;
    }
    
    public void setAddress()
    {
        DBFactory tableManager = new DBFactory();
        System.out.println("\n\n");
        System.out.println("--------------------------------------");
        System.out.print("Enter your Address: ");
        Scanner i = new Scanner(System.in);
        String in = i.nextLine();
        System.out.println("");
        System.out.println("Your Address Has Been Setup!");
        System.out.println("--------------------------------------");
        tableManager.columnAdder("Address", "varchar(255)"); // checks if there is a table for the Name if not make one.
        tableManager.updateTable("Address", in, this.username); // update the table if there is one with the new job for the username
        this.Address = in;
    }
    
    public String getAddress()
    {
        return this.Address;
    }
    
    
    
    
    
    
}
