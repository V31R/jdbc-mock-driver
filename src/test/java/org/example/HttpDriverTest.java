package org.example;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Suite
public class HttpDriverTest {

    static String driverName="jdbc:wm:";
    static String host="//localhost:";
    static String port="8080";
    static String url ="/sql-mock";
    static String abracadabra = "abracadabra";

    static String driverUri_Correct;
    static String driverUri_Wrong_DriverName;
    static String driverUri_Wrong_Host;
    static String driverUri_Missing_DriverName ;
    static String driverUri_Missing_Host ;
    static String driverUri_Missing_Port;
    static String driverUri_Missing_Url;

    static Driver httpDriver;

    @BeforeAll
    static void setCaseString(){

        driverUri_Correct = driverName + host  + port + url;

        driverUri_Wrong_DriverName = abracadabra + host  + port + url;
        driverUri_Wrong_Host = driverName + abracadabra  + port + url;

        driverUri_Missing_DriverName = host  + port + url;
        driverUri_Missing_Host = driverName +  port + url;
        driverUri_Missing_Port = driverName + host + url;
        driverUri_Missing_Url = driverName + host + port;

    }

    @BeforeAll
    static void setHttpDriver(){

       httpDriver = HttpDriver.load();

    }

    @Test
    public void driverLoadedSuccessfully() throws SQLException {

        assertNotNull(DriverManager.getDriver(driverUri_Correct));

    }


    @Test
    public void driverLoadConnection_IfTrue() throws SQLException {

        Connection con = httpDriver.connect(driverUri_Correct, null);

        assertNotNull(con);

    }


    @Test
    public void driverLoadConnection_IfFalse_WrongDriverName() throws SQLException {

        Connection con = httpDriver.connect(driverUri_Wrong_DriverName, null);

        assertNull(con);

    }

    @Test
    public void driverLoadConnection_IfFalse_WrongHost() throws SQLException {

        Connection con = httpDriver.connect(driverUri_Wrong_Host, null);

        assertNull(con);

    }

    @Test
    public void driverLoadConnection_IfFalse_Missing_DriverName() throws SQLException {

        Connection con = httpDriver.connect(driverUri_Missing_DriverName, null);

        assertNull(con);

    }

    @Test
    public void driverLoadConnection_IfFalse_Missing_Host() throws SQLException {

        Connection con = httpDriver.connect(driverUri_Missing_DriverName, null);

        assertNull(con);

    }

    @Test
    public void driverLoadConnection_IfFalse_Missing_Port() throws SQLException {

        Connection con = httpDriver.connect(driverUri_Missing_DriverName, null);

        assertNull(con);

    }

    @Test
    public void driverLoadConnection_IfFalse_Missing_Url() throws SQLException {

        Connection con = httpDriver.connect(driverUri_Missing_Url, null);

        assertNull(con);

    }

    @Test
    public void driverAcceptsUrl_IfTrue() throws SQLException {

        assertTrue(httpDriver.acceptsURL(driverUri_Correct));

    }


    @Test
    public void driverAcceptsUrl_IfFalse_WrongDriverName() throws SQLException {

        assertFalse(httpDriver.acceptsURL(driverUri_Wrong_DriverName));

    }

    @Test
    public void driverAcceptsUrl_IfFalse_WrongHost() throws SQLException {

        assertFalse(httpDriver.acceptsURL(driverUri_Wrong_Host));

    }

    @Test
    public void driverAcceptsUrl_IfFalse_Missing_DriverName() throws SQLException {

        assertFalse(httpDriver.acceptsURL(driverUri_Missing_DriverName));

    }

    @Test
    public void driverAcceptsUrl_IfFalse_Missing_Host() throws SQLException {

        assertFalse(httpDriver.acceptsURL(driverUri_Missing_Host));

    }

    @Test
    public void driverAcceptsUrl_IfFalse_Missing_Port() throws SQLException {

        assertFalse(httpDriver.acceptsURL(driverUri_Missing_Port));

    }

    @Test
    public void driverAcceptsUrl_IfFalse_Missing_Url() throws SQLException {

        assertFalse(httpDriver.acceptsURL(driverUri_Missing_Url));

    }


}
