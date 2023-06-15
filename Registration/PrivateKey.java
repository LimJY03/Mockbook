package Registration;

import MainProgram.MainProgram;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class PrivateKey {
    
    public static void createPrivateKey(String username) {
        
        // Random bytes
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32];
        random.nextBytes(keyBytes);
        
        // Username bytes
        byte[] usernameBytes = username.getBytes(StandardCharsets.US_ASCII);

        // Generate private key
        byte[] privateKeyBytes = SHA256.hash(PasswordEncrypt.combineBytes(keyBytes, usernameBytes));
        String privateKey = PasswordEncrypt.byteToString(privateKeyBytes);

        System.out.printf("\nYour private key is: %s\nStore it at a safe place\n\n", privateKey);

        // Hash private key
        String hashedPrivateKey = PasswordEncrypt.byteToString(SHA256.hash(privateKey.getBytes(StandardCharsets.US_ASCII)));

        // Store key in database
        MainProgram.db.updateTable("PrivateKey", hashedPrivateKey, username);
    }
}
