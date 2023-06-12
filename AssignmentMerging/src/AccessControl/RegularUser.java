package AccessControl;

import java.time.LocalDate;
import java.util.ArrayList;

public class RegularUser extends User {

    private String contact, gender, email, job, hobbies, address;
    private int age, connection;
    private LocalDate birthday;
    private ArrayList<String> connection1 = new ArrayList<>();
    private ArrayList<String> connection2 = new ArrayList<>();
    private ArrayList<String> connection3plus = new ArrayList<>();
    
    

    public RegularUser(String userName, String email, String contact, int age, String gender, String p) {
        super(userName, p);
        this.email = email;
        this.contact = contact;
        this.age = age;
        this.gender = gender;
    }
    
    public RegularUser(String userName, String email, String contact, int age, String gender, String p, String job, String hobbies, String address) 
    {
        this(userName, email, contact, age, gender, p);
        this.job = job;
        this.hobbies = hobbies;
        this.address = address;
    }
    
    public RegularUser(String userName, String email, String contact, int age, String gender, String p, String job, String hobbies, String address, LocalDate birthday) 
    {
    	this(userName, email, contact, age, gender, p,job,hobbies,address);
    	this.birthday=birthday;
    }

    

    public void setConnection(int connection) {
        this.connection = connection;
    }

    public String getUserName() {
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

    public ArrayList<String> getConnection1() {
        return this.connection1;
    }

    public ArrayList<String> getConnection2() {
        return this.connection2;
    }

    public ArrayList<String> getConnection3plus() {
        return this.connection3plus;
    }
}
