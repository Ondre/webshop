package com.epam.ap.dao;

import com.epam.ap.entity.Order;
import com.epam.ap.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao extends AbstractDao<Order> {
    private static final String SELECT_QUERY = "SELECT * FROM \"ORDER\"";
    private static final String PAGINATION_SELECT_QUERY = "SELECT * FROM \"ORDER\" LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY = "UPDATE \"ORDER\" SET USER_ID = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM \"ORDER\" WHERE ID = ?";
    private static final String CREATE_QUERY = "INSERT INTO \"ORDER\"(USER_ID) VALUES (?)";
    private static final String TABLE_NAME = "ORDER";
    public OrderDao(Connection conn) {super(conn);}

    @Override
    protected void prepareStatementToCreate(PreparedStatement ps, Order obj) throws DaoException {
        try {
            ps.setLong(1, obj.getUser().getId());
        } catch (SQLException e) {
            throw new DaoException("Exception while preparing create statement for order", e);
        }
    }

    @Override
    protected void prepareStatementToUpdate(PreparedStatement ps, Order obj) throws DaoException {
        try {
            ps.setLong(1, obj.getUser().getId());
            ps.setLong(2,obj.getId());
        } catch (SQLException e) {
            throw new DaoException("Exception while preparing create statement for order", e);
        }
    }

    @Override
    protected List<Order> parseResultSet(ResultSet rs) throws DaoException {
        List<Order> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Order temp = new Order();
                temp.setId(rs.getLong("ID"));
                User user = new User();
                user.setId(rs.getLong("USER_ID"));
                temp.setUser(user);
                result.add(temp);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while parsing resultSet occurs", e);
        }
        return result;
    }

    public Order getByUserId(long pk) throws DaoException {
        log.debug("Read from db by pk ={}", pk);
        Order result = null;
        String query = getSelectQuery() + " WHERE USER_ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, pk);
            ResultSet resultSet = ps.executeQuery();
            List<Order> ts = parseResultSet(resultSet);
            if (ts.size()>0)
                result = ts.get(0);
        } catch (SQLException e) {
            throw new DaoException("Exception in get method.", e);
        }
        return result;
    }

    @Override
    protected String getCreateQuery() {
        return CREATE_QUERY;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE_QUERY;
    }

    @Override
    protected String getSelectQuery() {
        return SELECT_QUERY;
    }

    @Override
    protected String getPaginationSelectQuery() {
        return PAGINATION_SELECT_QUERY;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
