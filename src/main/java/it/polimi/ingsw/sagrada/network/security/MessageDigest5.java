package it.polimi.ingsw.sagrada.network.security;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * The Class MessageDigest5.
 */
class MessageDigest5 {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(MessageDigest5.class.getName());

    /**
     * Generate MD 5 hash.
     *
     * @param data the data
     * @return the string
     */
    static String generateMD5Hash(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest).toUpperCase();
        }
        catch (NoSuchAlgorithmException exc) {
            LOGGER.log(Level.SEVERE, () -> "MD5 hashing fatal error");
            return null;
        }
    }
}
