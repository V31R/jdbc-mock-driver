package org.example;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

@Suite
public class HttpResultSetTest {

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
    public void getXXXByIndex_IfFalse_IndexOutOfRange_LessZero(){

        try {
            if (httpResultSet.next()) {

                assertTrue(httpResultSet.getBoolean(-1));

            }
        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }

    @Test
    public void getXXXByIndex_IfFalse_IndexOutOfRange_MoreThanSize(){

        try {
            if (httpResultSet.next()) {

                assertTrue(httpResultSet.getBoolean(Integer.MAX_VALUE));

            }
        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }

    @Test
    public void getBooleanByIndex_IfTrue_TrueValue() throws SQLException{

        if(httpResultSet.next()) {

            assertTrue(httpResultSet.getBoolean(0));

        }

    }

    @Test
    public void getBooleanByIndex_IfTrue_FalseValue() throws SQLException{

        if(httpResultSet.next() && httpResultSet.next()) {

            assertFalse(httpResultSet.getBoolean(0));

        }

    }

    @Test
    public void getBooleanByIndex_IfFalse_WrongFormat(){

        try {
            if (httpResultSet.next()) {

                assertTrue(httpResultSet.getBoolean(1));

            }
        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }



    static String getCsvData(){

        return "\"bool\",\"byte\",\"short\",\"int\",\"long\",\"float\",\"double\",\"big_decimal\",\"string\"" +
                "\n 1, 255, -128, 2022, 27323213, 9.75, 9.75, 9.75, \"test\"" +
                "\n 0, 0, 0, 0, 0, 0.05, 9.75, 9.75, \"test\"";


    }


}
