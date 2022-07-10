package org.example;

import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class HttpDriver implements Driver {

    private static final Driver INSTANCE = new HttpDriver();
    private static boolean isRegistered = false;
    private static final  org.slf4j.Logger logger = LoggerFactory.getLogger(HttpDriver.class);

    Pattern driverUrlPattern = Pattern.compile("^jdbc:wm://.+:.+/.*",Pattern.CASE_INSENSITIVE);

    @Override
    public Connection connect(String url, Properties info) throws SQLException{

        var connectionUrlMatcher = Pattern.compile("//.+").matcher(url);

        if(driverUrlPattern.asPredicate().test(url) && connectionUrlMatcher.find()){

            var urlToConnection = url.substring(connectionUrlMatcher.start(), connectionUrlMatcher.end());

            var connection = new HttpConnection(urlToConnection);
            logger.debug("Driver create new connection(" + connection +
                    ") to '" + urlToConnection+"'");

            return connection;

        }

        logger.debug("Driver don't fit to '" + url +"'");

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
        return Integer.parseInt(System.getProperty("jdbc.wm.driver_major_version"));
    }

    @Override
    public int getMinorVersion() {
        return Integer.parseInt(System.getProperty("jdbc.wm.driver_minor_version"));
    }

    @Override
    public boolean jdbcCompliant() {
        return true;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return Logger.getLogger(logger.getName());
    }

    public static synchronized Driver load() {

        if (!isRegistered) {

            try {

                DriverManager.registerDriver(INSTANCE);

                isRegistered = true;

                logger.info("Driver registered in DriverManager");

            } catch (SQLException sqlException) {

                logger.error("Driver not registered in DriverManager due to"
                        + sqlException.getMessage());
                sqlException.printStackTrace();

            }

        }

        return INSTANCE;

    }

    public static synchronized void driverPropertiesLoad(){

        try {

            var propertiesFile = HttpDriver.class.getClassLoader().getResourceAsStream("driver-mock.properties");

            Properties newProperties = System.getProperties();
            newProperties.load(propertiesFile);

            System.setProperties(newProperties);

        }catch (Exception exception){

            logger.error("Driver properties don't load due to "
                    + exception.getMessage());
            System.exit(1);

        }

        logger.info("Driver properties was loaded");

    }

    static {

        load();
        driverPropertiesLoad();

    }

}
