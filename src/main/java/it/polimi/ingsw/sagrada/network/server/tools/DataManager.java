package it.polimi.ingsw.sagrada.network.server.tools;


import it.polimi.ingsw.sagrada.database.Database;
import it.polimi.ingsw.sagrada.network.LoginState;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataManager {

    private static final Logger LOGGER = Logger.getLogger(DataManager.class.getName());
    private static final Map<String, String> loggedUsers = new HashMap<>();
    private static final String DATABASE_CONFIG_PATH_ALT = "src/main/resources/json/config/database_config.json";
    private static final String DATABASE_CONFIG_PATH = "bytecode/json/config/database_config.json";
    private static final String NETWORK_CONFIG_ERROR = "network config fatal error";

    private Database database;
    private static String DBMS_USERNAME = getDbmsUsername();
    private static String DBMS_AUTH = getDbmsAuth();
    private static String DB_NAME = getDbName();
    private static int DBMS_PORT = getDbmsPort();

    private static DataManager dataManager;


    private DataManager()  {

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
            LOGGER.log(Level.SEVERE, () -> "Fatal error while initializing MySQL database connection " + exc.getMessage());
        }
    }

    public static DataManager getDataManager() {
        if(dataManager == null)
            dataManager = new DataManager();
        return dataManager;
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
            LOGGER.log(Level.SEVERE, exc.getMessage());
            return LoginState.AUTH_FATAL_ERROR;
        }
    }

    public synchronized boolean signUp(String username, String hashedPassword) {
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
            LOGGER.log(Level.SEVERE, exc.getMessage());
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
        output.println(commandParser.createJSONLoginResponse(token, lobbyPort));
        output.flush();
    }

    public static void sendLoginLobbyResponse(Socket clientSocket, int heartbeatPort) throws IOException {
        CommandParser commandParser = new CommandParser();
        PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
        output.println(commandParser.crateJSONLoginLobbyResponse(heartbeatPort));
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

    public static synchronized Function<String, Boolean> getSignOut() {
        return username -> loggedUsers.remove(username) != null;
    }

    private static String getDbmsUsername() {
        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader(DATABASE_CONFIG_PATH));
            JSONObject jsonObject = (JSONObject) obj;
            return (String) jsonObject.get("dbms_username");
        }
        catch (Exception exc) {
            try {

                Object obj = parser.parse(new FileReader(DATABASE_CONFIG_PATH_ALT));
                JSONObject jsonObject = (JSONObject) obj;
                return (String) jsonObject.get("dbms_username");
            }
            catch (Exception e) {
                LOGGER.log(Level.SEVERE, () -> NETWORK_CONFIG_ERROR);
                return "";
            }
        }
    }

    private static String getDbmsAuth() {
        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader(DATABASE_CONFIG_PATH));
            JSONObject jsonObject = (JSONObject) obj;
            return (String) jsonObject.get("dbms_auth");
        }
        catch (Exception exc) {
            try {

                Object obj = parser.parse(new FileReader(DATABASE_CONFIG_PATH_ALT));
                JSONObject jsonObject = (JSONObject) obj;
                return (String) jsonObject.get("dbms_auth");
            }
            catch (Exception e) {
                LOGGER.log(Level.SEVERE, () -> NETWORK_CONFIG_ERROR);
                return "";
            }
        }
    }

    private static String getDbName() {
        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader(DATABASE_CONFIG_PATH));
            JSONObject jsonObject = (JSONObject) obj;
            return (String) jsonObject.get("db_name");
        }
        catch (Exception exc) {
            try {

                Object obj = parser.parse(new FileReader(DATABASE_CONFIG_PATH_ALT));
                JSONObject jsonObject = (JSONObject) obj;
                return (String) jsonObject.get("db_name");
            }
            catch (Exception e) {
                LOGGER.log(Level.SEVERE, () -> NETWORK_CONFIG_ERROR);
                return "";
            }
        }
    }

    private static int getDbmsPort() {
        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader(DATABASE_CONFIG_PATH));
            JSONObject jsonObject = (JSONObject) obj;
            return Integer.parseInt((String) jsonObject.get("dbms_port"));
        }
        catch (Exception exc) {
            try {

                Object obj = parser.parse(new FileReader(DATABASE_CONFIG_PATH_ALT));
                JSONObject jsonObject = (JSONObject) obj;
                return Integer.parseInt((String) jsonObject.get("dbms_port"));
            }
            catch (Exception e) {
                LOGGER.log(Level.SEVERE, () -> NETWORK_CONFIG_ERROR);
                return Database.MYSQL_STANDARD_REGISTERED_PORT;
            }
        }
    }
}
