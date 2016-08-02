package com.epam.ap.service;

import com.epam.ap.dao.*;
import com.epam.ap.entity.Order;
import com.epam.ap.entity.OrderProduct;
import com.epam.ap.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OrderService {
    public static final Logger log = LoggerFactory.getLogger(OrderService.class);

    public Order getOrder(long id) throws ServiceException {
        Order result;
        List<OrderProduct> orderProductList;

        try (OrderDao orderDao = DaoFactory.newInstance().getOrderDao()) {
            result = orderDao.getByUserId(id);
            if (null == result) return null;
        } catch (DaoFactoryException | DaoException e) {
            throw new ServiceException("Some error related to dao occurs", e);
        }
        try (OrderProductDao orderProductDao = DaoFactory.newInstance().getOrderProductDao()) {
            orderProductList = orderProductDao.getOrderProductList(result.getId());
        } catch (DaoFactoryException | DaoException e) {
            throw new ServiceException("Some error related to dao occurs", e);
        }
        try (ProductDao productDao = DaoFactory.newInstance().getProductDao()) {
            Product product;
            for (OrderProduct orderProduct : orderProductList) {
                product = productDao.get(orderProduct.getProductId());
                result.addProduct(product, orderProduct.getQuantity());
            }
        } catch (DaoFactoryException | DaoException e) {
            throw new ServiceException("Some error related to dao occurs", e);
        }
        try (UserDao userDao = DaoFactory.newInstance().getUserDao()) {
            result.setUser(userDao.get(result.getUser().getId()));
        } catch (DaoFactoryException | DaoException e) {
            throw new ServiceException("Some error related to dao occurs", e);
        }
        return result;
    }


    public void addToOrder(Order order, Product product) throws ServiceException {
        removeFromOrder(order,product);
        log.debug("Order came as {}", order);
        log.debug("Product came as {}", product);
        Order order1;
        try (OrderDao orderDao = DaoFactory.newInstance().getOrderDao()) {
            order1 = orderDao.get(order.getId());
            if (null == order1) {
                order1 = orderDao.create(order);
            }
        } catch (DaoFactoryException | DaoException e) {
            throw new ServiceException("Cannot persist order", e);
        }

        try (OrderProductDao dao = DaoFactory.newInstance().getOrderProductDao()) {
            OrderProduct op = new OrderProduct();
            op.setOrderId(order1.getId());
            op.setProductId(product.getId());
            op.setQuantity(order.getProducts().get(product));
            dao.create(op);
        } catch (DaoFactoryException | DaoException e) {
            throw new ServiceException("Cannot persist order", e);
        }
    }

    public void removeFromOrder(Order order, Product product) throws ServiceException {
        try (OrderProductDao dao = DaoFactory.newInstance().getOrderProductDao()) {
            dao.delete(order.getId(),product.getId());
        } catch (DaoFactoryException | DaoException e) {
            throw new ServiceException("Cannot persist order", e);
        }
    }
}
