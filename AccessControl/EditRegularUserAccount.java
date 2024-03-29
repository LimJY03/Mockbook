package AccessControl;

import java.awt.AWTException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import MainProgram.MainPageFeature;
import MainProgram.MainProgram;
import Registration.AccountSetUp;
import Registration.PasswordEncrypt;
import Registration.PasswordReset;
import TraceBack.TraceBack;
import java.sql.ResultSet;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Stack;

public class EditRegularUserAccount extends TraceBack {

	static Scanner sc = MainProgram.sc;
	static Connection connection = MainProgram.connection;

	public TraceBack Main() throws InterruptedException, AWTException {
		editAccount(MainPageFeature.me);
		this.previous.isPrevious = true;
		return this.previous;
	}

	public static void editAccount(RegularUser me) {

		outerLoop: while (true) {
			System.out.println("\nEdit Account:");
			System.out.println("1)  Edit Username");
			System.out.println("2)  Edit Email");
			System.out.println("3)  Edit Current Job Status");
			System.out.println("4)  Edit Hobbies");
			System.out.println("5)  Edit Phone Number");
			System.out.println("6)  Edit Gender");
			System.out.println("7)  Edit Password");
			System.out.println("8)  Edit Birthday");
			System.out.println("9)  Exit");

			System.out.println("Enter your option");
			String option = sc.nextLine();

			switch (option) {
			case "1":
				optionLoop1: while (true) {
					System.out.println("Current username: " + me.getUsername());
					System.out.println("Would you like to change your username? Enter 1 for yes, 2 for no.");
					String yesOrNo = sc.nextLine();
					switch (yesOrNo) {
					case "1":
						updateUsername();
					case "2":
						break optionLoop1;
					default:
						System.out.println("Invalid Option. Choose 1 or 2.");
					}
				}
				break;

			case "2":
				optionLoop2: while (true) {
					System.out.println("Current email: " + me.getEmail());
					System.out.println("Would you like to change your email? Enter 1 for yes, 2 for no.");
					String yesOrNo = sc.nextLine();
					switch (yesOrNo) {
					case "1":
						updateEmail();
					case "2":
						break optionLoop2;
					default:
						System.out.println("Invalid option. Choose 1 or 2.");
					}
				}
				break;

			case "3":
				optionLoop3: while (true) {
					System.out.println("Current job: " + me.getJob());
					System.out.println("Would you like to change your current job? Enter 1 for yes, 2 for no.");
					String yesOrNo = sc.nextLine();
					switch (yesOrNo) {
					case "1":
						updateJob();
					case "2":
						break optionLoop3;
					default:
						System.out.println("Invalid option. Choose 1 or 2.");
					}
				}
				break;

			case "4":
				optionLoop4: while (true) {
					System.out.println("Current hobby or hobbies: " + me.getHobbies());
					System.out.println("Would you like to add a new hobby? Enter 1 for yes, 2 for no.");
					String yesOrNo = sc.nextLine();
					switch (yesOrNo) {
					case "1":
						updateHobbies();
					case "2":
						break optionLoop4;
					default:
						System.out.println("Invalid option. Choose 1 or 2.");
					}
				}
				break;

			case "5":
				optionLoop5: while (true) {
					System.out.println("Current phone number: " + me.getContact());
					System.out.println("Would you like to change your phone number? Enter 1 for yes, 2 for no.");
					String yesOrNo = sc.nextLine();
					switch (yesOrNo) {
					case "1":
						updatePhoneNumber();
					case "2":
						break optionLoop5;
					default:
						System.out.println("Invalid option. Choose 1 or 2.");
					}
				}
				break;

			case "6":
				optionLoop6: while (true) {
					System.out.println("Current gender: " + me.getGender());
					System.out.println("Would you like to edit your gender? Enter 1 for yes, 2 for no.");
					String yesOrNo = sc.nextLine();
					switch (yesOrNo) {
					case "1":
						updateGender();
					case "2":
						break optionLoop6;
					default:
						System.out.println("Invalid option. Choose 1 or 2.");
					}
				}
				break;

			case "7":
				optionLoop8: while (true) {
					System.out.println("Would you like to change your password? Enter 1 for yes, 2 for no.");
					String yesOrNo = sc.nextLine();
					switch (yesOrNo) {
					case "1":
						PasswordReset.resetPassword(MainPageFeature.me.getUsername());
					case "2":
						break optionLoop8;
					default:
						System.out.println("Invalid option. Choose 1 or 2.");
					}
				}
				break;

			case "8":
				optionLoop10: while (true) {
					System.out.println("Current birthday: " + me.getBirthday());
					System.out.println("Do you want to change your birthday? Enter 1 for yes, 2 for no.");
					String yesOrNo = sc.nextLine();
					switch (yesOrNo) {
					case "1":
						updateBirthday();
					case "2":
						break optionLoop10;
					default:
						System.out.println("Invalid option. Choose 1 or 2.");
					}
				}
				break;

			case "9":
				break outerLoop;

			default:
				System.out.println("Invalid option. Please enter again");
				break;
			}
		}

	}

