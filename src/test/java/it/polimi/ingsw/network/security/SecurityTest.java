package it.polimi.ingsw.network.security;

import it.polimi.ingsw.sagrada.network.security.Security;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SecurityTest {

    @Test
    public void testAESAlgorithm() {
        final String data = "this_is_not_encrypted";
        final String encryptedData = Security.getEncryptedData(data);
        final String decryptedData = Security.getDecryptedData(encryptedData);
        assertEquals(data, decryptedData);
    }

    @Test
    public void testMD5Hashing() {
        final String data = "this_is_not_an_hash";
        final String hashedData = Security.generateMD5Hash(data);
        assertEquals("74240BEF4AD045EA3EFADE06F3D31A75", hashedData);
    }
}