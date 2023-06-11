package AccessControl;

import java.util.ArrayList;

public class RegularUser extends User {

	private String contact, gender, email;
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
