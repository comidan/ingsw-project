package it.polimi.ingsw.sagrada.network.server;


import it.polimi.ingsw.sagrada.database.Database;
import it.polimi.ingsw.sagrada.network.server.protocols.application.CommandParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.Socket;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class LoginManager {

    private Database database;
    private Map<String, String> loggedUsers;
    private static final String DBMS_USERNAME = "daniele_sagrada";
    private static final String DBMS_AUTH = "ge7npchy5";
    private static final String DB_NAME = "sagrada_db";
    private static final int portDBMS = 3306;


    public LoginManager() throws SQLException {
        loggedUsers = new HashMap<>();
        try {
            database = Database.initSQLDatabase(DBMS_USERNAME,
                                                DBMS_AUTH,
                                                100,
                                                "localhost",
                                                3306,
                                                DB_NAME);
        }
        catch (SQLException exc) {
            database = Database.initMSAccessDatabase(DBMS_USERNAME,
                                                    DBMS_AUTH,
                                                    100,
                                                    "localhost",
                                                    3306,
                                                    DB_NAME);
            }

    }


    public synchronized LoginState autheanticate(String username, String hashedPassowrd) {
        if(loggedUsers.get(username) != null)
            return LoginState.AUTH_FAILED_USER_ALREADY_LOGGED;
        try {
            ResultSet queryResult = database.executeRawQuery("SELECT Username, Password FROM User WHERE Username = '" + username + "' AND " +
                    "Password = '" + hashedPassowrd + "'");
            if(queryResult.next()) {
                loggedUsers.put(username, hashedPassowrd);
                return LoginState.AUTH_OK;
            }
            else
                return LoginState.AUTH_FAILED_USER_NOT_EXIST;
        }
        catch (SQLException exc) {
            return LoginState.AUTH_FATAL_ERROR;
        }
    }

    public boolean signUp(String username, String hashedPassword) {
        try {
            long nanoDate = new java.util.Date().getTime();
            int result = database.executeUpdate("INSERT INTO User VALUES ('" + username + "', '" +
                                                                                           hashedPassword + "', '" +
                                                                                           new Date(nanoDate).toString() +"')");
            return result == 1;
        }
        catch (SQLException exc) {
            return false;
        }
    }

    public String receiveLoginData(Socket clientSocket) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String data;
        StringBuilder partialJSON = new StringBuilder();
        while ((data = input.readLine()) != null)
            partialJSON.append(data);
        return partialJSON.toString();
    }

    public static void sendLoginError(Socket clientSocket, String data) throws IOException {
        CommandParser commandParser = new CommandParser();
        PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
        output.println(commandParser.crateJSONMessage(data));
        output.flush();
    }

    public static void sendLoginResponse(Socket clientSocket, String token, int lobbyPort) throws IOException {
        CommandParser commandParser = new CommandParser();
        PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
        output.println(commandParser.crateJSONLoginResponse(token, lobbyPort));
        output.flush();
    }

    public static int tokenAuthentication(List<String> tokens, Socket client) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String data;
        StringBuilder partialJSON = new StringBuilder();
        while ((data = input.readLine()) != null)
            partialJSON.append(data);
        try {
            JSONObject jsonToken = (JSONObject) new JSONParser().parse(partialJSON.toString());
            return tokens.indexOf(jsonToken.get("token"));
        }
        catch (ParseException exc) {
            return -1;
        }
    }

    public Function<String, Boolean> getSignOut() {
        return username -> loggedUsers.remove(username) != null;
    }

    public enum LoginState {
        AUTH_OK,
        AUTH_FAILED_USER_ALREADY_LOGGED,
        AUTH_FAILED_USER_NOT_EXIST,
        AUTH_FATAL_ERROR
    }
}
