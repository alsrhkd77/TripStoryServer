package com.tripstory.tripstory.util;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;

@Component
@Primary
public class MD5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(String origin, String key) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(origin.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}


