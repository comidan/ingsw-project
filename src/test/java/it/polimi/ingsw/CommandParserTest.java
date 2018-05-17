package it.polimi.ingsw;

import it.polimi.ingsw.sagrada.network.JsonMessage;
import it.polimi.ingsw.sagrada.network.utilities.ActionEnum;
import it.polimi.ingsw.sagrada.network.utilities.CommandParser;
import org.json.simple.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandParserTest {


    @Test
    public void testCommandParser() {
        CommandParser commandParser = new CommandParser();
        JsonMessage jsonMessage = new JsonMessage();
        JSONObject json = jsonMessage.createLoginMessage("test", "prova");
        String string = json.toString();
        assertEquals(ActionEnum.LOGIN, commandParser.parse(string));

    }
}
