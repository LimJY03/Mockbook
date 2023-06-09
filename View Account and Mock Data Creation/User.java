package assignmentds;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private String contactNumber;
    private LocalDate birthday;
    private String address;
    private String gender;
    private int numberOfFriends;
    private List<String> hobbies;
    private List<String> jobs;
    private String addressPreview; // New field

    public User(int id, String name, String username, String email, String contactNumber, LocalDate birthday,
                String address, String gender, int numberOfFriends, List<String> hobbies, List<String> jobs) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.contactNumber = contactNumber;
        this.birthday = birthday;
        this.address = address;
        this.gender = gender;
        this.numberOfFriends = numberOfFriends;
        this.hobbies = hobbies;
        this.jobs = jobs;
        this.addressPreview = getAddressPreview();
    }

    public void viewAccount() {
        System.out.println("Name: " + getName());
        System.out.println("Username: " + getUsername());
        System.out.println("Email Address: " + getEmail());
        System.out.println("Contact Number: " + getContactNumber());
        System.out.println("Birthday: " + getBirthday());
        System.out.println("Age: " + calculateAge());
        System.out.println("Address: " + getAddress());
        System.out.println("Gender: " + getGender());
        System.out.println("Number of Friends: " + getNumberOfFriends());
        System.out.println("Hobbies: " + getHobbies());
        System.out.println("Jobs: " + getJobs());
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

    public String getUsername() {
        return username;
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

}
