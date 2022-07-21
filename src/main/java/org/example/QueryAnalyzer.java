package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryAnalyzer {

    static Pattern selectPattern = Pattern.compile("^select",Pattern.CASE_INSENSITIVE);
    static Pattern fromPattern = Pattern.compile("from",Pattern.CASE_INSENSITIVE);
    static Pattern asPattern = Pattern.compile("as",Pattern.CASE_INSENSITIVE);

    static List<String> getFieldNames(String sql){

        List<String> result = null;

        Matcher selectMatcher = selectPattern.matcher(sql);


        if(selectMatcher.find()){

            var start = selectMatcher.end() + 1;

            String changed = sql.substring(start);

            Matcher fromMatcher = fromPattern.matcher(changed);


            var end = changed.length();

            if(fromMatcher.find()){

                end = fromMatcher.start();

            }

            result = new ArrayList<>();

            var splittedNames = changed.substring(0, end - 1).split(",");

            if(splittedNames.length == 1 && splittedNames[0].equals("*")){

                return result;

            }

            for(int i = 0;i<splittedNames.length;i++){

                var name = splittedNames[i].trim().split(" ");

                if(name.length == 1){

                    result.add(name[0]);

                }else if(asPattern.asPredicate().test(name[1]) && name.length == 3){

                    result.add(name[2]);

                }

            }

        }

        return result;

    }

}
