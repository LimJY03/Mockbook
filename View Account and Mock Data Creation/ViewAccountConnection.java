package assignmentds;

import java.time.LocalDate;

public class ViewAccountConnection {
    private String name;
    private String username;
    private String gender;
    private int numberOfFriends;
    private String hobbies;
    private String job;
    private String email;
    private String contact;
    private int age;
    private String address;
    private LocalDate birthday;
    private String p;

    public ViewAccountConnection(String name, String username, String gender, int numberOfFriends, String hobbies, String job, String email, String contact, int age, String address, LocalDate birthday, String p) {
        this.name = name;
        this.username = username;
        this.gender = gender;
        this.numberOfFriends = numberOfFriends;
        this.hobbies = hobbies;
        this.job = job;
        this.email = email;
        this.contact = contact;
        this.age = age;
        this.address = address;
        this.birthday = birthday;
        this.p = p;
    }

    public void viewAccount1(int connectionDegree) {
        System.out.println("\nConnection Degree: " + connectionDegree);
        System.out.println("Name: " + name);
        System.out.println("Username: " + username);
        System.out.println("Gender: " + gender);
        System.out.println("Number of Friends: " + numberOfFriends);
        System.out.println("Hobbies: " + hobbies);
        System.out.println("Jobs: " + job);
        System.out.println("Email: " + email);
        System.out.println("Phone Number: " + contact);
        System.out.println("Age: " + age);
        System.out.println("Address: " + address);
        System.out.println("Birthday: " + birthday);
        System.out.println("Password: " + p);
}


    public void viewAccount2(int connectionDegree) {
        System.out.println("\nConnection Degree: " + connectionDegree);
        System.out.println("Name: " + name);
        System.out.println("Username: " + username);
        System.out.println("Hobbies: " + hobbies);
        System.out.println("Jobs: " + job);
        System.out.println("Number of Friends: " + numberOfFriends);
    }

    public void viewAccount3(int connectionDegree) {
        System.out.println("\nConnection Degree: " + connectionDegree);
        System.out.println("Name: " + name);
        System.out.println("Username: " + username);
        System.out.println("Hobbies: " + hobbies);
        System.out.println("Number of Friends: " + numberOfFriends);
    }
}
