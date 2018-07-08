package it.polimi.ingsw.network.client;

import it.polimi.ingsw.sagrada.game.intercomm.message.lobby.LobbyLoginEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.util.ErrorEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.util.HeartbeatInitEvent;
import it.polimi.ingsw.sagrada.network.server.protocols.application.JsonToMessageConverter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JSONMessageTest {

    @Test
    public void testJSONParsing() {
        JsonToMessageConverter jsonToMessageConverter = new JsonToMessageConverter();
        int port = ((HeartbeatInitEvent) it.polimi.ingsw.sagrada.network.client.protocols.application.JsonToMessageConverter
                    .parseJsonData(jsonToMessageConverter.crateJSONLoginLobbyResponse(49152)))
                    .getHeartbeatPort();
        assertEquals(49152, port);
        port = ((LobbyLoginEvent) it.polimi.ingsw.sagrada.network.client.protocols.application.JsonToMessageConverter
                .parseJsonData(jsonToMessageConverter.createJSONLoginResponse("id",49152)))
                .getLobbyPort();
        assertEquals(49152, port);
        assertEquals("error", ((ErrorEvent) it.polimi.ingsw.sagrada.network.client.protocols.application.JsonToMessageConverter.parseJsonData(jsonToMessageConverter.crateJSONLoginResponseError())).getError());
    }
}
