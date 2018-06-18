package it.polimi.ingsw.sagrada.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * SQLiteDatabase module/library class
 *
 * @author daniele
 */
class SQLiteDatabase extends Database {

    /** The Constant DATABASE_DRIVER package */
    private static final String DATABASE_DRIVER = "org.sqlite.JDBC";

    private static final Logger LOGGER = Logger.getLogger(SQLiteDatabase.class.getName());

    /**
     * Instantiates a new SQL database.
     *
     * @param username DB access username
     * @param password DB access password
     * @param maxPool DB max pool
     * @param dbName DB name
     * @throws SQLException SQL exception on connection fault
     */
    SQLiteDatabase(String username, String password, int maxPool, String dbName) throws SQLException
    {
        this.databaseURL = "jdbc:sqlite::resource:database/" + dbName; //just for now, next to be synced with dynamic resource extracting with failure countermeasures on internal readonly db
        this.username = username;
        this.password = password;
        this.maxPool = maxPool + "";
        connection = connect();
        statement = connection.createStatement();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.database.Database#connect()
     */
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