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


/**
 * The Class DataManager.
 */
public class DataManager {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(DataManager.class.getName());
    
    /** The Constant loggedUsers. */
    private static final Map<String, String> loggedUsers = new HashMap<>();
    
    /** The Constant DATABASE_CONFIG_PATH. */
    private static final String DATABASE_CONFIG_PATH = "/json/config/database_config.json";
    
    /** The Constant NETWORK_CONFIG_ERROR. */
    private static final String NETWORK_CONFIG_ERROR = "network config fatal error";

    /** The database. */
    private Database database;
    
    /** The dbms username. */
    private static final String DBMS_USERNAME = getDbmsUsername();
    
    /** The dbms auth. */
    private static final String DBMS_AUTH = getDbmsAuth();
    
    /** The db name. */
    private static final String DB_NAME = getDbName();
    
    /** The dbms port. */
    private static final int DBMS_PORT = getDbmsPort();

    /** The data manager. */
    private static DataManager dataManager;


    /**
     * Instantiates a new data manager.
     */
    private DataManager()  {
        try {
            database = Database.initSQLiteDatabase(DBMS_USERNAME,
                                                DBMS_AUTH,
                                                100,
                                                DB_NAME);
            System.out.println("SQLite database connection initialized");
        }
        catch (SQLException exc) {
            LOGGER.log(Level.SEVERE, () -> "Fatal error while initializing MySQL database connection " + exc.getMessage());
        }
    }

    /**
     * Gets the data manager.
     *
     * @return the data manager
     */
    public static DataManager getDataManager() {
        if(dataManager == null)
            dataManager = new DataManager();
        return dataManager;
    }


