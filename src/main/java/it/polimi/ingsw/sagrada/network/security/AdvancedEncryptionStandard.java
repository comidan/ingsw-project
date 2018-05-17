package it.polimi.ingsw.sagrada.network.security;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

class AdvancedEncryptionStandard {

    private static final Logger LOGGER = Logger.getLogger(AdvancedEncryptionStandard.class.getName());
    private static final String KEY_STORE_PATH = "src/main/resources/json/security/KeyStore.json";
    private static final String KEY = getKey(); // WARNING : PLEASE FIND A MORE SECURE WAY
    private static final String INIT_VECTOR = getInitializationVector();

    private static String encrypt(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec sKeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return Base64.encodeBase64String(encrypted);
        }
        catch (Exception ex) {
            LOGGER.log(Level.SEVERE, () -> "AES encryption fatal error");
        }

        return null;
    }

    private static String decrypt(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec sKeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        }
        catch (Exception ex) {
            LOGGER.log(Level.SEVERE, () -> "AES encryption fatal error");
        }

        return null;
    }

    static String getEncryptedData(String data) {
        return encrypt(data);
    }

    static String getDecryptedData(String encryptedData) {
        return decrypt(encryptedData);
    }

    /**
     * @deprecated
     */
    private static String generateInitializationVector() {
        String stringDate = new Date().getTime()+"";
        StringBuilder reverse = new StringBuilder();
        for(int index = stringDate.length() - 1; index >= 0; index--)
            reverse.append(stringDate.charAt(index));
        return Security.generateMD5Hash(reverse.toString()).substring(0, 16);
    }

    private static String getKey() {
        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader(KEY_STORE_PATH));
            JSONObject jsonObject = (JSONObject) obj;
            return (String) jsonObject.get("key");
        }
        catch (IOException|ParseException exc) {
            LOGGER.log(Level.SEVERE, () -> "AES KEYSTORE fatal error");
            return "";
        }
    }

    private static String getInitializationVector() {
        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader(KEY_STORE_PATH));
            JSONObject jsonObject = (JSONObject) obj;
            return (String) jsonObject.get("initialization_vector");
        }
        catch (IOException|ParseException exc) {
            LOGGER.log(Level.SEVERE, () -> "AES KEYSTORE fatal error");
            return "";
        }
    }
}
