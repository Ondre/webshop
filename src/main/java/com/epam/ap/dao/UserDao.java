package com.epam.ap.dao;

import com.epam.ap.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends AbstractDao<User> {

    private static final String SELECT_QUERY = "SELECT * FROM USER";
    private static final String PAGINATION_SELECT_QUERY = "SELECT * FROM USER LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY = "UPDATE USER SET LOGIN = ?, PASSWORD = ?, EMAIL=?, ROLE=? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM USER WHERE ID = ?";
    private static final String CREATE_QUERY = "INSERT INTO USER(LOGIN,PASSWORD,EMAIL,ROLE) VALUES (?,?,?,?)";
    private static final String TABLE_NAME = "USER";

    public UserDao(Connection conn) {
        super(conn);
    }

    @Override
    protected void prepareStatementToCreate(PreparedStatement ps, User obj) throws DaoException {
        try {
            ps.setString(1, obj.getLogin());
            ps.setString(2, obj.getPassword());
            ps.setString(3, obj.getEmail());
            ps.setString(4, obj.getRole());
        } catch (SQLException e) {
            throw new DaoException("Exception while preparing create statement for user", e);
        }
    }

    @Override
    protected void prepareStatementToUpdate(PreparedStatement ps, User obj) throws DaoException {
        try {
            ps.setString(1, obj.getLogin());
            ps.setString(2, obj.getPassword());
            ps.setString(3,obj.getEmail());
            ps.setString(4,obj.getRole());
            ps.setLong(5, obj.getId());
        } catch (SQLException e) {
            throw new DaoException("Exception while preparing update statement for user", e);
        }
    }

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws DaoException {
        List<User> result = new ArrayList<>();
        try {
            while (rs.next()) {
                User temp = new User();
                temp.setId(rs.getLong("ID"));
                temp.setLogin(rs.getString("LOGIN"));
                temp.setPassword(rs.getString("PASSWORD"));
                temp.setEmail(rs.getString("EMAIL"));
                temp.setRole(rs.getString("ROLE"));
                result.add(temp);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while parsing resultSet occurs", e);
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