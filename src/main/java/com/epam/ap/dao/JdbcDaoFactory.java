package com.epam.ap.dao;

import com.epam.ap.connection.ConnectionPool;
import com.epam.ap.connection.ConnectionPoolException;

public class JdbcDaoFactory implements DaoFactory {
    private static JdbcDaoFactory instance = new JdbcDaoFactory();

    public static JdbcDaoFactory getInstance() {
        return instance;
    }

    public UserDao getUserDao() throws DaoFactoryException {
        try {
            return new UserDao(ConnectionPool.getInstance().getConnection());
        } catch (ConnectionPoolException e) {
            throw new DaoFactoryException("Can't create user dao", e);
        }
    }

    @Override
    public ProductDao getProductDao() throws DaoFactoryException {
        try {
            return new ProductDao(ConnectionPool.getInstance().getConnection());
        } catch (ConnectionPoolException e) {
            throw new DaoFactoryException("Can't create product dao", e);
        }
    }

    @Override
    public OrderDao getOrderDao() throws DaoFactoryException {
        try {
            return new OrderDao(ConnectionPool.getInstance().getConnection());
        } catch (ConnectionPoolException e) {
            throw new DaoFactoryException("Can't create order dao", e);
        }
    }

    @Override
    public OrderProductDao getOrderProductDao() throws DaoFactoryException {
        try {
            return new OrderProductDao(ConnectionPool.getInstance().getConnection());
        } catch (ConnectionPoolException e) {
            throw new DaoFactoryException("Can't create orderProduct dao", e);
        }
    }
}