package it.polimi.ingsw.sagrada.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * MSAccessDatabase moduele/library class.
 *
 * @author daniele
 */
class MSAccessDatabase extends Database {
    
    /**  The Constant DATABASE_DRIVER package. */
    private static final String DATABASE_DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(MSAccessDatabase.class.getName());
    
    /**
     * Instantiates a new MS access database.
     *
     * @param username DB access username
     * @param password DB access password
     * @param maxPool DB max pool
     * @param dbmsURL DBMS URL
     * @param port DBMS port
     * @param dbName DB name
     * @throws SQLException SQL exception on connection fault
     */
    MSAccessDatabase(String username, String password, int maxPool, String dbmsURL, int port, String dbName) throws SQLException
    {
        this.databaseURL = "jdbc:ucanaccess://"+dbmsURL+":"+port+"/"+dbName;
        this.username = username;
        this.password = password;
        this.maxPool = maxPool + "";
        connection = connect();
        statement = connection.createStatement();
    }
    
    /**
     * Instantiates a new MS access database using default port.
     *
     * @param username DB access username
     * @param password DB access password
     * @param maxPool DB max pool
     * @param dbmsURL DBMS URL
     * @param dbName DB name
     * @throws SQLException SQL exception on connection fault
     */
    MSAccessDatabase(String username, String password, int maxPool, String dbmsURL, String dbName) throws SQLException
    {
        this.databaseURL = "jdbc:ucanaccess://"+dbmsURL+"/"+dbName;
        this.username = username;
        this.password = password;
        this.maxPool = maxPool + "";
        connection = connect();
        statement = connection.createStatement();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.database.Database#connect()
     */
    protected Connection connect() throws SQLException {
        if(connection == null)
        {
            try
            {
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(databaseURL, getProperties());
            }
            catch(ClassNotFoundException exc)
            {
                LOGGER.log(Level.SEVERE, () -> "Fatal DB error : " + exc.getMessage());
            }
            
            
        }
        return connection;
    }

}