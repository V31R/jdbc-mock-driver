package org.example;

import com.github.tomakehurst.wiremock.client.WireMock;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;

@WireMockTest
public class HttpPreparedStatementTest {

    private static String host="http://localhost:";
    private static String url="/sql-mock";
    private static String uri;

    @BeforeAll
    static void startWireMock(WireMockRuntimeInfo wmRuntimeInfo){

        uri = host + wmRuntimeInfo.getHttpPort() + url;

    }

    @Test
    public void testWireMockSetUp(WireMockRuntimeInfo wmRuntimeInfo){

        assertEquals(host + wmRuntimeInfo.getHttpPort(),wmRuntimeInfo.getHttpBaseUrl());

    }

    @Test
    public void constructorSplitString_WithoutParameters() throws IOException{

        String sql = "select * from table";

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        assertEquals(sql,preparedStatement.getQuery());

    }

    @Test
    public void constructorSplitString_WithParameters() throws IOException{

        String sql = "select * from table where id = ?1 and name =?2 order by name";

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        assertEquals("select * from table where id =  and name = order by name",
                preparedStatement.getQuery());

    }

}
