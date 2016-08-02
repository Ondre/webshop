package com.epam.ap.dao;

import com.epam.ap.entity.OrderProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderProductDao extends AbstractDao<OrderProduct> {
    private static final String SELECT_QUERY = "SELECT * FROM ORDER_PRODUCT";
    private static final String PAGINATION_SELECT_QUERY = "SELECT * FROM ORDER_PRODUCT LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY = "UPDATE ORDER_PRODUCT SET QUANTITY = ?, WHERE ORDER_ID = ? AND PRODUCT_ID = ?";
    private static final String DELETE_QUERY = "DELETE FROM ORDER_PRODUCT WHERE ORDER_ID = ? AND PRODUCT_ID = ?";
    private static final String CREATE_QUERY = "INSERT INTO ORDER_PRODUCT(ORDER_ID,PRODUCT_ID,QUANTITY) VALUES (?,?,?)";
    private static final String TABLE_NAME = "ORDER_PRODUCT";
    public OrderProductDao(Connection conn) {super(conn);}


    public List<OrderProduct> getOrderProductList(long orderId) throws DaoException {
        log.debug("Read from db by pk ={}", orderId);
        List<OrderProduct> result;
        String query = getSelectQuery() + " WHERE ORDER_ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, orderId);
            ResultSet resultSet = ps.executeQuery();
            result = parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception in get method.", e);
        }
        return result;
    }

    @Override
    protected void prepareStatementToCreate(PreparedStatement ps, OrderProduct obj) throws DaoException {
        try {
            ps.setLong(1, obj.getOrderId());
            ps.setLong(2, obj.getProductId());
            ps.setLong(3, obj.getQuantity());
        } catch (SQLException e) {
            throw new DaoException("Exception while preparing create statement for order", e);
        }
    }

    @Override
    protected void prepareStatementToUpdate(PreparedStatement ps, OrderProduct obj) throws DaoException {
        try {
            ps.setLong(1, obj.getQuantity());
            ps.setLong(2, obj.getOrderId());
            ps.setLong(3, obj.getProductId());
        } catch (SQLException e) {
            throw new DaoException("Exception while preparing create statement for order", e);
        }
    }

    @Override
    protected List<OrderProduct> parseResultSet(ResultSet rs) throws DaoException {
        List<OrderProduct> result = new ArrayList<>();
        try {
            while (rs.next()) {
                OrderProduct temp = new OrderProduct();
                temp.setOrderId(rs.getLong("ORDER_ID"));
                temp.setProductId(rs.getLong("PRODUCT_ID"));
                temp.setQuantity(rs.getInt("QUANTITY"));
                result.add(temp);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while parsing resultSet occurs", e);
        }
        return result;
    }

    public void delete(long orderId, long productId) throws DaoException {
        log.debug("Delete from db by pk ={} = {}", orderId, productId);
        OrderProduct deletedObject;
        String sql = getDeleteQuery();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, orderId);
            ps.setLong(2, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception in delete method.", e);
        }
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
