package it.polimi.ingsw.sagrada;

import it.polimi.ingsw.sagrada.network.client.Client;
import it.polimi.ingsw.sagrada.network.client.ClientManager;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GameClient {

    public static void main(String[] args) throws IOException {

        System.out.println("Choose network application protocol :\n1.TCP Socket\n2.RMI (through HTTP)");
        int choice;
        do
            choice = optionSelector();
        while (choice == -1 || choice < 1 || choice > 2);
        Client client;
        switch (choice) {
            case 1:
                client = ClientManager.getSocketClient();
                break;
            case 2:
                client = ClientManager.getRMIClient();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private static int optionSelector() {
        Scanner scanner = new Scanner(System.in);
        try {
            return scanner.nextInt();
        }
        catch (InputMismatchException exc) {
            System.out.println("Please insert a valid natural number");
            return -1;
        }
    }
}
