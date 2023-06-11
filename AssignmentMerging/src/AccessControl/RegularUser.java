package AccessControl;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public class RegularUser extends User {

    private String contact, gender, email, job, hobbies, address;
    private int age, connection, numberOfFriends,id;
    private String name, username, email, contactNumber, address, gender, addressPreview, password;
    private LocalDate birthday;
    private List<String> hobbies;
    private List<String> jobs;
    private ArrayList<String> connection1 = new ArrayList<>();
    private ArrayList<String> connection2 = new ArrayList<>();
    private ArrayList<String> connection3plus = new ArrayList<>();

    public RegularUser(String name, String email, String contact, int age, String gender, String p, String username, 
                       LocalDate birthday, String address, String gender, int numberOfFriends, 
                       List<String> hobbies, List<String> jobs, String password, int id) {
        super(name, p);
        this.email = email;
        this.contact = contact;
        this.age = age;
        this.gender = gender;
        this.username = username;
        this.birthday = birthday;
        this.address = address;
        this.numberOfFriends = numberOfFriends;
        this.hobbies = hobbies;
        this.jobs = jobs;
        this.addressPreview = getAddressPreview();
        this.password = password;
        this.contactNumber = contactNumber;
        
    }
    
    public RegularUser(String name, String email, String contact, int age, String gender, String p, String job, String hobbies, String address) 
    {
        this(name, email, contact, age, gender, p);
        this.job = job;
        this.hobbies = hobbies;
        this.address = address;
    }

    public void setConnection(int connection) {
        this.connection = connection;
    }

    public String getName() {
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

    public ArrayList<String> getConnection1() {
        return this.connection1;
    }

    public ArrayList<String> getConnection2() {
        return this.connection2;
    }

    public ArrayList<String> getConnection3plus() {
        return this.connection3plus;
    }
    
    public int calculateAge() {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthday, currentDate);
        return period.getYears();
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getNumberOfFriends() {
        return numberOfFriends;
    }

    public void setNumberOfFriends(int numberOfFriends) {
        this.numberOfFriends = numberOfFriends;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public List<String> getJobs() {
        return jobs;
    }

    public void setJobs(List<String> jobs) {
        this.jobs = jobs;
    }

    public String getAddressPreview() {
        return addressPreview;
    }

    public void setAddressPreview(String addressPreview) {
        this.addressPreview = addressPreview;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void viewAccount() {
        System.out.println("Name: " + getName());
        System.out.println("Username: " + getUsername());
        System.out.println("Email Address: " + getEmail());
        System.out.println("Phone Number: " + getContactNumber());
        System.out.println("Birthday: " + getBirthday());
        System.out.println("Age: " + calculateAge());
        System.out.println("Address: " + getAddress());
        System.out.println("Gender: " + getGender());
        System.out.println("Number of Friends: " + getNumberOfFriends());
        System.out.println("Hobbies: " + getHobbies());
        System.out.println("Jobs: " + getJobs());
        System.out.println("Password: " + getPassword());
    }
}
