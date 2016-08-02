package com.epam.ap.service;

import com.epam.ap.dao.*;
import com.epam.ap.entity.Product;

import java.util.Collections;
import java.util.List;

public class ProductService {

    private String order = null;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<Product> getAll() throws ServiceException {
        List<Product> products;
        try (ProductDao dao = DaoFactory.newInstance().getProductDao()) {
            dao.setOrder(order);
            products = dao.getAll();
        } catch (DaoFactoryException | DaoException e) {
            throw new ServiceException("Error in validation occurs", e);
        }
        return products;
    }

    public Product get(long pk) throws ServiceException {
        Product product;
        try (ProductDao dao = DaoFactory.newInstance().getProductDao()) {
            product = dao.get(pk);
        } catch (DaoFactoryException | DaoException e) {
            throw new ServiceException("Error in validation occurs", e);
        }
        return product;
    }

    public List<Product> getAll(int page, int recordsPerPage) throws ServiceException {
        List<Product> products;
        try (ProductDao dao = DaoFactory.newInstance().getProductDao()) {
            dao.setOrder(order);
            products = dao.getAll((page-1)*recordsPerPage,recordsPerPage);
        } catch (DaoFactoryException | DaoException e) {
            throw new ServiceException("Error in validation occurs", e);
        }
        return products;
    }

    public long getRowsCount() throws ServiceException {
        long count;
        try (ProductDao dao = DaoFactory.newInstance().getProductDao()) {
            count = dao.getCountRows();
        } catch (DaoFactoryException | DaoException e) {
            throw new ServiceException("Error in getting rows count for users", e);
        }
        return count;
    }

    public List<Product> findByName(String name, int page, int recordsPerPage) throws ServiceException {
        List<Product> products;
        try (Dao dao = DaoFactory.newInstance().getProductDao()) {
            dao.setOrder(order);
            products = dao.findAllByParams(Collections.singletonMap("TITLE", name), page, recordsPerPage);
        } catch (Exception e) {
            throw new ServiceException("Cannot find product by name", e );
        }
        return products;
    }


    public List<Product> findByName(String searchString) throws ServiceException {

        List<Product> products;
        try (Dao dao = DaoFactory.newInstance().getProductDao()) {
            dao.setOrder(order);
            products = dao.findAllByParams(Collections.singletonMap("TITLE", searchString));
        } catch (Exception e) {
            throw new ServiceException("Cannot find product by name", e );
        }
        return products;
    }
}
