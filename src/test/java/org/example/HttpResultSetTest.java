package org.example;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

@Suite
public class HttpResultSetTest {

    private static final String abracadabra = "abracadabra";

    private static HttpResultSet httpResultSet;

    @BeforeEach
    public void setHttpResultSet() throws CsvException, IOException {

        httpResultSet = new HttpResultSet(getCsvData());

    }

    @Test
    public void constructor_ifTrue(){

        assertNotNull(httpResultSet);

    }

    @Test
    public void testNext() throws SQLException{

        //csv data has only 2 rows
        assertTrue(httpResultSet.next());
        assertTrue(httpResultSet.next());
        assertFalse(httpResultSet.next());

    }

    @Test
    public void getXXXByIndex_IfFalse_IndexOutOfRange_LessZero() throws SQLException{

        httpResultSet.next();

        try {

            httpResultSet.getBoolean(-1);

        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }

    @Test
    public void getXXXByIndex_IfFalse_IndexOutOfRange_MoreThanSize() throws SQLException{

        httpResultSet.next();

        try {

            httpResultSet.getBoolean(Integer.MAX_VALUE);

        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }

    @Test
    public void getStringByIndex_IfTrue() throws SQLException{

        httpResultSet.next();

        String value = httpResultSet.getString(8);

        assertEquals(getString(),value);


    }


    @Test
    public void getBooleanByIndex_IfTrue_TrueValue() throws SQLException{

        httpResultSet.next();

        boolean value = httpResultSet.getBoolean(0);

        assertTrue(value);

    }

    @Test
    public void getBooleanByIndex_IfTrue_FalseValue() throws SQLException{

        httpResultSet.next();
        httpResultSet.next();

        boolean value = httpResultSet.getBoolean(0);

       assertFalse(value);

    }

    @Test
    public void getBooleanByIndex_IfFalse_WrongFormat() throws SQLException{


        httpResultSet.next();

        try {

            httpResultSet.getBoolean(1);

        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }

    @Test
    public void getByteByIndex_IfTrue() throws SQLException{

        httpResultSet.next();

        byte value = httpResultSet.getByte(1);

        assertEquals(getByte(),value);

    }

    @Test
    public void getByteByIndex_IfFalse_WrongFormat() throws SQLException{

        httpResultSet.next();

        try {

            httpResultSet.getByte(8);

        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }

    @Test
    public void getShortByIndex_IfTrue() throws SQLException{

        httpResultSet.next();

        short value = httpResultSet.getShort(2);

        assertEquals(getShort(),value);

    }

    @Test
    public void getShortByIndex_IfFalse_WrongFormat() throws SQLException{

        httpResultSet.next();

        try {

            httpResultSet.getShort(8);

        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }

    @Test
    public void getIntByIndex_IfTrue() throws SQLException{

        httpResultSet.next();

        int value = httpResultSet.getInt(3);

        assertEquals(getInt(),value);

    }

    @Test
    public void getIntByIndex_IfFalse_WrongFormat() throws SQLException{

        httpResultSet.next();

        try {

            httpResultSet.getInt(8);

        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }

    @Test
    public void getLongByIndex_IfTrue() throws SQLException{

        httpResultSet.next();

        long value = httpResultSet.getLong(4);

        assertEquals(getLong(),value);

    }

    @Test
    public void getLongByIndex_IfFalse_WrongFormat() throws SQLException{

        httpResultSet.next();

        try {

            httpResultSet.getLong(8);

        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }
    @Test
    public void getFloatByIndex_IfTrue() throws SQLException{

        httpResultSet.next();

        float value = httpResultSet.getFloat(5);

        assertEquals(getFloat(),value);

    }

    @Test
    public void getFloatByIndex_IfFalse_WrongFormat() throws SQLException{

        httpResultSet.next();

        try {

            httpResultSet.getFloat(8);

        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }

    @Test
    public void getDoubleByIndex_IfTrue() throws SQLException{

        httpResultSet.next();

        double value = httpResultSet.getDouble(6);

        assertEquals(getDouble(),value);

    }

    @Test
    public void getDoubleByIndex_IfFalse_WrongFormat() throws SQLException{

        httpResultSet.next();

        try {

            httpResultSet.getDouble(8);

        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }
    @Test
    public void getBigDecimalByIndex_IfTrue() throws SQLException{

        httpResultSet.next();

        BigDecimal value = httpResultSet.getBigDecimal(7);

        assertEquals(getBigDecimal(),value);

    }

    @Test
    public void getBigDecimalByIndex_IfFalse_WrongFormat() throws SQLException{

        httpResultSet.next();

        try {

            httpResultSet.getBigDecimal(8);

        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }


    @Test
    public void getStringByColumnLabel_IfTrue() throws SQLException{

        httpResultSet.next();

        String value = httpResultSet.getString(getStringName());

        assertEquals(getString(),value);


    }

    @Test
    public void getStringByColumnLabel_IfFalse_WrongLabel() throws SQLException{

        httpResultSet.next();

        try {

            httpResultSet.getString(abracadabra);

        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }


    @Test
    public void getBooleanByColumnLabel_IfTrue_TrueValue() throws SQLException{

        httpResultSet.next();

        boolean value = httpResultSet.getBoolean(getBooleanName());

        assertTrue(value);

    }


    @Test
    public void getBooleanByColumnLabel_IfFalse_WrongLabel() throws SQLException{

        httpResultSet.next();

        try {

            httpResultSet.getBoolean(abracadabra);

        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }

    @Test
    public void getByteByColumnLabel_IfTrue() throws SQLException{

        httpResultSet.next();

        byte value = httpResultSet.getByte(getByteName());

        assertEquals(getByte(),value);

    }

    @Test
    public void getByteByColumnLabel_IfFalse_WrongLabel() throws SQLException{

        httpResultSet.next();

        try {

            httpResultSet.getByte(abracadabra);

        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }

    @Test
    public void getShortByColumnLabel_IfTrue() throws SQLException{

        httpResultSet.next();

        short value = httpResultSet.getShort(getShortName());

        assertEquals(getShort(),value);

    }

    @Test
    public void getShortByColumnLabel_IfFalse_WrongLabel() throws SQLException{

        httpResultSet.next();

        try {

            httpResultSet.getShort(abracadabra);

        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }

    @Test
    public void getIntByColumnLabel_IfTrue() throws SQLException{

        httpResultSet.next();

        int value = httpResultSet.getInt(getIntName());

        assertEquals(getInt(),value);

    }

    @Test
    public void getIntByColumnLabel_IfFalse_WrongLabel() throws SQLException{

        httpResultSet.next();

        try {

            httpResultSet.getInt(abracadabra);

        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }

    @Test
    public void getLongByColumnLabel_IfTrue() throws SQLException{

        httpResultSet.next();

        long value = httpResultSet.getLong(getLongName());

        assertEquals(getLong(),value);

    }

    @Test
    public void getLongByColumnLabel_IfFalse_WrongLabel() throws SQLException{

        httpResultSet.next();

        try {

            httpResultSet.getLong(abracadabra);

        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }
    @Test
    public void getFloatByColumnLabel_IfTrue() throws SQLException{

        httpResultSet.next();

        float value = httpResultSet.getFloat(getFloatName());

        assertEquals(getFloat(),value);

    }

    @Test
    public void getFloatByColumnLabel_IfFalse_WrongLabel() throws SQLException{

        httpResultSet.next();

        try {

            httpResultSet.getFloat(abracadabra);

        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }

    @Test
    public void getDoubleByColumnLabel_IfTrue() throws SQLException{

        httpResultSet.next();

        double value = httpResultSet.getDouble(getDoubleName());

        assertEquals(getDouble(),value);

    }

    @Test
    public void getDoubleByColumnLabel_IfFalse_WrongLabel() throws SQLException{

        httpResultSet.next();

        try {

            httpResultSet.getDouble(abracadabra);

        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }
    @Test
    public void getBigDecimalByColumnLabel_IfTrue() throws SQLException{

        httpResultSet.next();

        BigDecimal value = httpResultSet.getBigDecimal(getBigDecimalName());

        assertEquals(getBigDecimal(),value);

    }

    @Test
    public void getBigDecimalByColumnLabel_IfFalse_WrongLabel() throws SQLException{

        httpResultSet.next();

        try {

            httpResultSet.getBigDecimal(abracadabra);

        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }



    static String getCsvData(){

        return  "\"" + getBooleanName()+ "\""
                + "," + "\"" + getByteName() + "\""
                + "," + "\"" + getShortName() + "\""
                + "," + "\"" + getIntName() + "\""
                + "," + "\"" + getLongName() + "\""
                + "," + "\"" + getFloatName() + "\""
                + "," + "\"" + getDoubleName() + "\""
                + "," + "\"" + getBigDecimalName() + "\""
                + "," + "\"" + getStringName() + "\""

                + "\n 1," + getByte()
                + "," + getShort()
                + "," + getInt()
                + "," + getLong()
                + "," + getFloat()
                + "," + getDouble()
                + "," + getBigDecimal()
                + "," + getString()

                + "\n 0, 0, 0, 0, 0, 0.05, 9.75, 9.75, \"test\"";


    }
    static String getBooleanName(){

        return "bool";

    }

    static String getByteName(){

        return "byte";

    }

    static String getShortName(){

        return "short";

    }

    static String getIntName(){

        return "int";

    }

    static String getLongName(){

        return "long";

    }

    static String getFloatName(){

        return "float";

    }

    static String getDoubleName(){

        return "double";

    }

    static String getBigDecimalName(){

        return "big_decimal";

    }

    static String getStringName(){

        return "string";

    }

    static byte getByte(){

        return (byte) 255;

    }

    static short getShort(){

        return (short)101;

    }

    static int getInt(){

       return 2022;

    }

    static long getLong(){

        return 20322322322L;

    }

    static float getFloat(){

        return 9.75f;

    }

    static double getDouble(){

        return 9.75;

    }

    static BigDecimal getBigDecimal(){

        return BigDecimal.valueOf(2323232.238263);

    }

    static String getString(){

        return "TEST";

    }


}
