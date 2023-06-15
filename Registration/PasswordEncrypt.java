package Registration;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class PasswordEncrypt {

    public static String encryptSHA256(String password, String username) {

        byte[] initialBytes = password.getBytes(StandardCharsets.US_ASCII);
        byte[] salt = username.getBytes(StandardCharsets.US_ASCII);
        
        byte[] finalBytes = SHA256.hash(combineBytes(initialBytes, salt));

        return byteToString(finalBytes);
    }

    public static String byteToString(byte[] bytes) {
        
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, bytes);

        // Convert into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public static byte[] combineBytes(byte[] byte1, byte[] byte2) {

        byte[] combinedBytes = new byte[byte1.length + byte2.length];

        // Copy the contents of byte1 to the combinedBytes array
        System.arraycopy(byte1, 0, combinedBytes, 0, byte1.length);

        // Copy the contents of byte2 to the combinedBytes array, starting from the end of byte1
        System.arraycopy(byte2, 0, combinedBytes, byte1.length, byte2.length);

        return combinedBytes;
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
