package org.example;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.*;

@Suite
public class HttpPreparedStatementParametersTest {

    private static String uri = "http://localhost:8080/sql-mock";

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

    @Test
    public void constructorSplitString_WithParameterAtEnd() throws IOException{

        String sql = "select * from table where id = ?1";

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        assertEquals("select * from table where id = ",
                preparedStatement.getQuery());

        assertEquals(1, preparedStatement.getParameters().length);

    }

    @Test
    public void setBoolean_IfTrue_TrueValue() throws SQLException, IOException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        preparedStatement.setBoolean(1, true);

        assertEquals("select * from table where field = 1",
                preparedStatement.getQuery());

    }

    @Test
    public void setBoolean_IfTrue_FalseValue() throws SQLException, IOException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        preparedStatement.setBoolean(1, false);

        assertEquals("select * from table where field = 0",
                preparedStatement.getQuery());

    }

    @Test
    public void setBoolean_IfFalse() throws IOException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        try {

            preparedStatement.setBoolean(0, true);

        }catch (SQLException exception){

            assertNotNull(exception);

        }

    }

    @Test
    public void checkIndexBoundsMoreThan() throws SQLException, IOException{

        String sql = "select * from table";

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        try {

            preparedStatement.setBoolean(1, true);

        }catch (SQLException exception){

            assertNotNull(exception);

        }

    }

    @Test
    public void setByte_IfTrue() throws SQLException, IOException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        byte value = getIntegerParameterValue();

        preparedStatement.setByte(1, value);

        assertEquals(getSqlWithIntegerParameter(), preparedStatement.getQuery());

    }

    @Test
    public void setByte_IfFalse() throws IOException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        byte value = getIntegerParameterValue();
        try {

            preparedStatement.setByte(0, value);

        }catch (SQLException exception){

            assertNotNull(exception);

        }

    }


    @Test
    public void setShort_IfTrue() throws SQLException, IOException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        short value = getIntegerParameterValue();

        preparedStatement.setShort(1, value);

        assertEquals(getSqlWithIntegerParameter(), preparedStatement.getQuery());

    }

    @Test
    public void setShort_IfFalse() throws IOException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        short value = getIntegerParameterValue();
        try {

            preparedStatement.setShort(0, value);

        }catch (SQLException exception){

            assertNotNull(exception);

        }

    }

    @Test
    public void setInt_IfTrue() throws SQLException, IOException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        int value = getIntegerParameterValue();

        preparedStatement.setInt(1, value);

        assertEquals(getSqlWithIntegerParameter(), preparedStatement.getQuery());

    }

    @Test
    public void setInt_IfFalse() throws IOException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        int value = getIntegerParameterValue();
        try {

            preparedStatement.setInt(0, value);

        }catch (SQLException exception){

            assertNotNull(exception);

        }

    }

    @Test
    public void setLong_IfTrue() throws SQLException, IOException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        long value = getIntegerParameterValue();

        preparedStatement.setLong(1, value);

        assertEquals(getSqlWithIntegerParameter(), preparedStatement.getQuery());

    }

    @Test
    public void setLong_IfFalse() throws IOException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        long value = getIntegerParameterValue();
        try {

            preparedStatement.setLong(0, value);

        }catch (SQLException exception){

            assertNotNull(exception);

        }

    }

    @Test
    public void setFloat_IfTrue() throws SQLException, IOException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        float value = getRealParameterValue();

        preparedStatement.setFloat(1, value);

        assertEquals(getSqlWithRealParameter(), preparedStatement.getQuery());

    }

    @Test
    public void setFloat_IfFalse() throws IOException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        float value = getRealParameterValue();
        try {

            preparedStatement.setFloat(0, value);

        }catch (SQLException exception){

            assertNotNull(exception);

        }

    }

    @Test
    public void setDouble_IfTrue() throws SQLException, IOException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        Double value = Double.valueOf(getRealParameterValue());

        preparedStatement.setDouble(1, value);

        assertEquals(getSqlWithRealParameter(), preparedStatement.getQuery());

    }

    @Test
    public void setDouble_IfFalse() throws IOException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        Double value = Double.valueOf(getRealParameterValue());
        try {

            preparedStatement.setDouble(0, value);

        }catch (SQLException exception){

            assertNotNull(exception);

        }

    }

    @Test
    public void setBigDecimal_IfTrue() throws SQLException, IOException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        BigDecimal value = BigDecimal.valueOf(getRealParameterValue());

        preparedStatement.setBigDecimal(1, value);

        assertEquals(getSqlWithRealParameter(), preparedStatement.getQuery());

    }

    @Test
    public void setBigDecimal_IfFalse() throws IOException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        BigDecimal value = BigDecimal.valueOf(getRealParameterValue());
        try {

            preparedStatement.setBigDecimal(0, value);

        }catch (SQLException exception){

            assertNotNull(exception);

        }

    }


    @Test
    public void setString_IfTrue() throws SQLException, IOException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        preparedStatement.setString(1,"value");

        assertEquals(getSqlWithStringParameter(), preparedStatement.getQuery());

    }

    @Test
    public void setString_IfFalse() throws IOException{

        String sql = getSqlWithParameter();

        HttpPreparedStatement preparedStatement = new HttpPreparedStatement(uri,sql);

        try {

            preparedStatement.setString(0, "");

        }catch (SQLException exception){

            assertNotNull(exception);

        }

    }

    private static String getSqlWithParameter(){

        return "select * from table where field = ?1";

    }


    private static byte getIntegerParameterValue(){

        return 101;

    }

    private static float getRealParameterValue(){

        return 45.5f;

    }

    private static String getSqlWithIntegerParameter(){

        return "select * from table where field = 101";

    }

    private static String getSqlWithRealParameter(){

        return "select * from table where field = 45.5";

    }

    private static String getSqlWithStringParameter(){

        return "select * from table where field = 'value'";

    }


}
