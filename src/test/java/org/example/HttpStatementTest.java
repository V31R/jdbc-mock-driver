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
public class HttpStatementTest {

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
    public void requestTest(WireMockRuntimeInfo wmRuntimeInfo) throws IOException, SQLException {

        wmRuntimeInfo.getWireMock().stubFor(post(WireMock.urlEqualTo(url))
                .willReturn(okForContentType("text/plain","\"Timestamp\",\"Age\",\"Gender\" " +
                        "\n 2014-08-27 11:29:31,37,\"Female\"")));

        HttpStatement testStatement = new HttpStatement(uri);
        var result = testStatement.executeQuery("select * from table;");


        assertNotNull(result);

    }



}


