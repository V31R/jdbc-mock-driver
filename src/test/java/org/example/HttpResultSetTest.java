package org.example;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.channels.FileLock;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Suite
public class HttpResultSetTest {

    private static final String abracadabra = "abracadabra";

    private static HttpResultSet httpResultSet;

    private static List<TypeAndValue> dataForCsv;
    private static Map<Class, Integer> typeIndexes;

    private static String csvData;

    @BeforeAll
    static public void setDataForCsv(){

        dataForCsv = new ArrayList<>();
        typeIndexes = new HashMap<>();

        (new Util<Byte>()).putValue((byte)255);
        (new Util<Short>()).putValue((short)101);
        (new Util<Integer>()).putValue(2022);
        (new Util<Long>()).putValue(2736283623L);
        (new Util<Float>()).putValue(9.75F);
        (new Util<Double>()).putValue(45.5);
        (new Util<BigDecimal>()).putValue(new BigDecimal("1.5"));
        (new Util<String>()).putValue("TEST");
        (new Util<Date>()).putValue(Date.valueOf("2022-06-20"));


        //name row
        StringBuilder csv = new StringBuilder("\"bool\"");
        for (var typeData: dataForCsv) {

            csv.append(", \"").append(typeData.name).append('"');

        }
        //first row
        csv.append("\n 1");
        for (var typeData: dataForCsv) {

            csv.append(',').append(typeData.value);

        }

        //second row
        csv.append("\n 0");
        for (var typeData: dataForCsv) {

            csv.append(',').append(typeData.value);

        }

        csvData = csv.toString();

    }


    @BeforeEach
    public void setHttpResultSet() throws CsvException, IOException {

        httpResultSet = new HttpResultSet(getCsvData());

    }

    @Test
    public void constructor_IfTrue(){

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

        int index = typeIndexes.get(String.class);

        httpResultSet.next();

        String value = httpResultSet.getString(index);

        assertEquals(dataForCsv.get(--index).value,value);


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

        int index = typeIndexes.get(Byte.class);

        httpResultSet.next();

        byte value = httpResultSet.getByte(index);

        assertEquals(dataForCsv.get(--index).value,value);

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

        int index = typeIndexes.get(Short.class);

        httpResultSet.next();

        short value = httpResultSet.getShort(index);

        assertEquals(dataForCsv.get(--index).value,value);

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

        int index = typeIndexes.get(Integer.class);

        httpResultSet.next();

        int value = httpResultSet.getInt(index);

        assertEquals(dataForCsv.get(--index).value,value);

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

        int index = typeIndexes.get(Long.class);

        httpResultSet.next();

        long value = httpResultSet.getLong(index);

        assertEquals(dataForCsv.get(--index).value,value);

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

        int index = typeIndexes.get(Float.class);

        httpResultSet.next();

        float value = httpResultSet.getFloat(index);

        assertEquals(dataForCsv.get(--index).value,value);

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

        int index = typeIndexes.get(Double.class);

        httpResultSet.next();

        double value = httpResultSet.getDouble(index);

        assertEquals(dataForCsv.get(--index).value,value);

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

        int index = typeIndexes.get(BigDecimal.class);

        httpResultSet.next();

        BigDecimal value = httpResultSet.getBigDecimal(index);

        assertEquals(dataForCsv.get(--index).value,value);

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

        Class clazz = String.class;

        httpResultSet.next();

        String value = httpResultSet.getString(clazz.getName());

        assertEquals(dataForCsv.get(typeIndexes.get(clazz) - 1).value,value);


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

        Class clazz = Byte.class;

        httpResultSet.next();

        byte value = httpResultSet.getByte(clazz.getName());

        assertEquals(dataForCsv.get(typeIndexes.get(clazz) - 1).value,value);

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

        Class clazz = Short.class;

        httpResultSet.next();

        short value = httpResultSet.getShort(clazz.getName());

        assertEquals(dataForCsv.get(typeIndexes.get(clazz) - 1).value,value);

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

        Class clazz = Integer.class;

        httpResultSet.next();

        int value = httpResultSet.getInt(clazz.getName());

        assertEquals(dataForCsv.get(typeIndexes.get(clazz) - 1).value,value);

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

        Class clazz = Long.class;

        httpResultSet.next();

        long value = httpResultSet.getLong(clazz.getName());

        assertEquals(dataForCsv.get(typeIndexes.get(clazz) - 1).value,value);

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

        Class clazz = Float.class;

        httpResultSet.next();

        float value = httpResultSet.getFloat(clazz.getName());

        assertEquals(dataForCsv.get(typeIndexes.get(clazz) - 1).value,value);;

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

        Class clazz = Double.class;

        httpResultSet.next();

        double value = httpResultSet.getDouble(clazz.getName());

        assertEquals(dataForCsv.get(typeIndexes.get(clazz) - 1).value,value);

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

        Class clazz = BigDecimal.class;

        httpResultSet.next();

        BigDecimal value = httpResultSet.getBigDecimal(clazz.getName());

        assertEquals(dataForCsv.get(typeIndexes.get(clazz) - 1).value,value);

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

        return csvData;

        /*return  "\"" + getBooleanName()+ "\""
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

                + "\n 0, 0, 0, 0, 0, 0.05, 9.75, 9.75, \"test\"";*/


    }


    static class TypeAndValue<T>{

        String name;
        T value;

        public TypeAndValue(T value) {
            this.name = value.getClass().getName();
            this.value = value;
        }
    }

    static class Util<T>{


        void putValue(T value){

            dataForCsv.add(new TypeAndValue<>(value));
            putTypeIndex(value.getClass());

        }

        static int index = 1;
        static void putTypeIndex(Class clazz){

            typeIndexes.put(clazz, index++);

        }


    }



    static String getBooleanName(){

        return "bool";

    }


}
