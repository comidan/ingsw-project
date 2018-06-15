package it.polimi.ingsw.sagrada.network.security;


/**
 * The Class Security.
 */
public class Security {

    /**
     * Gets the encrypted data.
     *
     * @param data the data
     * @return the encrypted data
     */
    static public String getEncryptedData(String data) {
        return AdvancedEncryptionStandard.getEncryptedData(data);
    }

    /**
     * Gets the decrypted data.
     *
     * @param encryptedData the encrypted data
     * @return the decrypted data
     */
    static public String getDecryptedData(String encryptedData) {
        return AdvancedEncryptionStandard.getDecryptedData(encryptedData);
    }

    /**
     * Generate MD 5 hash.
     *
     * @param data the data
     * @return the string
     */
    static public String generateMD5Hash(String data) {
        return MessageDigest5.generateMD5Hash(data);
    }
}