	// update methods
	private static void updateUsername() {

		String password;

		while (true) {
			System.out.print("Enter your current password before changing the username (0 to quit): ");
			password = sc.nextLine();

			if (password.equals("0")) {
				return;
			}

			if (isValidPassword(MainPageFeature.me.getUsername(), password)) {
				break;
			}

			System.out.println("Wrong password. Try again");
		}

		String newUsername;

		do {
			System.out.print("Enter your new username: ");
			newUsername = sc.nextLine();

			if (!isValidUsername(newUsername)) {
				System.out.println("Please enter a valid username");
			}
		} while (!isValidUsername(newUsername));

		try {
			String query = "UPDATE User SET Username = ? WHERE Username = ?";

			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, newUsername);
			stmt.setString(2, MainPageFeature.me.getUsername());

			int rowAffected = stmt.executeUpdate();

			if (rowAffected > 0) {
				MainPageFeature.me.setUsername(newUsername);
				System.out.println("Successfully changed to new username");
				System.out.println("Your current username is: " + MainPageFeature.me.getUsername());

				MainProgram.GlobalDataStore.username = MainPageFeature.me.getUsername();
				password = PasswordEncrypt.encryptSHA256(password, MainPageFeature.me.getUsername());
				MainProgram.db.updateTable("Password", password, MainPageFeature.me.getUsername());
			} else
				System.out.println("Error updating username. Please try again");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void updateEmail() {
		System.out.println("Enter your new email: ");
		String newEmail = sc.nextLine();

		while (!isValidEmail(newEmail)) {
			System.out.println("Please enter your new email: ");
			newEmail = sc.nextLine();
		}

		try {
			String query = "UPDATE User SET Email = ? WHERE Username = ?";

			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, newEmail);
			stmt.setString(2, MainPageFeature.me.getUsername());

			int rowAffected = stmt.executeUpdate();

			if (rowAffected > 0) {
				MainPageFeature.me.setEmail(newEmail);
				System.out.println("Successfully changed to new email");
				System.out.println("Your current email is: " + MainPageFeature.me.getEmail());
			} else
				System.out.println("Error updating email. Please try again");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void updateJob() {
		Stack<String> jobList = new Stack<>();

		System.out.println("Enter your new job: ");
		String newJob = sc.nextLine();

		while (!isValidJob(newJob)) {
			System.out.println("Please enter your new job");
			newJob = sc.nextLine();
		}

		try {

			PreparedStatement stmt = connection.prepareStatement("SELECT Job FROM User WHERE Username = ?");
			stmt.setString(1, MainPageFeature.me.getUsername());
			ResultSet rs = stmt.executeQuery();

			String query = "UPDATE User SET Job = ? WHERE Username = ?";
			PreparedStatement stmt2 = connection.prepareStatement(query);
			stmt2.setString(1, newJob);
			stmt2.setString(2, MainPageFeature.me.getUsername());

			int rowAffected = stmt2.executeUpdate();

			if (rowAffected > 0) {
				MainPageFeature.me.setJob(newJob);
				System.out.println("Successfully changed job list");
				System.out.println("Your current job list is: " + MainPageFeature.me.getJob());
			} else {
				System.out.println("Error updating job list. Please try again");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void updateHobbies() {
		ArrayList<String> hobbyList = new ArrayList<>();

		System.out.println("Enter your new hobby: ");
		String newHobby = sc.nextLine();

		while (!isValidHobby(newHobby)) {
			System.out.println("Please enter your new hobby");
			newHobby = sc.nextLine();
		}

		try {

			PreparedStatement stmt = connection.prepareStatement("SELECT Hobbies FROM User WHERE Username = ?");
			stmt.setString(1, MainPageFeature.me.getUsername());
			StringBuilder hobbySb = new StringBuilder();
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				String hobbies = rs.getString("Hobbies");
				String[] hobbiesArr = {};

				if (hobbies != null) {
					hobbiesArr = hobbies.split(",");
					for (String i : hobbiesArr) {
						hobbyList.add(i);
						hobbySb.append(i + ",");
					}
				}

				hobbyList.add(newHobby);
				hobbySb.append(newHobby);
			}

			String query = "UPDATE User SET Hobbies = ? WHERE Username = ?";
			PreparedStatement stmt2 = connection.prepareStatement(query);
			stmt2.setString(1, hobbySb.toString());
			stmt2.setString(2, MainPageFeature.me.getUsername());

			int rowAffected = stmt2.executeUpdate();

			if (rowAffected > 0) {
				MainPageFeature.me.setHobbies(hobbySb.toString());
				System.out.println("Successfully changed hobby list");
				System.out.println("Your current hobbies list is: " + MainPageFeature.me.getHobbies());
			} else {
				System.out.println("Error updating hobby list. Please try again");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void updatePhoneNumber() {
		System.out.println("Enter your new phone number: ");
		String newPhoneNumber = sc.nextLine();

		while (!isValidPhoneNumber(newPhoneNumber)) {
			System.out.println("Please enter your new phone number: ");
			newPhoneNumber = sc.nextLine();
		}

		try {
			String query = "UPDATE User SET PhoneNumber = ? WHERE Username = ?";

			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, newPhoneNumber);
			stmt.setString(2, MainPageFeature.me.getUsername());

			int rowAffected = stmt.executeUpdate();

			if (rowAffected > 0) {
				MainPageFeature.me.setContact(newPhoneNumber);
				System.out.println("Successfully changed to new phone number");
				System.out.println("Your current phone number is: " + MainPageFeature.me.getContact());
			} else
				System.out.println("Error updating phone number. Please try again");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void updateGender() {
		System.out.println("Enter your new gender: ");
		String newGender = sc.nextLine();

		while (!isValidGender(newGender)) {
			System.out.println("Please enter your new gender");
			newGender = sc.nextLine();
		}

		try {
			String query = "UPDATE User SET Gender = ? WHERE Username = ?";

			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, newGender);
			stmt.setString(2, MainPageFeature.me.getUsername());

			int rowAffected = stmt.executeUpdate();

			if (rowAffected > 0) {
				MainPageFeature.me.setGender(newGender);
				System.out.println("Successfully changed to new gender");
				System.out.println("Your current gender is: " + MainPageFeature.me.getGender());
			} else
				System.out.println("Error updating gender. Please try again");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void updateBirthday() {

		new AccountSetUp(MainPageFeature.me.getUsername()).setBirthday();
		System.out.println("This is your current birthday "+ MainPageFeature.me.getBirthday() + " and age: "
				+ MainPageFeature.me.getAge());
	}

	// validation methods
	private static boolean isValidUsername(String username) {

		boolean isValid = true;
		final Pattern textPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z]).+$");

		if (MainProgram.db.searchTable("Username", username)) {
			System.out.println("Username has been taken");
			isValid = false;
		} else if (username.length() > 25 || username.length() < 5) {
			System.out.println("Username must be less than 25 and more than 5 characters in length.");
			isValid = false;
		} else if (!textPattern.matcher(username).matches()) {
			System.out.println("Username must have at least one uppercase / one lowercase character");
			isValid = false;
		} else if (username.contains(" ")) {
			System.out.println("Username can't contain empty space");
			isValid = false;
		} else if (username.isEmpty()) {

			System.out.println("Invalid input.");
			isValid = false;

		}

		return isValid;
	}

	private static boolean isValidEmail(String email) {

		boolean isValid = true;
		final Pattern textPattern = Pattern.compile("[\\w-\\.+]*[\\w-\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");

		if (MainProgram.db.searchTable("Email", email.toLowerCase())) {
			System.out.println("Email has been taken");
			isValid = false;
		} else if (!textPattern.matcher(email).matches()) {
			System.out.println("Email must be in email format");
			isValid = false;
		} else if (email.contains(" ")) {
			System.out.println("Email can't contain empty space");
			isValid = false;
		} else if (email.isEmpty()) {

			System.out.println("Invalid input.");
			isValid = false;

		}

		return isValid;

	}

	private static boolean isValidJob(String job) {

		boolean isValid = true;

		if (job.matches("^-?\\d+$")) {

			System.out.println("Job cannot have integer");
			isValid = false;

		} else if (!job.matches("[a-zA-Z]+")) {

			System.out.println("Job should contain only alphabetic characters.");
			isValid = false;

		} else if (job.isEmpty()) {

			System.out.println("Invalid input.");
			isValid = false;

		}

		return isValid;

	}

	private static boolean isValidHobby(String hobby) {

		boolean isValid = true;

		if (hobby.matches("^-?\\d+$")) {

			System.out.println("Hobby cannot be an integer");
			isValid = false;

		} else if (!hobby.matches("[a-zA-Z]+")) {

			System.out.println("Hobby should contain only alphabetic characters.");
			isValid = false;

		} else if (hobby.isEmpty()) {

			System.out.println("Invalid input.");
			isValid = false;

		}

		return isValid;

	}

	private static boolean isValidPhoneNumber(String phoneNumber) {

		boolean isValid = true;
		final Pattern textPattern = Pattern.compile("\\d{10,11}");

		if (MainProgram.db.searchTable("PhoneNumber", phoneNumber)) {

			System.out.println("Phone number has been taken");
			isValid = false;

		} else if (!textPattern.matcher(phoneNumber).matches()) {

			System.out.println("Phone number must only contain 10 to 11 Digits");
			isValid = false;

		} else if (phoneNumber.contains(" ")) {

			System.out.println("Phone number can't contain empty space");
			isValid = false;

		} else if (phoneNumber.isEmpty()) {

			System.out.println("Invalid input.");
			isValid = false;

		}

		return isValid;

	}

	private static boolean isValidGender(String gender) {
		boolean isValid = true;

		if (!(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female")
				|| gender.equalsIgnoreCase("other"))) {

			System.out.print("Enter 'male', 'female' or 'other'");
			isValid = false;

		} else if (gender.contains(" ")) {

			System.out.println("Gender can't contain empty space");
			isValid = false;

		} else if (gender.isEmpty()) {

			System.out.println("Invalid input.");
			isValid = false;

		}

		return isValid;

	}

	private static boolean isValidPassword(String username, String password) {
		try {
			PreparedStatement stmt = MainProgram.connection
					.prepareStatement("SELECT Password FROM User WHERE Username= ?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				if (PasswordEncrypt.encryptSHA256(password, username).equals(rs.getString("Password")))
					return true;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
