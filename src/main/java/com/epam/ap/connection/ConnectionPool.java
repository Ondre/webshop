package com.epam.ap.connection;

import com.epam.ap.util.PropertyLoader;
import com.epam.ap.util.PropertyLoaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class ConnectionPool {
    public static final Logger log = LoggerFactory.getLogger(ConnectionPool.class);

    private String driver = "org.h2.Driver";
    private String url = "jdbc:h2:tcp://localhost/~/webshop";
    private String username = "sa";
    private String password = "";
    private int timeout = 5000;
    private int connectionsLimit = 10;
    private int triesToEstablishConnections = 10;

    private BlockingQueue<Connection> freeConnections;
    private BlockingQueue<Connection> usedConnections;

    private ConnectionPool() throws ConnectionPoolException {
        Properties properties = null;

        try {
            properties = PropertyLoader.getProperties("database.properties");
        } catch (PropertyLoaderException e) {
            log.debug("Can't load property. Using default settings");
        }

        if (properties != null) {
            this.driver = properties.getProperty("db.driver");
            this.url = properties.getProperty("db.url");
            this.username = properties.getProperty("db.username");
            this.password = properties.getProperty("db.password");
            this.connectionsLimit = Integer.parseInt(properties.getProperty("db.connections.limit"));
            this.timeout = Integer.parseInt(properties.getProperty("db.timeout"));
        }

        for (int i = 0; i < triesToEstablishConnections; i++) {
            try {
                initConnections();
                break;
            } catch (ConnectionPoolException e) {
                try {
                    Thread.sleep(timeout);
                } catch (InterruptedException e1) {
                }
            }
        }


    }

    public static ConnectionPool getInstance() {
        return InstanceHolder.pool;
    }

    public BlockingQueue<Connection> getFreeConnections() {
        return freeConnections;
    }

    public BlockingQueue<Connection> getUsedConnections() {
        return usedConnections;
    }

    public Connection getConnection() throws ConnectionPoolException {
        if (usedConnections == null) {
            usedConnections = new ArrayBlockingQueue<>(connectionsLimit);
        }
        PooledConnection pooledConnection;
        try {
            pooledConnection = (PooledConnection) freeConnections.poll(timeout, TimeUnit.MILLISECONDS);
            if (pooledConnection == null) {
                throw new ConnectionPoolException("No free connections in pool");
            }
            usedConnections.put(pooledConnection);
            log.debug("connection used. freeCon: {} usedCon: {}", freeConnections.size(), usedConnections.size());
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Could not get connection", e);
        }
        return pooledConnection;
    }

    public void initConnections() throws ConnectionPoolException {
        if (freeConnections == null) {
            freeConnections = new ArrayBlockingQueue<>(connectionsLimit);
        }
        try {
            Class.forName(driver);
            for (int i = 0; i < connectionsLimit; i++) {
                Connection pooledConnection = getPooledConnection();
                freeConnections.put(pooledConnection);
            }
        } catch (ClassNotFoundException | InterruptedException | ConnectionPoolException e) {
            throw new ConnectionPoolException("Can't initialize connections", e);
        }
        log.debug("Connections has been initialized. Initialized connection count - {}", freeConnections.size());
    }

    private Connection getPooledConnection() throws ConnectionPoolException {
        Connection pooledConnection;
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            pooledConnection = new PooledConnection(connection);
        } catch (SQLException e) {
            throw new ConnectionPoolException("Could't get pooled connection", e);
        }
        return pooledConnection;
    }

    private static class InstanceHolder {
        static final ConnectionPool pool;

        static {
            try {
                pool = new ConnectionPool();
            } catch (ConnectionPoolException e) {
                throw new ExceptionInInitializerError(e);
            }
        }
    }

    private class PooledConnection implements Connection {
        Connection conn;

        public PooledConnection(Connection conn) {
            this.conn = conn;
        }

        @Override
        public Statement createStatement() throws SQLException {
            return conn.createStatement();
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return conn.prepareStatement(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return conn.prepareCall(sql);
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return conn.nativeSQL(sql);
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return conn.getAutoCommit();
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            conn.setAutoCommit(autoCommit);
        }

        @Override
        public void commit() throws SQLException {
            conn.commit();
        }

        @Override
        public void rollback() throws SQLException {
            conn.rollback();
        }

        @Override
        public void close() throws SQLException {
            try {
                setAutoCommit(true);
                usedConnections.remove(this);
                freeConnections.put(this);
                log.debug("Connection released. freeConections: {} usedConnections: {}", freeConnections.size(), usedConnections.size());
            } catch (SQLException | InterruptedException e) {
                throw new SQLException("Can't release current connection", e);
            }
        }

        @Override
        public boolean isClosed() throws SQLException {
            return conn.isClosed();
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return conn.getMetaData();
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return conn.isReadOnly();
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            conn.setReadOnly(readOnly);
        }

        @Override
        public String getCatalog() throws SQLException {
            return conn.getCatalog();
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            conn.setCatalog(catalog);
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return conn.getTransactionIsolation();
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            conn.setTransactionIsolation(level);
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return conn.getWarnings();
        }

        @Override
        public void clearWarnings() throws SQLException {
            conn.clearWarnings();
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return conn.createStatement(resultSetType, resultSetConcurrency);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return conn.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return conn.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return conn.getTypeMap();
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            conn.setTypeMap(map);
        }

        @Override
        public int getHoldability() throws SQLException {
            return conn.getHoldability();
        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            conn.setHoldability(holdability);
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return conn.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return conn.setSavepoint(name);
        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {
            conn.rollback(savepoint);
        }

        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            conn.releaseSavepoint(savepoint);
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return conn.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return conn.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return conn.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return conn.prepareStatement(sql, autoGeneratedKeys);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return conn.prepareStatement(sql, columnIndexes);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return conn.prepareStatement(sql, columnNames);
        }

        @Override
        public Clob createClob() throws SQLException {
            return conn.createClob();
        }

        @Override
        public Blob createBlob() throws SQLException {
            return conn.createBlob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            return conn.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return conn.createSQLXML();
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return conn.isValid(timeout);
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            conn.setClientInfo(name, value);
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return conn.getClientInfo(name);
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return conn.getClientInfo();
        }

        @Override
        public void setClientInfo(Properties properties) throws SQLClientInfoException {
            conn.setClientInfo(properties);
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return conn.createArrayOf(typeName, elements);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return conn.createStruct(typeName, attributes);
        }

        @Override
        public String getSchema() throws SQLException {
            return conn.getSchema();
        }

        @Override
        public void setSchema(String schema) throws SQLException {
            conn.setSchema(schema);
        }

        @Override
        public void abort(Executor executor) throws SQLException {
            conn.abort(executor);
        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
            conn.setNetworkTimeout(executor, milliseconds);
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return conn.getNetworkTimeout();
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return conn.unwrap(iface);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return conn.isWrapperFor(iface);
        }
    }

}
