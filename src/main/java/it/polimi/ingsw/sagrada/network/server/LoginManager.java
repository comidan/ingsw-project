package it.polimi.ingsw.sagrada.network.server;


import it.polimi.ingsw.sagrada.database.Database;
import it.polimi.ingsw.sagrada.network.server.protocols.application.CommandParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.Socket;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class LoginManager {

    private Database database;
    private Map<String, String> loggedUsers;
    private static final String DBMS_USERNAME = "root";  //temporary credentials, TO BE CHANGED
    private static final String DBMS_AUTH = "";
    private static final String DB_NAME = "sagrada";
    private static final int DBMS_PORT = 3306;


    public LoginManager() throws SQLException {
        loggedUsers = new HashMap<>();
        try {
            database = Database.initSQLDatabase(DBMS_USERNAME,
                                                DBMS_AUTH,
                                                100,
                                                "localhost",
                                                DBMS_PORT,
                                                DB_NAME);
            System.out.println("MySQL database connection initialized on port " + DBMS_PORT);
        }
        catch (SQLException exc) {
            database = Database.initMSAccessDatabase(DBMS_USERNAME,
                                                    DBMS_AUTH,
                                                    100,
                                                    "localhost",
                                                    DBMS_PORT,
                                                    DB_NAME);
            }
    }


    public synchronized LoginState authenticate(String username, String hashedPassowrd) {
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
            exc.printStackTrace();
            return LoginState.AUTH_FATAL_ERROR;
        }
    }

    public boolean signUp(String username, String hashedPassword) {
        try {
            long nanoDate = new java.util.Date().getTime();
            PreparedStatement query = database.prepareQuery("INSERT INTO User (Username, Password, SubscriptionDate, Email) VALUES (?, ?, ?, ?)");
            String email = "emailTestForNow";
            query.setString(1, username);
            query.setString(2, hashedPassword);
            query.setDate(3, new Date(nanoDate));
            query.setString(4, email);
            return query.executeUpdate() == 1;
        }
        catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        }
    }

    public String receiveLoginData(Socket clientSocket) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        return input.readLine();
    }

    public static void sendLoginError(Socket clientSocket, String data) throws IOException {
        CommandParser commandParser = new CommandParser();
        PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
        output.println(commandParser.crateJSONMessage(data));
        output.flush();
    }

    public static void sendLoginError(Socket clientSocket) throws IOException {
        CommandParser commandParser = new CommandParser();
        PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
        output.println(commandParser.crateJSONLoginResponseError());
        output.flush();
    }

    public static void sendLoginSignup(Socket clientSocket) throws IOException {
        CommandParser commandParser = new CommandParser();
        PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
        output.println(commandParser.crateJSONLoginResponseRegister());
        output.flush();
    }

    public static void sendLoginResponse(Socket clientSocket, String token, int lobbyPort) throws IOException {
        CommandParser commandParser = new CommandParser();
        PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
        output.println(commandParser.crateJSONLoginResponse(token, lobbyPort));
        output.flush();
    }

    public static void sendLoginLobbyResponse(Socket clientSocket, int lobbyPort) throws IOException {
        CommandParser commandParser = new CommandParser();
        PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
        output.println(commandParser.crateJSONLoginLobbyResponse(lobbyPort));
        output.flush();
    }

    public static int tokenAuthentication(List<String> tokens, Socket client) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String data = input.readLine();
        try {
            JSONObject jsonToken = (JSONObject) new JSONParser().parse(data);
            return tokens.indexOf((String)jsonToken.get("token"));
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
