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
class MSAccessDatabase extends Database {
    private static final String DATABASE_DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";
    private static final Logger LOGGER = Logger.getLogger(MSAccessDatabase.class.getName());
    
    MSAccessDatabase(String username, String password, int maxPool, String dbmsURL, int port, String dbName) throws SQLException
    {
        this.databaseURL = "jdbc:ucanaccess://"+dbmsURL+":"+port+"/"+dbName;
        this.username = username;
        this.password = password;
        this.maxPool = maxPool + "";
        connection = connect();
        statement = connection.createStatement();
    }
    
    MSAccessDatabase(String username, String password, int maxPool, String dbmsURL, String dbName) throws SQLException
    {
        this.databaseURL = "jdbc:ucanaccess://"+dbmsURL+"/"+dbName;
        this.username = username;
        this.password = password;
        this.maxPool = maxPool + "";
        connection = connect();
        statement = connection.createStatement();
    }

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