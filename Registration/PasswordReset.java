package Registration;

import java.sql.ResultSet;
import java.sql.SQLException;

import MainProgram.MainProgram;

import java.nio.charset.StandardCharsets;

public class PasswordReset {
    
    private static boolean matchPrivateKey(String username, String privateKey) {

        // Hash private key
        String hashedPrivateKey = PasswordEncrypt.byteToString(SHA256.hash(privateKey.getBytes(StandardCharsets.US_ASCII)));

        try {
            ResultSet rs = MainProgram.connection.prepareStatement("SELECT PrivateKey FROM User WHERE Username = '" + username + "'").executeQuery();
            if (rs.next()) return hashedPrivateKey.equals(rs.getString("PrivateKey"));
        }
        catch (SQLException e) { System.out.println(e); }
        
        return false;
    }

    public static void resetPassword(String username) {

        // Check private key
        while (true) {
            
            System.out.println("Enter your private key: ");
            String privateKey = MainProgram.sc.nextLine();

            boolean matchKey = matchPrivateKey(username, privateKey);

            // Break loop
            if (matchKey) break;

            // Key does not match
            System.out.println("Your private key is wrong, try again!");
        }

        // Reset password
        Registration.setNewPassword(username);
    }
}
