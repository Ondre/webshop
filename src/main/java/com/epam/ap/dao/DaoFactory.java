package com.epam.ap.dao;

public interface DaoFactory {

    static DaoFactory newInstance() {
        return new JdbcDaoFactory();
    }

    UserDao getUserDao() throws DaoFactoryException;

    ProductDao getProductDao() throws DaoFactoryException;

    OrderDao getOrderDao() throws DaoFactoryException;

    OrderProductDao getOrderProductDao() throws DaoFactoryException;
}
