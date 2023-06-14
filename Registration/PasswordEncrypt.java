package Registration;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class PasswordEncrypt {

    public static String encryptSHA256(String password, String username) {

        byte[] initialBytes = password.getBytes(StandardCharsets.US_ASCII);
        byte[] salt = username.getBytes(StandardCharsets.US_ASCII);

        byte[] combinedBytes = new byte[initialBytes.length + salt.length];

        // Copy the contents of initialBytes to the combinedBytes array
        System.arraycopy(initialBytes, 0, combinedBytes, 0, initialBytes.length);

        // Copy the contents of salt to the combinedBytes array, starting from the end of initialBytes
        System.arraycopy(salt, 0, combinedBytes, initialBytes.length, salt.length);

        byte[] finalBytes = SHA256.hash(combinedBytes);

        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, finalBytes);

        // Convert into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    // Test
    public static void main(String args[]) {

        String s1 = "data_Structureisc00L";
        System.out.println("\n" + s1 + ": " + encryptSHA256(s1, "Wia1002Datastructure"));

        String s2 = "data_Structureisc00L";
        System.out.println("\n" + s2 + ": " + encryptSHA256(s2, "1002Datastructure"));

        // String s3 = "my-pAssword1";
        // System.out.println("\n" + s3 + ": " + encryptSHA256(s3));
    }
}
