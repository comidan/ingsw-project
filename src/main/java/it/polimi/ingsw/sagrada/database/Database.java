package it.polimi.ingsw.sagrada.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


/**
 * Main database class describing its main properties and leaving specific ones as abstractions
 *
 * @author daniele
 */
public abstract class Database
{
    
    /** The Constant MYSQL_STANDARD_REGISTERED_PORT as default port in case of missing json config*/
    public static final int MYSQL_STANDARD_REGISTERED_PORT = 3306;
    
    /** The Constant MYSQL_STANDARD_USERNAME as default username in case of missing json config*/
    public static final String MYSQL_STANDARD_USERNAME = "root";
    
    /** The Constant MYSQL_STANDARD_AUTH as default password in case of missing json config*/
    public static final String MYSQL_STANDARD_AUTH = "";
    
    /** The Constant MYSQL_STANDARD_DB_NAME as default db name in case of missing json config */
    public static final String MYSQL_STANDARD_DB_NAME = "sagrada";

    protected String databaseURL;

    protected String username;

    protected String password;

    protected String maxPool;

    protected Connection connection;

    protected Properties properties;

    protected Statement statement;

    protected ResultSet resultSet;
    
    /** Describing multi thread status by allowing multiple queries on the same connection by disallowing auto commit */
    protected boolean multiThread = false;
    
    /**
     * Init the SQL database connection using personalized port
     *
     * @param username DB access username
     * @param password DB access password
     * @param maxPool DB max pool
     * @param dbmsURL DBMS URL
     * @param port DBMS port
     * @param dbName DB name
     * @return database connection instance
     * @throws SQLException SQL exception on connection fault
     */
    public static Database initSQLDatabase(String username, String password, int maxPool, String dbmsURL, int port, String dbName) throws SQLException
    {
        return new SQLDatabase(username, password, maxPool, dbmsURL, port, dbName);
    }
    
    /**
     * Init the SQL database connection using the default registered SQL port 3306, defined above as constant
     *
     * @param username DB access username
     * @param password DB access password
     * @param maxPool DB max pool
     * @param dbmsURL DBMS URL
     * @param dbName DB name
     * @return database connection instance
     * @throws SQLException SQL exception on connection fault
     */
    public static Database initSQLDatabase(String username, String password, int maxPool, String dbmsURL, String dbName) throws SQLException
    {
        return new SQLDatabase(username, password, maxPool, dbmsURL, dbName);
    }
    
    /**
     * Init the MS access database connection.
     *
     * @param username DB access username
     * @param password DB access password
     * @param maxPool DB max pool
     * @param dbmsURL DBMS URL
     * @param port DBMS port
     * @param dbName DB name
     * @return database connection instance
     * @throws SQLException SQL exception on connection fault
     */
    public static Database initMSAccessDatabase(String username, String password, int maxPool, String dbmsURL, int port, String dbName) throws SQLException
    {
        return new MSAccessDatabase(username, password, maxPool, dbmsURL, port, dbName);
    }
    
    /**
     * Init the MS Access database connection using the default registered SQL port 3306, defined above as constant
     *
     * @param username DB access username
     * @param password DB access password
     * @param maxPool DB max pool
     * @param dbmsURL DBMS URL
     * @param dbName DB name
     * @return database connection instance
     * @throws SQLException SQL exception on connection fault
     */
    public static Database initMSAccessDatabase(String username, String password, int maxPool, String dbmsURL, String dbName) throws SQLException
    {
        return new MSAccessDatabase(username, password, maxPool, dbmsURL, dbName);
    }

    /**
     * Init the SQLite database connection using personalized port
     *
     * @param username DB access username
     * @param password DB access password
     * @param maxPool DB max pool
     * @param dbName DB name
     * @return database connection instance
     * @throws SQLException SQL exception on connection fault
     */
    public static Database initSQLiteDatabase(String username, String password, int maxPool, String dbName) throws SQLException
    {
        return new SQLiteDatabase(username, password, maxPool, dbName);
    }
    
    /**
     * Gets the properties for database connection : internal use only
     *
     * @return database connection properties
     */
    protected Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", username);
            properties.setProperty("password", password);
            properties.setProperty("MaxPooledStatements", maxPool);
        }
        return properties;
    }

    /**
     * Connect to specified database
     *
     * @return DB connection
     * @throws ClassNotFoundException probably mis configured JDBC library
     * @throws SQLException SQL exception on connection fault
     */
    protected abstract Connection connect() throws ClassNotFoundException, SQLException;
    
    /**
     * Gets query statement
     *
     * @return statement
     * @throws SQLException SQL exception on connection
     */
    public Statement getStatement() throws SQLException
    {
        return connection.createStatement();
    }
    
    /**
     * Allow multi thread concurrency access.
     *
     * @throws SQLException SQL exception on connection or query fault
     */
    public void allowMultiThreadConcurrencyAccess() throws SQLException
    {
        connection.setAutoCommit(false);
        multiThread = true;
    }
    
    /**
     * Execute raw query on database
     *
     * @param query query
     * @return result set to iterate over results
     * @throws SQLException SQL exception on connection or query fault
     */
    public ResultSet executeRawQuery(String query) throws SQLException
    {
        if(multiThread)
            statement = connection.createStatement();
        return statement.executeQuery(query);
    }
    
    /**
     * Execute update query
     *
     * @param queryUpdate update query
     * @return return statement of Statement.executeUpdate(String query)
     * @throws SQLException SQL exception on connection or update query fault
     */
    public int executeUpdate(String queryUpdate) throws SQLException
    {
        if(multiThread)
            statement = connection.createStatement();
        return statement.executeUpdate(queryUpdate);
    }
    
    /**
     * Prepare query using statement
     *
     * @param initQuery initial generic query
     * @return prepared statement to insert values
     * @throws SQLException SQL exception on connection fault
     */
    public PreparedStatement prepareQuery(String initQuery) throws SQLException
    {
        return connection.prepareStatement(initQuery);
    }
    
    /**
     * Disconnect from database
     *
     * @throws SQLException SQL exception on connection fault
     */
    public void disconnect() throws SQLException 
    {
        if (connection != null)
        {
                connection.close();
                connection = null;
        }
        else throw new NullPointerException("There is no connection to any database");
    }
}