package assignmentds;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MockDataCreator {
    private static final List<String> NAMES1 = Arrays.asList(
        "Emma", "Noah", "Olivia", "Liam", "Ava", "Isabella",
        "Sophia", "Mia", "Charlotte", "Amelia", "Harper",
        "Evelyn", "Benjamin", "James", "William", "Oliver",
        "Elijah", "Lucas", "Henry", "Alexander", "Michael",
        "Daniel", "Matthew", "Aiden", "Jackson"
    );
    private static final List<String> NAMES2 = Arrays.asList(
        "Smith", "Johnson", "Brown", "Davis", "Miller",
        "Wilson", "Moore", "Taylor", "Anderson", "Thomas",
        "Jackson", "White", "Harris", "Martin", "Lee"
    );
    private static final List<String> HOBBIES = Arrays.asList(
        "Reading", "Hiking", "Cooking", "Gaming", "Traveling",
        "Painting", "Photography", "Sleeping", "Dancing",
        "Writing", "Sports", "Gardening", "Singing", "Knitting",
        "Playing an instrument", "Yoga", "Fishing", "Collecting",
        "Cycling", "Watching movies", "Volunteering", "Meditation",
        "Chess", "Running"
    );
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
    private static final int PASSWORD_LENGTH = 10;
    private static final List<String> JOBS = Arrays.asList(
        "Software Engineer", "Teacher", "Doctor", "Writer", "Artist",
        "Chef", "Lawyer", "Programmer"
    );
    private static final List<String> ROAD_NAMES = Arrays.asList(
        "Main Street", "Cedar Road", "Oak Avenue", "Maple Drive",
        "Elm Street", "Tapah Road"
    );
    private static final List<String> COUNTRIES = Arrays.asList(
        "USA", "Canada", "UK", "Australia", "Germany", "Malaysia"
    );
    private static final List<String> POSTCODES = Arrays.asList(
        "12345", "67890", "54321", "98765", "43210", "18450", "12445"
    );
    private static final List<String> CITIES = Arrays.asList(
        "New York City", "Los Angeles", "London", "Sydney",
        "Berlin", "Selangor", "Perak"
    );
    private static final List<String> DISTRICTS = Arrays.asList(
        "Midtown", "Upper East Side", "Upper West Side",
        "Lower East Side", "Chelsea", "Greenwich Village",
        "Harlem", "Soho", "Tribeca"
    );

    public static User createMockUser() {
        Random random = new Random();
        int id = random.nextInt(1000) + 1; // Random ID between 1 and 1000
        String firstName = getRandomElement(NAMES1);
        String lastName = getRandomElement(NAMES2);
        String name = firstName + " " + lastName;
        String username = generateUsername(name);
        String email = username + "@gmail.com";
        String contact = generateRandomPhoneNumber();
        LocalDate birthday = generateRandomBirthday();
        String address = generateRandomAddress();
        String gender = random.nextBoolean() ? "Male" : "Female";
        int numberOfFriends = random.nextInt(100);
        String hobbies = generateRandomHobbies();
        String job = generateRandomJob();
        String p = generateRandomPassword();
    
        return new RegularUser(username, email, contact, calculateAge(birthday), gender, p, job, hobbies, address, birthday, id, name, numberOfFriends);
    }

    private static String generateUsername(String name) {
        String[] nameParts = name.split(" ");
        String firstName = nameParts[0];
        String lastName = nameParts[1]; // Use the second part as the last name

        StringBuilder usernameBuilder = new StringBuilder();
        usernameBuilder.append(firstName.toLowerCase());
        usernameBuilder.append(lastName.toLowerCase());

        Random random = new Random();
        int randomNumber = random.nextInt(10000); // Generate a random number between 0 and 9999
        usernameBuilder.append(randomNumber);

        return usernameBuilder.toString();
    }

    private static String generateRandomPhoneNumber() {
        StringBuilder phoneNumber = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int digit = random.nextInt(10);
            phoneNumber.append(digit);
        }
        return phoneNumber.toString();
    }

    private static LocalDate generateRandomBirthday() {
        Random random = new Random();
        int minYear = 1950;
        int maxYear = LocalDate.now().getYear() - 18; // Consider adults only (18 years old and above)

        int year = random.nextInt(maxYear - minYear + 1) + minYear;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(getMaxDayOfMonth(year, month)) + 1;

        return LocalDate.of(year, month, day);
    }

    private static int getMaxDayOfMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.lengthOfMonth();
    }

    private static String generateRandomAddress() {
        Random random = new Random();
        int houseNumber = random.nextInt(100) + 1;
        String roadName = getRandomElement(ROAD_NAMES);
        String city = getRandomElement(CITIES);
        String district = getRandomElement(DISTRICTS);
        String postalCode = getRandomElement(POSTCODES);
        String country = getRandomElement(COUNTRIES);

        StringBuilder addressBuilder = new StringBuilder();
        addressBuilder.append(houseNumber).append(" ");
        addressBuilder.append(roadName).append(", ");
        addressBuilder.append(district).append(", ");
        addressBuilder.append(postalCode).append(", ");
        addressBuilder.append(city).append(", ");
        addressBuilder.append(country);

        return addressBuilder.toString();
    }

    private static String generateRandomHobbies() {
        Collections.shuffle(HOBBIES);
        return String.join(", ", HOBBIES.subList(0, 3));
    }

    private static String generateRandomJob() {
        Collections.shuffle(JOBS);
        return JOBS.get(0);
    }

    private static <T> T getRandomElement(List<T> list) {
        Random random = new Random();
        int index = random.nextInt(list.size());
        return list.get(index);
    }

    private static String generateRandomPassword() {
        StringBuilder passwordBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            passwordBuilder.append(CHARACTERS.charAt(index));
        }

        return passwordBuilder.toString();
    }
    
    public static int calculateAge(LocalDate birthday) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthday, currentDate);
    return period.getYears();
    }

    
}
