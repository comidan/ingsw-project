package it.polimi.ingsw.network.client;

import it.polimi.ingsw.sagrada.game.intercomm.message.lobby.LobbyLoginEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.util.ErrorEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.util.HeartbeatInitEvent;
import it.polimi.ingsw.sagrada.network.client.protocols.application.JsonMessage;
import it.polimi.ingsw.sagrada.network.server.protocols.application.CommandParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JSONMessageTest {

    @Test
    public void testJSONParsing() {
        CommandParser commandParser = new CommandParser();
        int port = ((HeartbeatInitEvent)JsonMessage
                    .parseJsonData(commandParser.crateJSONLoginLobbyResponse(49152)))
                    .getHeartbeatPort();
        assertEquals(49152, port);
        port = ((LobbyLoginEvent)JsonMessage
                .parseJsonData(commandParser.createJSONLoginResponse("id",49152)))
                .getLobbyPort();
        assertEquals(49152, port);
        assertEquals("error", ((ErrorEvent)JsonMessage.parseJsonData(commandParser.crateJSONLoginResponseError())).getError());
    }
}
