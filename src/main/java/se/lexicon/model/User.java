package se.lexicon.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import se.lexicon.exception.AuthenticationFailedException;

import java.security.SecureRandom;
import java.util.Random;

public class User {
    private final String username;
    private String password;
    private boolean expired;

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, boolean expired) {
        this.username = username;
        this.password = password;
        this.expired = expired;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.encode(this.password);
    }

    public void checkHash(String hashedPassword) throws AuthenticationFailedException{
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean isEqual = passwordEncoder.matches(this.password, hashedPassword);
        if (!isEqual) {
            throw new AuthenticationFailedException("Authentication failed. Invalid credentials.");
        }
    }

    public boolean isExpired() {
        return expired;
    }

    public String userInfo() {
        return "Username " + username + ", Password: " + password;
    }

    public void newPassword() {
        this.password = generateRandomPassword();
    }

    private String generateRandomPassword() {
        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVXYZabcdefghijklmnopqrstuvxyz0123456789";
        int passwordLength = 10;
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            char randomChar = allowedCharacters.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

}
