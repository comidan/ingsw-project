package it.polimi.ingsw.gui;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.DiceResponse;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.gui.*;
import it.polimi.ingsw.sagrada.network.client.Client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.fail;

public class MainGameGuiTest {

    private static final Logger LOGGER = Logger.getLogger(MainGameGuiTest.class.getName());
    private GameView gameView;

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
            List<Constraint[][]> constraints = new ArrayList<>();
            ConstraintGenerator constraintGenerator = new ConstraintGenerator();
            constraints.add(constraintGenerator.getConstraintMatrix(1, WindowSide.FRONT));
            constraints.add(constraintGenerator.getConstraintMatrix(2, WindowSide.FRONT));
            constraints.add(constraintGenerator.getConstraintMatrix(3, WindowSide.FRONT));
            GameView gameView = GameView.getInstance("test", players, diceResponse, constraints);
            GameGuiManager gameGuiManager = new GameGuiManager(gameView, new Client() {
                @Override
                public void sendMessage(String message) throws RemoteException {

                }

                @Override
                public void sendRemoteMessage(Message message) throws RemoteException {

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
            Scanner scanner = new Scanner(System.in);
            scanner.nextInt();
        } catch (Exception exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
            fail();
        }

    }
}
