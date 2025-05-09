package com.audicin.backend.track.api.security.helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class LicenseHasher {
    /**
     * Hashes the license data (partner, track, and license date)
     *
     * @param data the data to be hashed (partner email, track title, license
     *            date, etc.)
     * @return a hashed string (Base64 encoded)
     */
    public static String hashLicense(String data) {
        try {
            // Using SHA-256 for strong hashing
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data.getBytes());

            // Convert hash bytes to Base64 encoding (URL-safe)
            return Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing license data", e);
        }
    }
}

