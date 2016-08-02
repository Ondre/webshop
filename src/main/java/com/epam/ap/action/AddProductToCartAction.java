package com.epam.ap.action;

import com.epam.ap.entity.Order;
import com.epam.ap.entity.Product;
import com.epam.ap.entity.User;
import com.epam.ap.service.OrderService;
import com.epam.ap.service.ProductService;
import com.epam.ap.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class AddProductToCartAction implements Action {
    public static final Logger log = LoggerFactory.getLogger(AddProductToCartAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, IOException {
        ActionResult result = new ActionResult(req.getHeader("Referer").substring(27), true);

        log.debug("Action result set to {}", req.getContextPath());
        User user = (User) req.getSession().getAttribute("user");
        if (null != user) {
            Order order;
            OrderService service = new OrderService();
            try {
                 order = service.getOrder(user.getId());
            } catch (ServiceException e) {
                log.debug("Exception while getting order by UserId {}", user.getId());
                throw new ActionException("Exception while recieving order from UserId" + user.getId(), e);
            }
            if (null == order) order = new Order();
            order.setUser(user);
            String product = req.getParameter("product");
            log.debug("product from view  = {}", product);
            ProductService ps = new ProductService();
            Product product1 = null;
            try {
                product1 = ps.get(Long.parseLong(product));
            } catch (ServiceException e) {
                e.printStackTrace();
            }

            Integer integer = order.getProducts().get(product1);
            if (null == integer)
            order.setProducts(Collections.singletonMap(product1, 1));
            else order.setProducts(Collections.singletonMap(product1, ++integer));

            try {
                service.addToOrder(order, product1);
            } catch (ServiceException e) {
                log.debug("Exception while persisting order {} with product {}", order, product);
                throw new ActionException("Exception while persisting order" + order, e);
            }

            return result;
        } else {
            return new ActionResult("login");
        }
    }
}
