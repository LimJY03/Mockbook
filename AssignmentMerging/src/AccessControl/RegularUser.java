package AccessControl;

import java.util.ArrayList;

public class RegularUser extends User {

    private String contact, gender, email, job, hobbies, address;
    private int age, connection;
    private ArrayList<String> connection1 = new ArrayList<>();
    private ArrayList<String> connection2 = new ArrayList<>();
    private ArrayList<String> connection3plus = new ArrayList<>();

    public RegularUser(String name, String email, String contact, int age, String gender, String p) {
        super(name, p);
        this.email = email;
        this.contact = contact;
        this.age = age;
        this.gender = gender;
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
}
