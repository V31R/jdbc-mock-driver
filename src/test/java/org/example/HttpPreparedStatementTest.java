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

    private static final String host="http://localhost:";
    private static final String url="/sql-mock";
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
    public void executePreparedQuery_IfTrue() throws IOException, SQLException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);
        preparedStatement.setInt(1,getParameterValue());

    }

    @Test
    public void executePreparedQuery_IfFalse() throws IOException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        try {

            preparedStatement.execute();

        }catch(SQLException sqlException){

            assertNotNull(sqlException);

        }


    }


    private static String getSqlWithParameter(){

        return "select * from table where field = ?1";

    }


    private static int getParameterValue(){

        return 2022;

    }




}
