package Registration;

import java.sql.ResultSet;
import java.sql.SQLException;

import MainProgram.MainProgram;
import TraceBack.TraceBack;

import java.awt.AWTException;
import java.nio.charset.StandardCharsets;

public class PasswordReset extends TraceBack{
    
	
	public TraceBack Main() throws InterruptedException, AWTException {
        System.out.println("Enter your username");
        String username = MainProgram.sc.nextLine();
		resetPassword(username);
		this.previous.isPrevious = true;
        return this.previous;
}
	
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
            
            System.out.println("Enter your private key (press 0 to quit): ");
            String privateKey = MainProgram.sc.nextLine();

            boolean matchKey = matchPrivateKey(username, privateKey);

            // Break loop
            if (matchKey) break;
            if (privateKey.equals("0")) return;

            // Key does not match
            System.out.println("Your private key is wrong, try again!");
        }

        // Reset password
        Registration.setNewPassword(username);
        System.out.println("Password reset successfully!");
    }
}