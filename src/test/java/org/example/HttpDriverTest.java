package org.example;


import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Suite
public class HttpDriverTest {

    static String driverName="jdbc:wm:";
    static String host="//localhost";
    static String port="8080";
    static String url ="/sql-mock";

    @Test
    public void driverLoadConnection_IfTrue() throws SQLException {

        HttpDriver testDriver = new HttpDriver();
        Connection con = testDriver.connect(driverName + host + ":" + port + url, null);

        assertNotNull(con);

    }


    @Test
    public void driverLoadConnection_IfFalse_WrongDriverName() throws SQLException {

        HttpDriver testDriver = new HttpDriver();

        Connection connection = testDriver.connect("abracadabra" + host + ":" + port + url, null);

        assertNull(connection);

    }

    @Test
    public void driverLoadConnection_IfFalse_WrongHost() throws SQLException {

        HttpDriver testDriver = new HttpDriver();

        Connection connection = testDriver.connect(driverName + "abracadabra" + ":" + port + url, null);

        assertNull(connection);

    }

    @Test
    public void driverLoadConnection_IfFalse_OnlyDriverName() throws SQLException {

        HttpDriver testDriver = new HttpDriver();

        Connection connection = testDriver.connect(driverName, null);

        assertNull(connection);

    }

}
