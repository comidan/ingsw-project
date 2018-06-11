package it.polimi.ingsw.sagrada.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author daniele
 */
public abstract class Database
{
    public static final int MYSQL_STANDARD_REGISTERED_PORT = 3306;
    public static final String MYSQL_STANDARD_USERNAME = "root";
    public static final String MYSQL_STANDARD_AUTH = "";
    public static final String MYSQL_STANDARD_DB_NAME = "sagrada";

    protected String databaseURL;
    protected String username;
    protected String password;
    protected String maxPool;
    protected Connection connection;
    protected Properties properties;
    protected Statement statement;
    protected ResultSet resultSet;
    protected boolean multiThread = false;
    
    public static Database initSQLDatabase(String username, String password, int maxPool, String dbmsURL, int port, String dbName) throws SQLException
    {
        return new SQLDatabase(username, password, maxPool, dbmsURL, port, dbName);
    }
    
    public static Database initSQLDatabase(String username, String password, int maxPool, String dbmsURL, String dbName) throws SQLException
    {
        return new SQLDatabase(username, password, maxPool, dbmsURL, dbName);
    }
    
    public static Database initMSAccessDatabase(String username, String password, int maxPool, String dbmsURL, int port, String dbName) throws SQLException
    {
        return new MSAccessDatabase(username, password, maxPool, dbmsURL, port, dbName);
    }
    
    public static Database initMSAccessDatabase(String username, String password, int maxPool, String dbmsURL, String dbName) throws SQLException
    {
        return new MSAccessDatabase(username, password, maxPool, dbmsURL, dbName);
    }
    
    protected Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", username);
            properties.setProperty("password", password);
            properties.setProperty("MaxPooledStatements", maxPool);
        }
        return properties;
    }

    protected abstract Connection connect() throws ClassNotFoundException, SQLException;
    
    public Statement getStatement() throws SQLException
    {
        return connection.createStatement();
    }
    
    public void allowMultiThreadConcurrencyAccess() throws SQLException
    {
        connection.setAutoCommit(false);
        multiThread = true;
    }
    
    public ResultSet executeRawQuery(String query) throws SQLException
    {
        if(multiThread)
            statement = connection.createStatement();
        return statement.executeQuery(query);
    }
    
    public int executeUpdate(String queryUpdate) throws SQLException
    {
        if(multiThread)
            statement = connection.createStatement();
        return statement.executeUpdate(queryUpdate);
    }
    
    public PreparedStatement prepareQuery(String initQuery) throws SQLException
    {
        return connection.prepareStatement(initQuery);
    }
    
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
