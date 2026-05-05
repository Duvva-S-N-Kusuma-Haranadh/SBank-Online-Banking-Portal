package com.haranadh.SBank.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class AESUtil {

    private static final String SECRET = "1234567890123456";

    private SecretKeySpec getKey() {
        return new SecretKeySpec(SECRET.getBytes(), "AES");
    }

    public String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, getKey());
        byte[] decoded = Base64.getDecoder().decode(data);
        return new String(cipher.doFinal(decoded));
    }
}