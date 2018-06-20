package it.polimi.ingsw.database;

import it.polimi.ingsw.sagrada.database.Database;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DatabaseSQLRemoteConnectionTest {

    @Test
    public void testRemoteSQLConnection() {

            /*Database d = Database.initSQLDatabase("daniele_sagrada", "ge7npchy5", 100, "db4free.net", 3306, "sagrada_db_test");
            ResultSet set = d.executeRawQuery("SELECT ID FROM Test");
            while (set.next())
                assertEquals(1, Integer.parseInt(set.getString("ID")));*/

        try {
            Database d = Database.initSQLiteDatabase("root", "", 100, "Sagrada.db");
            ResultSet set = d.executeRawQuery("SELECT Username FROM User");
            assertNotNull(set);
        }
        catch (SQLException exc) {
            fail();
        }
    }
}
