package org.example;

import com.opencsv.exceptions.CsvException;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.URL;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.time.Duration;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpPreparedStatement implements PreparedStatement {

    private static final  org.slf4j.Logger logger = LoggerFactory.getLogger(HttpPreparedStatement.class);

    private static final String parameterRegex = "[?]{1}";
    private static final String numberRegex = "^\\d+";
    private HttpClient httpClient;
    private HttpRequest.Builder httpRequestBuilder;

    private String[] parts;
    private String[] parameters;
    private boolean queryIsReady = false;
    private HttpResultSet lastResult;

    public HttpPreparedStatement(String uri, String sql) throws IOException {

        prepareHttpSettings(uri);

        analyzeQueryForParameters(sql);

    }

    public HttpPreparedStatement(String uri) throws IOException {

        prepareHttpSettings(uri);
        parameters = new String[0];

    }

    private void prepareHttpSettings(String uri) throws IOException{

        httpClient = HttpClient.newBuilder().build();

        httpRequestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .timeout(Duration.ofMillis(HttpConnectionData.timeout))
                .header(HttpConnectionData.CONTENT_TYPE, HttpConnectionData.contentType)
                .header(HttpConnectionData.ACCEPT, HttpConnectionData.accept);

    }

    private void analyzeQueryForParameters(String sql){

        Pattern parameterPattern = Pattern.compile(parameterRegex);

        Matcher matcher = parameterPattern.matcher(sql);
        int parametersCount = 0;
        while(matcher.find()){
            parametersCount++;
        }
        parts = sql.split(parameterRegex);
        var numberPredicate = Pattern.compile(numberRegex).asPredicate();

        for(int i =0;i < parts.length;i++){

            while(numberPredicate.test(parts[i])) {

                parts[i] = parts[i].substring(1);

            }
        }

        parameters = new String[parametersCount];

    }

    private String createQuery(){

        int parametersNumber = 0;

        StringBuilder query = new StringBuilder();
        for(int i =0; i < parameters.length;i++){

            query.append(parts[i]);
            if(parameters[i] != null){

                query.append(parameters[i]);
                parametersNumber++;

            }

        }

        if(parts.length > parameters.length) {

            query.append(parts[parameters.length]);

        }

        queryIsReady = (parametersNumber == parameters.length);

        return query.toString();

    }

    public String getQuery() {

        if(parameters.length == 0){
            return parts[0];
        }

        return createQuery();

    }

    private String createQueryBeforeExecute() throws SQLException{

        String sql = createQuery();
        if(!queryIsReady){

            logger.error("Can't execute query - not all query parameters are set");
            throw new SQLException("Not all query parameters are set");

        }

        return sql;

    }

    private void createResultSetRequest(String sql, String responseBody) throws CsvException, IOException , SQLException{

        List<String> queryFieldNames = QueryAnalyzer.getFieldNames(sql);

        if(queryFieldNames != null && queryFieldNames.size() > 0){

            lastResult = new HttpResultSet(responseBody, queryFieldNames);

        }else {

            lastResult = new HttpResultSet(responseBody);

        }

    }

    private void executeRequest(String sql) throws SQLException{

        logger.info(this + " Execute query `" + sql + "'");

        try {

            HttpRequest request = httpRequestBuilder
                    .POST(HttpRequest.BodyPublishers.ofString(sql))
                    .build();

            HttpResponse<String>  response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == HttpURLConnection.HTTP_OK){

                createResultSetRequest(sql, response.body());

            }else{

                throw new SQLException("Connection to " + request.uri() + " failed. Code: " + response.statusCode());

            }

        }
        catch (IOException| CsvException|InterruptedException exception){

            throw  new SQLException(exception.getMessage());

        }

    }


    public String[] getParameters() {
        return parameters;
    }

    @Override
    public ResultSet executeQuery() throws SQLException {

        executeRequest(createQueryBeforeExecute());

        return lastResult;

    }

    @Override
    public int executeUpdate() throws SQLException {

        executeRequest(createQueryBeforeExecute());

        return lastResult.getSize();

    }

    private void checkParameterIndexBounds(int parameterIndex) throws SQLException{

        if(parameterIndex <= 0 || parameterIndex > parameters.length){

            throw new SQLException("Index out of range");

        }

    }

    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {

        checkParameterIndexBounds(parameterIndex);

    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {

        checkParameterIndexBounds(parameterIndex);

        parameters[parameterIndex - 1] = x?"1":"0";

    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException {

        checkParameterIndexBounds(parameterIndex);

        parameters[parameterIndex - 1] = String.valueOf(x);

    }

    @Override
    public void setShort(int parameterIndex, short x) throws SQLException {

        checkParameterIndexBounds(parameterIndex);

        parameters[parameterIndex - 1] = String.valueOf(x);

    }

    @Override
    public void setInt(int parameterIndex, int x) throws SQLException {

        checkParameterIndexBounds(parameterIndex);

        parameters[parameterIndex - 1] = String.valueOf(x);

    }

    @Override
    public void setLong(int parameterIndex, long x) throws SQLException {

        checkParameterIndexBounds(parameterIndex);

        parameters[parameterIndex - 1] = String.valueOf(x);

    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException {

        checkParameterIndexBounds(parameterIndex);

        parameters[parameterIndex - 1] = String.valueOf(x);

    }

    @Override
    public void setDouble(int parameterIndex, double x) throws SQLException {

        checkParameterIndexBounds(parameterIndex);

        parameters[parameterIndex - 1] = String.valueOf(x);

    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {

        checkParameterIndexBounds(parameterIndex);

        parameters[parameterIndex - 1] = String.valueOf(x);

    }

    @Override
    public void setString(int parameterIndex, String x) throws SQLException {

        checkParameterIndexBounds(parameterIndex);

        parameters[parameterIndex - 1] = "'" + x + "'";

    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {

    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException {

    }

    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException {

    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {

    }

    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {

    }

    @Override
    public void clearParameters() throws SQLException {

        Arrays.fill(parameters,null);

    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {

    }

    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {

    }

    @Override
    public boolean execute() throws SQLException {

        executeRequest(createQueryBeforeExecute());
        return lastResult != null;

    }

    @Override
    public void addBatch() throws SQLException {

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {

    }

    @Override
    public void setRef(int parameterIndex, Ref x) throws SQLException {

    }

    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException {

    }

    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException {

    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {

    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return null;
    }

    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {

    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {

    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {

    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {

    }

    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {

    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        return null;
    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {

    }

    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {

    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {

    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {

    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {

    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {

    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {

    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {

    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {

    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {

    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {

    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {

    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {

    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {

        executeRequest(sql);
        return lastResult;

    }

    @Override
    public int executeUpdate(String sql) throws SQLException {

        executeRequest(sql);

        return lastResult.getSize();

    }

    @Override
    public void close() throws SQLException {

    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        return 0;
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {

    }

    @Override
    public int getMaxRows() throws SQLException {
        return 0;
    }

    @Override
    public void setMaxRows(int max) throws SQLException {

    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {

    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return 0;
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {

    }

    @Override
    public void cancel() throws SQLException {

    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {

    }

    @Override
    public void setCursorName(String name) throws SQLException {

    }

    @Override
    public boolean execute(String sql) throws SQLException {

        executeRequest(sql);
        return  lastResult != null;

    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return lastResult;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return lastResult.getSize();
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return false;
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {

    }

    @Override
    public int getFetchDirection() throws SQLException {
        return 0;
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {

    }

    @Override
    public int getFetchSize() throws SQLException {
        return 0;
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        return 0;
    }

    @Override
    public int getResultSetType() throws SQLException {
        return 0;
    }

    @Override
    public void addBatch(String sql) throws SQLException {

    }

    @Override
    public void clearBatch() throws SQLException {

    }

    @Override
    public int[] executeBatch() throws SQLException {
        return new int[0];
    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return false;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return null;
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        return 0;
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return false;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return 0;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return false;
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {

    }

    @Override
    public boolean isPoolable() throws SQLException {
        return false;
    }

    @Override
    public void closeOnCompletion() throws SQLException {

    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
