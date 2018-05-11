package dataAccess.business.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class PasswordService {

    public PasswordService() {}

    public byte[] hash(String pass) {
        byte[] hashed = new byte[32];
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes());
            hashed = md.digest();
        } catch (NoSuchAlgorithmException e) { }
        return hashed;
    }

    public Boolean match(String pass, byte[] hash) {
        return Arrays.equals(hash, hash(pass));
    }
}
