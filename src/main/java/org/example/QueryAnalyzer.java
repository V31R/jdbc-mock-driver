package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryAnalyzer {

    private static final  org.slf4j.Logger logger = LoggerFactory.getLogger(QueryAnalyzer.class);


    static Pattern selectPattern = Pattern.compile("^select",Pattern.CASE_INSENSITIVE);
    static Pattern fromPattern = Pattern.compile("from",Pattern.CASE_INSENSITIVE);
    static Pattern asPattern = Pattern.compile("as",Pattern.CASE_INSENSITIVE);

    static List<String> getFieldNames(String sql) throws SQLException {

        logger.info("Analyze query '{}' for field names", sql);

        List<String> result = null;

        Matcher selectMatcher = selectPattern.matcher(sql);
        Matcher fromMatcher = fromPattern.matcher(sql);

        if(selectMatcher.find() && fromMatcher.find()){

            var start = selectMatcher.end() + 1;
            var end = fromMatcher.start() - 1;

            result = new ArrayList<>();

            var splittedNames = sql.substring(start, end).split(",");

            if(splittedNames.length == 1 && splittedNames[0].trim().equals("*")){

                logger.info("Can not find names for select all in '{}'", sql);

                return result;

            }

            for(int i = 0;i<splittedNames.length;i++){

                var name = splittedNames[i].trim().split(" ");

                if(name.length == 1){

                    result.add(name[0]);

                }else if(asPattern.asPredicate().test(name[1]) && name.length == 3){

                    result.add(name[2]);

                }else{

                    throw new SQLException("Syntax error in '"+splittedNames[i]+"'");

                }

            }

            StringBuilder names = new StringBuilder();
            for (int  i =0; i < result.size();i++) {

                names.append("\n").append(i).append(" : ").append(result.get(i));

            }
            logger.info("Query '{}' has this names: {}", sql, names.toString());

        }


        return result;

    }

}
