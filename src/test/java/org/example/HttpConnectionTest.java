package org.example;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@Suite
public class HttpConnectionTest {

    private static String url="//localhost:8080/sql-mock";


    @Test
    public void connectionConstructor(){

        HttpConnection connection = new HttpConnection(url);

        assertEquals("http:" + url, connection.getUrl());

    }

    @Test
    public void connectionCreateStatement() throws SQLException{

        Connection connection = new HttpConnection(url);

        assertNotNull(connection.createStatement());

    }

    @Test
    public void connectionPrepareStatement() throws SQLException{

        Connection connection = new HttpConnection(url);

        assertNotNull(connection.prepareStatement("sql"));

    }

}
