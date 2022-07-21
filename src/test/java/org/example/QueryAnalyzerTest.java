package org.example;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Suite
public class QueryAnalyzerTest {

    @Test
    public void findNames_SelectAll() throws SQLException {

        String sql = "select * from table";

        var names = QueryAnalyzer.getFieldNames(sql);

        assertEquals(0, names.size());

    }


    @Test
    public void findNames() throws SQLException {

        String sql = "select data from table";

        var names = QueryAnalyzer.getFieldNames(sql);

        assertEquals("data", names.get(0));

    }

    @Test
    public void findNames_WithAlias() throws SQLException {

        String sql = "select data as field from table";

        var names = QueryAnalyzer.getFieldNames(sql);

        assertEquals("field", names.get(0));

    }


    @Test
    public void findNames_SeveralNames() throws SQLException {

        String sql = "select data, field from table";

        var names = QueryAnalyzer.getFieldNames(sql);

        assertEquals("data", names.get(0));
        assertEquals("field", names.get(1));

    }

    @Test
    public void findNames_SeveralNames_WithAlias() throws SQLException {

        String sql = "select data, field as name from table";

        var names = QueryAnalyzer.getFieldNames(sql);

        assertEquals("data", names.get(0));
        assertEquals("name", names.get(1));

    }

    @Test
    public void findNames_Null_NotSelectFromQuery() throws SQLException {

        String sql = "select currval('id')";

        var names = QueryAnalyzer.getFieldNames(sql);

        assertNull(names);

    }

    @Test
    public void findNames_WrongSQLSyntax_WithoutAlias(){

        String sql = "select data as from table";
        try {
            var names = QueryAnalyzer.getFieldNames(sql);
            fail();
        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }

    @Test
    public void findNames_WrongSQLSyntax_IncorrectOperator(){

        String sql = "select data os field from table";
        try {
            var names = QueryAnalyzer.getFieldNames(sql);
            fail();
        }
        catch (SQLException sqlException){

            assertNotNull(sqlException);

        }

    }

}
