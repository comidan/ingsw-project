package it.polimi.ingsw.sagrada.network.security;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

class AdvancedEncryptionStandard {

    private static final Logger LOGGER = Logger.getLogger(AdvancedEncryptionStandard.class.getName());
    private static final String KEY = "31415926ecehigic"; // WARNING : PLEASE FIND A MORE SECURE WAY
    private static String INIT_VECTOR = "";

    private static String encrypt(String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return Base64.encodeBase64String(encrypted);
        }
        catch (Exception ex) {
            LOGGER.log(Level.SEVERE, () -> "AES encryption fatal error");
        }

        return null;
    }

    private static String decrypt(String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        }
        catch (Exception ex) {
            LOGGER.log(Level.SEVERE, () -> "AES encryption fatal error");
        }

        return null;
    }

    static String getEncryptedData(String data) {
        INIT_VECTOR = generateInitializationVector();
        return encrypt(INIT_VECTOR, data);
    }

    static String getDecryptedData(String encryptedData) {
        if(INIT_VECTOR.isEmpty())
            INIT_VECTOR = generateInitializationVector();
        String decrypted = decrypt(INIT_VECTOR, encryptedData);
        INIT_VECTOR = "";
        return decrypted;
    }

    private static String generateInitializationVector() {
        String stringDate = new Date().getTime()+"";
        StringBuilder reverse = new StringBuilder();
        for(int index = stringDate.length() - 1; index >= 0; index--)
            reverse.append(stringDate.charAt(index));
        return Security.generateMD5Hash(reverse.toString()).substring(0, 16);
    }
}
