package com.epam.ap.dao;

import com.epam.ap.entity.BaseEntity;

import java.util.List;
import java.util.Map;

public interface Dao<T extends BaseEntity> extends AutoCloseable {

    T create(T obj) throws DaoException;

    T get(long pk) throws DaoException;

    List<T> getAll() throws DaoException;

    List<T> getAll(int start, int size) throws DaoException;

    void update(T obj) throws DaoException;

    T delete(long pk) throws DaoException;

    List<T> findAllByParams(Map<String, String> params) throws DaoException;
    List<T> findAllByParams(Map<String, String> params, int page, int pageSize) throws DaoException;

    String getOrder();
    void setOrder(String order);

    long getCountRows() throws DaoException;

}
