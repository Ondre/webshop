package com.epam.ap.dao;

import com.epam.ap.entity.Product;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao extends AbstractDao<Product> {

    private static final String SELECT_QUERY = "SELECT * FROM PRODUCT";
    private static final String PAGINATION_SELECT_QUERY = "SELECT * FROM PRODUCT LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY = "UPDATE PRODUCT SET TITLE = ?, DESCRIPTION = ?, COST = ?, IMG_PATH = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM PRODUCT WHERE ID = ?";
    private static final String CREATE_QUERY = "INSERT INTO PRODUCT(TITLE, DESCRIPTION, COST, IMG_PATH) VALUES (?, ?, ?, ?)";
    private static final String TABLE_NAME = "PRODUCT";

    public ProductDao(Connection conn) {
        super(conn);
    }

    @Override
    protected void prepareStatementToCreate(PreparedStatement ps, Product obj) throws DaoException {
        try {
            ps.setString(1, obj.getTitle());
            ps.setString(2, obj.getDescription());
            ps.setBigDecimal(3, obj.getCost().getAmount());
            ps.setString(4, obj.getImgPath());
        } catch (SQLException e) {
            throw new DaoException("Exception while preparing create statement for product", e);
        }
    }

    @Override
    protected void prepareStatementToUpdate(PreparedStatement ps, Product obj) throws DaoException {
        try {
            ps.setString(1, obj.getTitle());
            ps.setString(2, obj.getDescription());
            ps.setBigDecimal(3, obj.getCost().getAmount());
            ps.setString(4, obj.getImgPath());
            ps.setLong(5, obj.getId());
        } catch (SQLException e) {
            throw new DaoException("Exception while preparing update statement for product", e);
        }
    }

    @Override
    protected List<Product> parseResultSet(ResultSet rs) throws DaoException {
        List<Product> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Product temp = new Product();
                temp.setId(rs.getLong("ID"));
                temp.setTitle(rs.getString("TITLE"));
                temp.setDescription(rs.getString("DESCRIPTION"));
                temp.setCost(Money.of(CurrencyUnit.USD, rs.getBigDecimal("COST")));
                temp.setImgPath(rs.getString("IMG_PATH"));
                result.add(temp);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while parsing resultSet occures", e);
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
        if (null != getOrder() ) {
            return "SELECT * FROM PRODUCT ORDER BY " + getOrder() + " NULLS LAST LIMIT ? OFFSET ?";
        } else
        return PAGINATION_SELECT_QUERY;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
