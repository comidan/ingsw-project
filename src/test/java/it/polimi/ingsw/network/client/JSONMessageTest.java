package it.polimi.ingsw.network.client;

import it.polimi.ingsw.sagrada.network.client.JsonMessage;
import it.polimi.ingsw.sagrada.network.server.protocols.application.CommandParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JSONMessageTest {

    @Test
    public void testJSONParsing() {
        CommandParser commandParser = new CommandParser();
        String port = JsonMessage.parseJsonData(commandParser.crateJSONLoginLobbyResponse(49152)).get("heartbeat_port");
        assertEquals(49152, Integer.parseInt(port));
        port = JsonMessage.parseJsonData(commandParser.crateJSONLoginResponse("id", 49152)).get("lobby_port");
        assertEquals(49152, Integer.parseInt(port));
        assertEquals("error", JsonMessage.parseJsonData(commandParser.crateJSONLoginResponseError()).get("login"));
    }
}
