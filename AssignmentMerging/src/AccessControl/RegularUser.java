package AccessControl;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class RegularUser extends User {

    private String contact, gender, email, job, hobbies, address, name;
    private int age, connection, id, numberOfFriends;
    private LocalDate birthday;
    private ArrayList<String> connection1 = new ArrayList<>();
    private ArrayList<String> connection2 = new ArrayList<>();
    private ArrayList<String> connection3plus = new ArrayList<>();

    public RegularUser(String username, String email, String contact, int age, String gender, String p) {
        super(username, p);
        this.email = email;
        this.contact = contact;
        this.age = age;
        this.gender = gender;
    }
    
    public RegularUser(String username, String email, String contact, int age, String gender, String p, String job, String hobbies, String address) 
    {
        this(username, email, contact, age, gender, p);
        this.job = job;
        this.hobbies = hobbies;
        this.address = address;
    }
    
    public RegularUser(String username, String email, String contact, int age, String gender, String p, String job, String hobbies, String address, LocalDate birthday) 
    {
    	this(username, email, contact, age, gender, p,job,hobbies,address);
    	this.birthday=birthday;
    }
    
    public RegularUser(String username, String email, String contact, int age, String gender, String p, String job, String hobbies, String address, LocalDate birthday, int id, String name, int numberOfFriends) 
    {
    	this(username, email, contact, age, gender, p, job, hobbies, address, birthday);
        this.id = id;
        this.name = name;
    }

    public void setConnection(int connection) {
        this.connection = connection;
    }

    public String getUsername() {
        return super.getUsername();
    }

    public String getEmail() {
        return this.email;
    }

    public String getGender() {
        return this.gender;
    }

    public String getContact() {
        return this.contact;
    }

    public int getAge() {
        return this.age;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getJob() {
        return this.job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getHobbies() {
        return this.hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public LocalDate getBirthday() {
    	return this.birthday;
    }
    
    public void setBirthday(LocalDate birthday) {
    	this.birthday = birthday;
    }

    public int calculateAge() {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthday, currentDate);
        return period.getYears();
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(){
        this.name = name;
    }
    
    public int getNumberOfFriends() {
        return numberOfFriends;
    }
    
    public void setNumberOfFriends(){
        this.numberOfFriends = numberOfFriends;
    }
    
    public ArrayList<String> getConnection1() {
        return this.connection1;
    }

    public ArrayList<String> getConnection2() {
        return this.connection2;
    }

    public ArrayList<String> getConnection3plus() {
        return this.connection3plus;
    }
    
    public void viewAccount(int connectionDegree) {
        System.out.println("Name: " + getName());
        System.out.println("Username: " + getUsername());
        System.out.println("Email Address: " + getEmail());
        System.out.println("Phone Number: " + getContact());
        System.out.println("Birthday: " + getBirthday());
        System.out.println("Age: " + calculateAge());
        System.out.println("Address: " + getAddress());
        System.out.println("Gender: " + getGender());
        System.out.println("Number of Friends: " + getNumberOfFriends());
        System.out.println("Hobbies: " + getHobbies());
        System.out.println("Jobs: " + getJob());
        System.out.println("Password: " + getPassword());
        System.out.println("Connection Degree to the User: " + connectionDegree);
    }
}
