package com.github.elbean.ignite.cache.api.security;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HmacSignatureHelpers {

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    /**
     * Validate that the signature received matches the generated signature for
     * the data received in the request
     *
     * @param content
     * @param expectedSignature
     * @return
     */
    public static boolean validateExpectedSignature(String content, String privateKey, String expectedSignature) {
        String generatedSignature = getHmacSha1SignatureFromStringContent(content, privateKey);

        if (generatedSignature.equals(expectedSignature)) {
            return true;
        }

        return false;
    }

    /**
     * Get the HMAC SHA-1 signature for the content using the specified private
     * key.
     *
     * @param contentToHash
     * @param privateKey
     * @return
     */
    public static String getHmacSha1SignatureFromStringContent(String contentToHash, String privateKey) {

        //get private key bytes
        byte[] privateKeyBytes = privateKey.getBytes(StandardCharsets.US_ASCII);
        byte[] contentBytes = contentToHash.getBytes(StandardCharsets.US_ASCII);
        String signature = "";
        SecretKeySpec key = new SecretKeySpec(privateKeyBytes, HMAC_SHA1_ALGORITHM);
        try {
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(key);
            byte[] computedHashBytes = mac.doFinal(contentBytes);
            signature = Base64.getEncoder().encodeToString(computedHashBytes);
        } catch (NoSuchAlgorithmException ae) {
            //log that the algorithm is incorrect
        } catch (InvalidKeyException ke) {
            //log that the key was invalid
        } finally {
            return signature;
        }
    }
}
