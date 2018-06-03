package it.polimi.ingsw.gui;

import it.polimi.ingsw.sagrada.game.intercomm.message.WindowResponse;
import it.polimi.ingsw.sagrada.gui.window_choice.WindowChoiceGuiController;
import it.polimi.ingsw.sagrada.gui.window_choice.WindowChoiceGuiView;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WindowChoiceGuiTest {
    public void testGui() {
        List<Integer> ids = new ArrayList<>();
        ids.add(0);
        ids.add(1);
        WindowResponse windowResponse = new WindowResponse("Gianni", ids);
        WindowChoiceGuiView windowChoiceGuiView = WindowChoiceGuiView.getInstance(windowResponse);
        new WindowChoiceGuiController(windowChoiceGuiView, null);
        Scanner scanner = new Scanner(System.in);
        scanner.nextInt();
    }
}
