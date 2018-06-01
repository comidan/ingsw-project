package it.polimi.ingsw.network.protocols;

import it.polimi.ingsw.sagrada.network.client.protocols.networklink.discoverinternet.DiscoverInternet;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import java.io.FileReader;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DiscoverInternetTest {

    private static final String NETWORK_TEST_CONFIG_PATH = "src/main/resources/json/test/network.json";
    private static final Logger LOGGER = Logger.getLogger(DiscoverInternetTest.class.getName());

    @Test
    public void testIsPrivateIP() throws UnknownHostException {
        assertTrue(DiscoverInternet.isPrivateIP(Inet4Address.getByName(getPrivateClassIp('C'))));
        assertTrue(DiscoverInternet.isPrivateIP(Inet4Address.getByName(getPrivateClassIp('B'))));
        assertTrue(DiscoverInternet.isPrivateIP(Inet4Address.getByName(getPrivateClassIp('A'))));
        assertFalse(DiscoverInternet.isPrivateIP(Inet4Address.getByName(getPublicClassIp('C'))));
        assertFalse(DiscoverInternet.isPrivateIP(Inet4Address.getByName(getPublicClassIp('B'))));
        assertFalse(DiscoverInternet.isPrivateIP(Inet4Address.getByName(getPublicClassIp('A'))));
    }

    private static String getPrivateClassIp(char ipClass) {
        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader(NETWORK_TEST_CONFIG_PATH));
            JSONObject jsonObject = (JSONObject) obj;
            return (String) jsonObject.get("ip_no_mask_class_" + ipClass + "_private");
        }
        catch (Exception exc) {
            LOGGER.log(Level.SEVERE, () -> "network test fatal error");
            return "";
        }
    }

    private static String getPublicClassIp(char ipClass) {
        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader(NETWORK_TEST_CONFIG_PATH));
            JSONObject jsonObject = (JSONObject) obj;
            return (String) jsonObject.get("ip_no_mask_class_" + ipClass);
        }
        catch (Exception exc) {
            LOGGER.log(Level.SEVERE, () -> "network test fatal error");
            return "";
        }
    }
}