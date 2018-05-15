package it.polimi.ingsw.sagrada.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniele
 */
class SQLDatabase extends Database {
    private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final Logger LOGGER = Logger.getLogger(SQLDatabase.class.getName());
    
    SQLDatabase(String username, String password, int maxPool, String dbmsURL, int port, String dbName) throws SQLException
    {
        this.databaseURL = "jdbc:mysql://"+dbmsURL+":"+port+"/"+dbName;
        this.username = username;
        this.password = password;
        this.maxPool = maxPool + "";
        connection = connect();
        statement = connection.createStatement();
    }
    
    SQLDatabase(String username, String password, int maxPool, String dbmsURL, String dbName) throws SQLException
    {
        this.databaseURL = "jdbc:mysql://"+dbmsURL+"/"+dbName;
        this.username = username;
        this.password = password;
        this.maxPool = maxPool + "";
        connection = connect();
        statement = connection.createStatement();
    }

    protected Connection connect() {
        if (connection == null) {
            try {
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(databaseURL, getProperties());
            } catch (ClassNotFoundException | SQLException exc) {
                LOGGER.log(Level.SEVERE, () -> "Fatal DB error : " + exc.getMessage());
            }
        }
        return connection;
    }

}