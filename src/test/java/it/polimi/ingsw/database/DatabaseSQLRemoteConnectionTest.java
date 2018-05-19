package it.polimi.ingsw.database;

import it.polimi.ingsw.sagrada.database.Database;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DatabaseSQLRemoteConnectionTest {

    @Test
    public void remoteSQLConncection() throws SQLException,IOException {

        if(System.getProperty("os.name").toLowerCase().contains("windows") && !getConnectionName().toLowerCase().contains("polimi") && !getConnectionName().toLowerCase().contains("eduroam")) { //temporary
            Database d = Database.initSQLDatabase("daniele_sagrada", "ge7npchy5", 100, "db4free.net", 3306, "sagrada_db_test");
            ResultSet set = d.executeRawQuery("SELECT ID FROM Test");
            while (set.next())
                assertEquals(1, Integer.parseInt(set.getString("ID")));
        }
    }

    private String getConnectionName() throws IOException  {
        List<String> ssids=new ArrayList<String>();
        List<String>signals=new ArrayList<String>();
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "netsh lan show interfaces");
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (r.read()!=-1) {
            line = r.readLine();
            System.out.println(line);
            if (line.contains("SSID") || line.contains("Signal")) {
                line = line.substring(8);
                ssids.add(line);
            }
        }
        return ssids.isEmpty() ? "" : ssids.get(0);
    }
}
