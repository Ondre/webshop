package com.epam.ap.dao;

import com.epam.ap.entity.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;
import java.util.Map;

public abstract class AbstractDao<T extends BaseEntity> implements Dao<T> {
    public static final Logger log = LoggerFactory.getLogger(AbstractDao.class);
    private String order = "ID";
    Connection conn;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public AbstractDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public T create(T obj) throws DaoException {
        String sql = getCreateQuery();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementToCreate(ps, obj);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) obj.setId(rs.getLong(1));
        } catch (SQLException e) {
            throw new DaoException("Exception in create method.", e);
        }
        return obj;
    }

    @Override
    public T get(long pk) throws DaoException {
        log.debug("Read from db by pk ={}", pk);
        T result = null;
        String query = getSelectQuery() + " WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, pk);
            ResultSet resultSet = ps.executeQuery();
            List<T> ts = parseResultSet(resultSet);
            if (ts.size()>0)
            result = ts.get(0);
        } catch (SQLException e) {
            throw new DaoException("Exception in get method.", e);
        }
        return result;
    }

    @Override
    public List<T> getAll() throws DaoException {
        log.debug("Read all from db");
        List<T> result;
        String query = getSelectQuery();
        if (null != order)
            query += " ORDER BY " + order + " NULLS LAST";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet resultSet = ps.executeQuery();
            result = parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception in getAll method.", e);
        }
        return result;
    }

    public List<T> getAll(int start, int size) throws DaoException {
        log.debug("Read all from db");
        List<T> result;
        String query = getPaginationSelectQuery();
        System.out.println(query);
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, size);
            ps.setInt(2, start);
            ResultSet resultSet = ps.executeQuery();
            result = parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception in getAll method.", e);
        }
        return result;
    }

    @Override
    public void update(T obj) throws DaoException {
        log.debug("Updating object : ", obj);
        String sql = getUpdateQuery();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            prepareStatementToUpdate(ps, obj);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception in update method.", e);
        }
    }

    @Override
    public T delete(long pk) throws DaoException {
        log.debug("Delete from db by pk ={}", pk);
        T deletedObject;
        String sql = getDeleteQuery();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            deletedObject = get(pk);
            ps.setLong(1, pk);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception in delete method.", e);
        }
        return deletedObject;
    }

    @Override
    public void close() throws DaoException {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new DaoException("Can't release connection", e);
        }
    }

    public List<T> findAllByParams(Map<String, String> params) throws DaoException {
        List<T> objects;
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(getFindQueryByParams(params))) {
            objects = parseResultSet(rs);
            log.debug("Get entity list by current params: {} - {}", params, objects);
            return objects;
        } catch (SQLException e) {
            throw new DaoException("Could not get object with this params", e);
        }
    }

    public List<T> findAllByParams(Map<String, String> params, int page, int pageSize) throws DaoException {
        List<T> objects;
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(getFindQueryByParams(params, page, pageSize))) {
            objects = parseResultSet(rs);
            log.debug("Query {}", getFindQueryByParams(params, page, pageSize));
            log.debug("Get entity list by current params: {} - {}", params, objects);
            return objects;
        } catch (SQLException e) {
            throw new DaoException("Could not get object with this params", e);
        }
    }

    private String getFindQueryByParams(Map<String, String> params) {
        String resultQuery = "SELECT * FROM " + getTableName() + " WHERE ";
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (params.size() == 1) {
                resultQuery += "UPPER( +" + param.getKey() + ")" + " LIKE '%" + param.getValue().toUpperCase() + "%'";
                if (null != order)
                    resultQuery += " ORDER BY " + order + " NULLS LAST";
                return resultQuery;
            } else {
                resultQuery += " AND " + param.getKey() + " = '" + param.getValue() + "'";
            }
        }

        return resultQuery;
    }
    private String getFindQueryByParams(Map<String, String> params, int page, int pageSize) {
        String resultQuery = "SELECT * FROM " + getTableName() + " WHERE ";
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (params.size() == 1) {
                resultQuery += "UPPER(" + param.getKey() + ")" + " LIKE '%" + param.getValue().toUpperCase() + "%'";
                if (null != order)
                    resultQuery += " ORDER BY " + order + " NULLS LAST";
                resultQuery += " LIMIT " + pageSize + " OFFSET " + (page-1)*pageSize;
                return resultQuery;
            } else {
                resultQuery += " AND " + param.getKey() + " = '" + param.getValue() + "'";
            }

        }

        return resultQuery;
    }

    public long getCountRows() throws DaoException {
        log.debug("Getting rows count");
        long count = 0;
        ResultSet rs;
        try {
            rs = conn.createStatement().executeQuery("select count(*) from " + getTableName());
            rs.next();
            count = rs.getLong(1);
        } catch (SQLException e) {
            throw new DaoException("Can't get rows count", e);
        }
        log.debug("Rows count - {}", count);
        return count;
    }

    protected abstract void prepareStatementToCreate(PreparedStatement ps, T obj) throws DaoException;

    protected abstract void prepareStatementToUpdate(PreparedStatement ps, T obj) throws DaoException;

    protected abstract List<T> parseResultSet(ResultSet rs) throws DaoException;

    protected abstract String getCreateQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getSelectQuery();

    protected abstract String getPaginationSelectQuery();

    protected abstract String getTableName();
}
