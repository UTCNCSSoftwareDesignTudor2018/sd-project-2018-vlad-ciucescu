package business.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

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
        byte[] salt = new byte[32];
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
        }
        return Arrays.toString(salt);
    }

    public Boolean match(String pass, byte[] hash) {
        return Arrays.equals(hash, hash(pass));
    }

}
