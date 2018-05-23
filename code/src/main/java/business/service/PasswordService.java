package business.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

public class PasswordService extends Service {

    public PasswordService() {
    }

    public byte[] hash(String pass) {
        byte[] hashed = new byte[32];
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes());
            hashed = md.digest();
        } catch (NoSuchAlgorithmException e) {
        }
        return hashed;
    }

    public String randomPass(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
    	Random rand = new Random();
        for (int i = 0; i<5; i++) {
        	sb.append(chars.charAt(rand.nextInt((chars.length()))));
        }
        return sb.toString();
    }

    public Boolean match(char[] pass, byte[] hash) {
        return Arrays.equals(hash, hash(new String(pass)));
    }

}
