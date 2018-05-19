package it.polimi.ingsw.network;

import it.polimi.ingsw.sagrada.network.server.protocols.application.CommandParser;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CommandParserTest {

    @Test
    public void testJSONParsing() {
        CommandParser commandParser = new CommandParser();
        JSONObject jsonObjectTest = new JSONObject();
        JSONObject jsonData = new JSONObject();
        jsonData.put("username", "test");
        jsonData.put("auth", "testPassword");
        JSONObject jsonActionLogin = new JSONObject();
        jsonActionLogin.put("login", jsonData);
        JSONObject container = new JSONObject();
        container.put("action", jsonActionLogin);
        Map<String, String> data = commandParser.parse(container.toJSONString());
        assertEquals("test", data.get("username"));
        assertEquals("testPassword", data.get("auth"));
    }
}