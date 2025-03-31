package org.chitsa.orderservice.services.impl;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CryptoUtil {

    public static String calculateSecretHash(String userPoolClientId, String userPoolClientSecret, String username) {
        try {
            final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(new SecretKeySpec(
                    userPoolClientSecret.getBytes(StandardCharsets.UTF_8),
                    HMAC_SHA256_ALGORITHM
            ));

            mac.update(username.getBytes(StandardCharsets.UTF_8));
            byte[] hmacResult = mac.doFinal(userPoolClientId.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hmacResult);
        } catch (Exception e) {
            throw new RuntimeException("Error creating secret hash", e);
        }
    }
}
