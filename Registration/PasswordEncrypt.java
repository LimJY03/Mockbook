package Registration;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class PasswordEncrypt {

    public static String encryptSHA256(String input) {

        byte[] bytes = SHA256.hash(input.getBytes(StandardCharsets.US_ASCII));

        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, bytes);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    // Test
    public static void main(String args[]) {

        String s1 = "pAssword1";
        System.out.println("\n" + s1 + ": " + encryptSHA256(s1));

        String s2 = "mypAssword1";
        System.out.println("\n" + s2 + ": " + encryptSHA256(s2));

        String s3 = "my-pAssword1";
        System.out.println("\n" + s3 + ": " + encryptSHA256(s3));
    }
}
