package com.epam.ap.dao;

public class DaoFactoryException extends Exception {

    public DaoFactoryException(String message) {
        super(message);
    }

    public DaoFactoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
