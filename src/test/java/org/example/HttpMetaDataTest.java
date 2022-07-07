package org.example;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@Suite
public class HttpMetaDataTest {


    private static final String url="jdbc:wm://localhost:8080/sql-mock";

    private static Connection connection ;

    @BeforeAll
    static void setConnection() throws SQLException{

        connection = DriverManager.getConnection(url);

    }

    @Test
    public void getDatabaseProductName() throws SQLException{

        DatabaseMetaData metaData = connection.getMetaData();

        assertNotNull(metaData.getDatabaseProductName());

    }

    @Test
    public void getDatabaseProductVersion() throws SQLException{

        DatabaseMetaData metaData = connection.getMetaData();

        assertNotNull(metaData.getDatabaseProductVersion());

    }

    @Test
    public void getDriverName() throws SQLException{

        DatabaseMetaData metaData = connection.getMetaData();

        assertNotNull(metaData.getDriverName());

    }

    @Test
    public void getDriverVersion() throws SQLException{

        DatabaseMetaData metaData = connection.getMetaData();

        assertNotNull(metaData.getDriverVersion());

    }

    @Test
    public void getDriverMajorVersion() throws SQLException{

        DatabaseMetaData metaData = connection.getMetaData();
        var r = metaData.getDriverMajorVersion();

        assertTrue(true);

    }

    @Test
    public void getDriverMinorVersion() throws SQLException{

        DatabaseMetaData metaData = connection.getMetaData();
        var r = metaData.getDriverMinorVersion();

        assertTrue(true);

    }

}
