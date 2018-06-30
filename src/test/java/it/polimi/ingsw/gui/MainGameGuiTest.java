package it.polimi.ingsw.gui;

import it.polimi.ingsw.sagrada.game.base.WindowManager;
import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.MessageDispatcher;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.RuleResponse;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.playables.Window;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.game.rules.ErrorType;
import it.polimi.ingsw.sagrada.game.rules.RuleManager;
import it.polimi.ingsw.sagrada.gui.components.DiceView;
import it.polimi.ingsw.sagrada.gui.game.GameGuiAdapter;
import it.polimi.ingsw.sagrada.gui.game.GameView;
import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import it.polimi.ingsw.sagrada.gui.utils.ConstraintGenerator;
import it.polimi.ingsw.sagrada.network.client.Client;
import javafx.application.Platform;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.fail;

public class MainGameGuiTest {

    private static final Logger LOGGER = Logger.getLogger(MainGameGuiTest.class.getName());
    private GameView gameView;
    private GameGuiAdapter gameGuiAdapter;

    public void testMainGameGui() {
        try {
            List<String> players = new ArrayList<>();
            players.add("test");
            players.add("daniele");
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
            Map<String, Constraint[][]> constraints = new HashMap<>();
            ConstraintGenerator constraintGenerator = new ConstraintGenerator();
            constraints.put("test", constraintGenerator.getConstraintMatrix(1, WindowSide.FRONT));
            constraints.put("daniele", constraintGenerator.getConstraintMatrix(2, WindowSide.FRONT));
            constraints.put("admin", constraintGenerator.getConstraintMatrix(3, WindowSide.FRONT));
            final Window window = new WindowManager(null, new MessageDispatcher()).generateWindow(1, WindowSide.FRONT);
            GameView gameView = GameView.getInstance("test", players, constraints);
            DiceView diceView = new DiceView(Constraint.BLUE, Constraint.THREE, 4);
            DiceView diceView2 = new DiceView(Constraint.BLUE, Constraint.THREE, 4);
            List<DiceView> diceViews = new ArrayList<>();
            diceViews.add(diceView);
            diceViews.add(diceView2);

            Platform.runLater(() -> gameView.setRoundtrackImage(diceViews, 3));

            gameGuiAdapter = new GameGuiAdapter(gameView, new Client() {

                @Override
                public void startHeartbeat(int port) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void close() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void sendMessage(String message) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void sendRemoteMessage(Message message) {
                    System.out.println(message);
                    DiceEvent diceEvent = (DiceEvent) message;
                    window.setCell(diceList.get(diceEvent.getIdDice() - 1), diceEvent.getPosition().getRow(), diceEvent.getPosition().getCol());
                    ErrorType errorType = new RuleManager().validateWindow(window.getCellMatrix());
                    System.out.println(errorType);
                    if(errorType != ErrorType.NO_ERROR) {
                        gameGuiAdapter.notifyMoveResponse(new RuleResponse("daniele", false));
                        window.resetCell(diceEvent.getPosition().getRow(), diceEvent.getPosition().getCol());
                    }
                }

                @Override
                public void disconnect() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void sendResponse(Message message) {
                    System.out.println(message);
                }

                @Override
                public String getId() {
                    return null;
                }

                @Override
                public boolean isInFastRecovery() throws RemoteException {
                    return false;
                }
            });
            gameGuiAdapter.setDiceList(diceResponse);
            gameGuiAdapter.setPrivateObjective(1);
            gameGuiAdapter.setPublicObjectives(Arrays.asList(1,2,3));
            Scanner scanner = new Scanner(System.in);
            scanner.nextInt();
        }
        catch (Exception exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
            fail();
        }

    }
}