    /**
     * Authenticate.
     *
     * @param username the username
     * @param hashedPassword the hashed passowrd
     * @return the login state
     */
    public synchronized LoginState authenticate(String username, String hashedPassword) {
        if(loggedUsers.get(username) != null)
            return LoginState.AUTH_FAILED_USER_ALREADY_LOGGED;
        try {
            ResultSet queryResult = database.executeRawQuery("SELECT Username, Password FROM User WHERE Username = '" + username + "' AND " +
                    "Password = '" + hashedPassword + "'");
            if(queryResult.next()) {
                loggedUsers.put(username, hashedPassword);
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

    /**
     * Sign up.
     *
     * @param username the username
     * @param hashedPassword the hashed password
     * @return true, if successful
     */
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

    public synchronized int saveGameRecord(Date date, int duration, int playersNumber) {
        try {
            PreparedStatement query = database.prepareQuery("INSERT INTO play (Date, Duration, PlayersNumber) VALUES (?, ?, ?)");
            query.setDate(1, date);
            query.setInt(2, duration);
            query.setInt(3, playersNumber);
            query.executeUpdate();
            ResultSet queryResult = database.executeRawQuery("SELECT MAX(ID) FROM play");
            return queryResult.getInt("ID");
        }
        catch (SQLException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
            return 0;
        }
    }

    public synchronized void savePlayerRecord(String username, int gameID, int score, Date joinDate, int chosenWindowID, byte[] windowEndgameImage) {
        try {
            PreparedStatement query = database.prepareQuery("INSERT INTO playrecord (Username, PlayID, Score, PlayJoiningDate, ChoseWinowID, WindowMatrixState) VALUES (?, ?, ?, ?, ?, ?)");
            query.setString(1, username);
            query.setInt(2, gameID);
            query.setInt(3, score);
            query.setDate(4, joinDate);
            query.setInt(5, chosenWindowID);
            query.setBytes(6, windowEndgameImage);
            query.executeUpdate();
        }
        catch (SQLException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
        }
    }

    public synchronized void saveAssignedObjectCards(String username, int gameID, int objectiveID, int score) {
        try {
            PreparedStatement query = database.prepareQuery("INSERT INTO assignedobjectives (Username, PlayID, ObjectiveID, Score) VALUES (?, ?, ?, ?)");
            query.setString(1, username);
            query.setInt(2, gameID);
            query.setInt(3, objectiveID);
            query.setInt(4, score);
            query.executeUpdate();
        }
        catch (SQLException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
        }
    }

    public synchronized void saveAssignedToolCards(String username, int gameID, int toolCardID, int timesUsed) {
        try {
            PreparedStatement query = database.prepareQuery("INSERT INTO assignedtools (Username, PlayID, ToolID, Used) VALUES (?, ?, ?, ?)");
            query.setString(1, username);
            query.setInt(2, gameID);
            query.setInt(3, toolCardID);
            query.setInt(4, timesUsed);
            query.executeUpdate();
        }
        catch (SQLException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
        }
    }

    /**
     * Receive login data.
     *
     * @param clientSocket the client socket
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String receiveLoginData(Socket clientSocket) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        return input.readLine();
    }

    /**
     * Send login error.
     *
     * @param clientSocket the client socket
     * @param data the data
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void sendLoginError(Socket clientSocket, String data) throws IOException {
        CommandParser commandParser = new CommandParser();
        PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
        output.println(commandParser.crateJSONMessage(data));
        output.flush();
    }

    /**
     * Send login error.
     *
     * @param clientSocket the client socket
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void sendLoginError(Socket clientSocket) throws IOException {
        CommandParser commandParser = new CommandParser();
        PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
        output.println(commandParser.crateJSONLoginResponseError());
        output.flush();
    }

    /**
     * Send login signup.
     *
     * @param clientSocket the client socket
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void sendLoginSignup(Socket clientSocket) throws IOException {
        CommandParser commandParser = new CommandParser();
        PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
        output.println(commandParser.crateJSONLoginResponseRegister());
        output.flush();
    }

    /**
     * Send login response.
     *
     * @param clientSocket the client socket
     * @param token the token
     * @param lobbyPort the lobby port
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void sendLoginResponse(Socket clientSocket, String token, int lobbyPort) throws IOException {
        CommandParser commandParser = new CommandParser();
        PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
        output.println(commandParser.createJSONLoginResponse(token, lobbyPort));
        output.flush();
    }

    /**
     * Send login lobby response.
     *
     * @param clientSocket the client socket
     * @param heartbeatPort the heartbeat port
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void sendLoginLobbyResponse(Socket clientSocket, int heartbeatPort) throws IOException {
        CommandParser commandParser = new CommandParser();
        PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
        output.println(commandParser.crateJSONLoginLobbyResponse(heartbeatPort));
        output.flush();
    }

    /**
     * Token authentication.
     *
     * @param tokens the tokens
     * @param client the client
     * @return the int
     * @throws IOException Signals that an I/O exception has occurred.
     */
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

    /**
     * Gets the sign out.
     *
     * @return the sign out
     */
    public static synchronized Function<String, Boolean> getSignOut() {
        return username -> loggedUsers.remove(username) != null;
    }

    /**
     * Gets the dbms username.
     *
     * @return the dbms username
     */
    private static String getDbmsUsername() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new InputStreamReader(DataManager.class.getResourceAsStream(DATABASE_CONFIG_PATH)));
            JSONObject jsonObject = (JSONObject) obj;
            return (String) jsonObject.get("dbms_username");
        }
        catch (Exception exc) {
            LOGGER.log(Level.SEVERE, NETWORK_CONFIG_ERROR);
            return Database.MYSQL_STANDARD_USERNAME;
        }
    }

    /**
     * Gets the dbms auth.
     *
     * @return the dbms auth
     */
    private static String getDbmsAuth() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new InputStreamReader(DataManager.class.getResourceAsStream(DATABASE_CONFIG_PATH)));
            JSONObject jsonObject = (JSONObject) obj;
            return (String) jsonObject.get("dbms_auth");
        }
        catch (Exception exc) {
            LOGGER.log(Level.SEVERE, NETWORK_CONFIG_ERROR);
            return Database.MYSQL_STANDARD_AUTH;
        }
    }

    /**
     * Gets the db name.
     *
     * @return the db name
     */
    private static String getDbName() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new InputStreamReader(DataManager.class.getResourceAsStream(DATABASE_CONFIG_PATH)));
            JSONObject jsonObject = (JSONObject) obj;
            return (String) jsonObject.get("db_name");
        }
        catch (Exception exc) {
            LOGGER.log(Level.SEVERE, NETWORK_CONFIG_ERROR);
            return Database.MYSQL_STANDARD_DB_NAME;
        }
    }

    /**
     * Gets the dbms port.
     *
     * @return the dbms port
     */
    private static int getDbmsPort() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new InputStreamReader(DataManager.class.getResourceAsStream(DATABASE_CONFIG_PATH)));
            JSONObject jsonObject = (JSONObject) obj;
            return Integer.parseInt((String) jsonObject.get("dbms_port"));
        }
        catch (Exception exc) {
            LOGGER.log(Level.SEVERE, NETWORK_CONFIG_ERROR);
            return Database.MYSQL_STANDARD_REGISTERED_PORT;
        }
    }
}
