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
import java.util.logging.Level;
import java.util.logging.Logger;

class AdvancedEncryptionStandard {

    private static final Logger LOGGER = Logger.getLogger(AdvancedEncryptionStandard.class.getName());
    private static final String KEY_STORE_PATH = "src/main/resources/json/security/KeyStore.json";
    private static final String KEY = getKey(); // WARNING : PLEASE FIND A MORE SECURE WAY
    private static final String INIT_VECTOR = getInitializationVector();
    private static final String ENCODING = "UTF-8";
    private static final String AES_CIPHER_CONFIG = "AES/CBC/PKCS5PADDING";
    private static final String ENCRYPTION_ALGORITHM = "AES";

    private static String encrypt(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes(ENCODING));
            SecretKeySpec sKeySpec = new SecretKeySpec(KEY.getBytes(ENCODING), ENCRYPTION_ALGORITHM);

            Cipher cipher = Cipher.getInstance(AES_CIPHER_CONFIG);
            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return Base64.encodeBase64String(encrypted);
        }
        catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex::getMessage);
        }

        return null;
    }

    private static String decrypt(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes(ENCODING));
            SecretKeySpec sKeySpec = new SecretKeySpec(KEY.getBytes(ENCODING), ENCRYPTION_ALGORITHM);

            Cipher cipher = Cipher.getInstance(AES_CIPHER_CONFIG);
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        }
        catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex::getMessage);
        }

        return null;
    }

    static String getEncryptedData(String data) {
        return encrypt(data);
    }

    static String getDecryptedData(String encryptedData) {
        return decrypt(encryptedData);
    }


    private static String getKey() {
        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader(KEY_STORE_PATH));
            JSONObject jsonObject = (JSONObject) obj;
            return (String) jsonObject.get("key");
        }
        catch (IOException|ParseException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
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
            LOGGER.log(Level.SEVERE, exc::getMessage);
            return "";
        }
    }
}
