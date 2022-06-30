package org.example;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class HttpDriver implements Driver {

    private static final Driver INSTANCE = new HttpDriver();
    private static boolean isRegistered = false;

    Pattern driverUrlPattern = Pattern.compile("^jdbc:wm://.+:.+/.*",Pattern.CASE_INSENSITIVE);

    @Override
    public Connection connect(String url, Properties info) throws SQLException{

        var connectionUrlMatcher = Pattern.compile("//.+").matcher(url);

        if(driverUrlPattern.asPredicate().test(url) && connectionUrlMatcher.find()){
            try {
                return new HttpConnection(url.substring(connectionUrlMatcher.start(), connectionUrlMatcher.end()));

            }catch (IOException  exception){//MalformedURLException too

                throw  new SQLException(exception.getMessage());

            }

        }

        return null;

    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return driverUrlPattern.asPredicate().test(url);
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        return 0;
    }

    @Override
    public int getMinorVersion() {
        return 1;
    }

    @Override
    public boolean jdbcCompliant() {
        return true;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    public static synchronized Driver load() {

        if (!isRegistered) {

            isRegistered = true;

            try {

                DriverManager.registerDriver(INSTANCE);

            } catch (SQLException sqlException) {

                sqlException.printStackTrace();

            }

        }

        return INSTANCE;

    }

    static {

        load();

    }

}
