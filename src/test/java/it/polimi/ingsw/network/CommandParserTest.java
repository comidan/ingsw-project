package it.polimi.ingsw.network;

import it.polimi.ingsw.sagrada.game.intercomm.message.player.LoginEvent;
import it.polimi.ingsw.sagrada.network.server.protocols.application.CommandParser;
import org.json.simple.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandParserTest {

    @Test
    public void testJSONParsing() {
        CommandParser commandParser = new CommandParser();
        JSONObject jsonData = new JSONObject();
        jsonData.put("username", "test");
        jsonData.put("auth", "testPassword");
        JSONObject container = new JSONObject();
        container.put("type_msg", "action");
        container.put("type_cmd", "login");
        container.put("login", jsonData);
        LoginEvent data = (LoginEvent)commandParser.parse(container.toJSONString());
        assertEquals("test", data.getUsername());
        assertEquals("testPassword", data.getPassword());
    }
}