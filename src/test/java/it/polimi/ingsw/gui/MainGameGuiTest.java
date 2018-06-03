package it.polimi.ingsw.gui;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.DiceResponse;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.gui.*;
import it.polimi.ingsw.sagrada.network.client.Client;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.fail;

public class MainGameGuiTest {

    private static final Logger LOGGER = Logger.getLogger(MainGameGuiTest.class.getName());
    private GameView gameView;

    @Test
    public void testMainGameGui() {
        try {
            List<String> players = new ArrayList<>();
            players.add("daniele");
            players.add("test");
            players.add("admin");
            List<Dice> diceList = new ArrayList<>();
            Dice dice = new Dice(1, Colors.BLUE);
            dice.setValue(1);
            diceList.add(dice);
            dice = new Dice(2, Colors.RED);
            dice.setValue(2);
            diceList.add(dice);
            dice = new Dice(3, Colors.GREEN);
            dice.setValue(3);
            diceList.add(dice);
            dice = new Dice(4, Colors.PURPLE);
            dice.setValue(4);
            diceList.add(dice);
            dice = new Dice(5, Colors.YELLOW);
            dice.setValue(5);
            diceList.add(dice);
            DiceResponse diceResponse = new DiceResponse("draft", diceList);
            Constraint[][] constraints = new Constraint[][]{
                    {Constraint.WHITE, Constraint.PURPLE, Constraint.RED, Constraint.BLUE, Constraint.YELLOW},
                    {Constraint.GREEN, Constraint.WHITE, Constraint.PURPLE, Constraint.YELLOW, Constraint.GREEN},
                    {Constraint.WHITE, Constraint.YELLOW, Constraint.BLUE, Constraint.GREEN, Constraint.RED},
                    {Constraint.BLUE, Constraint.WHITE, Constraint.RED, Constraint.BLUE, Constraint.WHITE},
            };
            GameView gameView = GameView.getInstance(players, diceResponse, constraints);
            GameGuiController gameGuiController = new GameGuiController(gameView, new Client() {
                @Override
                public void sendMessage(String message) throws RemoteException {

                }

                @Override
                public void disconnect() throws RemoteException {

                }

                @Override
                public void sendResponse(Message message) throws RemoteException {
                    System.out.println(message);
                }

                @Override
                public String getId() throws RemoteException {
                    return null;
                }
            });
            while(true);
        } catch (Exception exc) {
            exc.printStackTrace();
            LOGGER.log(Level.SEVERE, () -> exc.getMessage());
            fail();
        }

    }
}
