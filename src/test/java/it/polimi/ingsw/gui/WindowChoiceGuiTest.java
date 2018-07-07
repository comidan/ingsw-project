package it.polimi.ingsw.gui;

import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse;
import it.polimi.ingsw.sagrada.gui.windows.WindowChoiceGuiAdapter;
import it.polimi.ingsw.sagrada.gui.windows.WindowChoiceGuiView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WindowChoiceGuiTest {
    public void testGui() {
        List<Integer> ids = new ArrayList<>();
        ids.add(0);
        ids.add(1);
        WindowResponse windowResponse = new WindowResponse("Gianni", ids);
        WindowChoiceGuiView windowChoiceGuiView = WindowChoiceGuiView.getInstance(windowResponse, new Stage());
        new WindowChoiceGuiAdapter(windowChoiceGuiView, null);
        Scanner scanner = new Scanner(System.in);
        scanner.nextInt();
    }
}
