package com.example.school.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class EncryptionUtils {
    private static final Logger log = LogManager.getLogger(EncryptionUtils.class);

    private static String enryptPassword(String password) {
        String encryptedPassword = null;

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // Strength set as 12
            encryptedPassword = encoder.encode(password);
        }   catch (Exception e) {
            log.error("Error while encrypting password", e);
        }

        return encryptedPassword;
    }

}
