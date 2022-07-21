package org.example;

import com.github.tomakehurst.wiremock.client.WireMock;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void  resetWireMock(WireMockRuntimeInfo wmRuntimeInfo){

        wmRuntimeInfo.getWireMock().resetMappings();

    }

    @Test
    public void executePreparedQuery_IfTrue(WireMockRuntimeInfo wmRuntimeInfo) throws IOException, SQLException{

        wmRuntimeInfo.getWireMock().stubFor(post(WireMock.urlEqualTo(url))
                .willReturn(okForContentType("text/plain",getCsvData())));

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);
        preparedStatement.setInt(1,getParameterValue());

        ResultSet result = preparedStatement.executeQuery();

        assertNotNull(result);

    }

    @Test
    public void executePreparedQuery_IfFalse_NotAllParameters() throws IOException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        try {

            preparedStatement.execute();
            fail();

        }catch(SQLException sqlException){

            assertNotNull(sqlException);

        }

    }

    @Test
    public void executePreparedQuery_IfFalse_HttpConnectionError() throws IOException, SQLException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);
        preparedStatement.setInt(1,getParameterValue());

        try {

            preparedStatement.execute();
            fail();

        }catch(SQLException sqlException){

            assertNotNull(sqlException);

        }

    }


    @Test
    public void executeSelectQuery_IfTrue_WithNames(WireMockRuntimeInfo wmRuntimeInfo) throws IOException, SQLException{

        wmRuntimeInfo.getWireMock().stubFor(post(WireMock.urlEqualTo(url))
                .willReturn(okForContentType("text/plain",getCsvData())));

        String sql = getSqlWithAlias();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        var result = preparedStatement.execute();

        assertTrue(result);

    }

    @Test
    public void executeSelectQuery_IfFalse_WrongNamesNumber(WireMockRuntimeInfo wmRuntimeInfo) throws IOException{

        wmRuntimeInfo.getWireMock().stubFor(post(WireMock.urlEqualTo(url))
                .willReturn(okForContentType("text/plain",getCsvData())));

        String sql = "select timestamp as t, age as a from table";

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);
        try {

           preparedStatement.execute();
            fail();

        }catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }

    @Test
    public void executeUpdateQuery_IfTrue(WireMockRuntimeInfo wmRuntimeInfo) throws IOException, SQLException{

        wmRuntimeInfo.getWireMock().stubFor(post(WireMock.urlEqualTo(url))
                .willReturn(okForContentType("text/plain",getCsvData())));

        String sql = getSqlForUpdate();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);
        preparedStatement.setInt(1,getParameterValue());
        preparedStatement.setInt(2,getParameterValue());

        var result = preparedStatement.executeUpdate();

        assertEquals(1, result);

    }

    @Test
    public void executeUpdateQuery_IfFalse_NotAllParameters() throws IOException, SQLException{

        String sql = getSqlForUpdate();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);
        preparedStatement.setInt(1, getParameterValue());

        try {

            preparedStatement.executeUpdate();
            fail();

        }catch(SQLException sqlException){

            assertNotNull(sqlException);

        }

    }

    @Test
    public void executeUpdateQuery_IfFalse_HttpConnectionError() throws IOException, SQLException{

        String sql = getSqlForUpdate();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);
        preparedStatement.setInt(1,getParameterValue());
        preparedStatement.setInt(2,getParameterValue());

        try {

            preparedStatement.executeUpdate();
            fail();

        }catch(SQLException sqlException){

            assertNotNull(sqlException);

        }

    }

    private static String getSqlForUpdate(){

        return "update table set field = ? where field = ?2";

    }

    private static String getSqlWithParameter(){

        return "select * from table where field = ?1";

    }

    private static String getSqlWithAlias(){

        return "select timestamp as t, age as a, gender as g from table";

    }


    private static int getParameterValue(){

        return 2022;

    }

    private static String getCsvData(){

        return "\"Timestamp\",\"Age\",\"Gender\" " +
                "\n 2014-08-27 11:29:31,37,\"Female\"";

    }


}
