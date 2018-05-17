package it.polimi.ingsw.sagrada.network.security;

public class Security {

    static public String getEncryptedData(String data) {
        return AdvancedEncryptionStandard.getEncryptedData(data);
    }

    static public String getDecryptedData(String encryptedData) {
        return AdvancedEncryptionStandard.getDecryptedData(encryptedData);
    }

    static public String generateMD5Hash(String data) {
        return MessageDigest5.generateMD5Hash(data);
    }
}
