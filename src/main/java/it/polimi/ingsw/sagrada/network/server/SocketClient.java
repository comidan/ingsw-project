package it.polimi.ingsw.sagrada.network.server;

import it.polimi.ingsw.sagrada.network.client.JsonMessage;
import it.polimi.ingsw.sagrada.network.server.protocols.application.CommandParser;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketClient implements Client {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private JsonMessage loginMessage;
    private CommandParser commandParser;
    private ExecutorService executor;

    public SocketClient(Socket socket) throws IOException {
        this.socket = socket;
        commandParser = new CommandParser();
        executor = Executors.newSingleThreadExecutor();
        initCoreFunctions();
    }

    private void initCoreFunctions() throws IOException{
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        executor.submit(this);
    }

    @Override
    public void sendMessage(String message) {
        String payload = commandParser.crateJSONMessage(message);
        output.println(payload);
        output.flush();
    }

    @Override
    public void doActions() {

    }

    @Override
    public void close() {
        try {
            socket.close();
            executor.shutdown();
        } catch (Exception e) {
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {

            }
        }
    }

    @Override
    public void run() {
        while (!executor.isShutdown()) {
            try {
                System.out.println(input.readLine());
            } catch (IOException exc) {

            }
        }
    }
}
