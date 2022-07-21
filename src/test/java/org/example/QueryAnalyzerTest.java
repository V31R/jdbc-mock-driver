package org.example;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;

import static org.junit.jupiter.api.Assertions.*;

@Suite
public class QueryAnalyzerTest {

    @Test
    public void findNames_SelectAll(){

        String sql = "select * from table";

        var names = QueryAnalyzer.getFieldNames(sql);

        assertEquals(0, names.size());

    }


    @Test
    public void findNames(){

        String sql = "select data from table";

        var names = QueryAnalyzer.getFieldNames(sql);

        assertEquals("data", names.get(0));

    }

    @Test
    public void findNames_WithAlias(){

        String sql = "select data as field from table";

        var names = QueryAnalyzer.getFieldNames(sql);

        assertEquals("field", names.get(0));

    }


    @Test
    public void findNames_SeveralNames(){

        String sql = "select data, field from table";

        var names = QueryAnalyzer.getFieldNames(sql);

        assertEquals("data", names.get(0));
        assertEquals("field", names.get(1));

    }

    @Test
    public void findNames_SeveralNames_WithAlias(){

        String sql = "select data, field as name from table";

        var names = QueryAnalyzer.getFieldNames(sql);

        assertEquals("data", names.get(0));
        assertEquals("name", names.get(1));

    }

}
