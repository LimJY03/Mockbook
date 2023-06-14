package AccessControl;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;

public class RegularUser extends User {

    private String contact, gender, email, job, hobbies, address;
    private int age;
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
    
    public RegularUser(String username, String email, String contact, String gender, String p, String job, String hobbies, String address) 
    {
    	super(username,p);
    	this.email=email;
    	this.contact=contact;
    	this.gender=gender;
        this.job = job;
        this.hobbies = hobbies;
        this.address = address;
    }
    
    public RegularUser(String username, String email, String contact, String gender, String p, String job, String hobbies, String address, LocalDate birthday) 
    {
    	this(username, email, contact, gender, p,job,hobbies,address);
    	this.birthday=birthday;
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
        connection1.sort(Comparator.naturalOrder());
    	return this.connection1;
    }

    public ArrayList<String> getConnection2() {
        return this.connection2;
    }

    public ArrayList<String> getConnection3plus() {
        return this.connection3plus;
    }

    public void addConnection1(String friend) {
        this.connection1.add(friend);
    }

    public void addConnection2(String friend) {
        this.connection2.add(friend);
    }

    public void addConnection3plus(String friend) {
        this.connection3plus.add(friend);
    }

    public void removeConnection1(String friend) {
        this.connection1.remove(friend);
    }

    public void removeConnection2(String friend) {
        this.connection2.remove(friend);
    }

    public void removeConnection3plus(String friend) {
        this.connection3plus.remove(friend);
    }
    
    public int calculateAge() {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthday, currentDate);
        return period.getYears();
    }
    
}
