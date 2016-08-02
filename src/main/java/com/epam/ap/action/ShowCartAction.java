package com.epam.ap.action;

import com.epam.ap.entity.Order;
import com.epam.ap.entity.Product;
import com.epam.ap.entity.User;
import com.epam.ap.service.OrderService;
import com.epam.ap.service.ServiceException;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ShowCartAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(ShowCartAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, IOException {
        ActionResult result = new ActionResult("cart");

        User user = (User) req.getSession().getAttribute("user");
        if (null == user) {return new ActionResult("login");}

        OrderService os = new OrderService();
        Order order;
        try {
            order = os.getOrder(user.getId());
        } catch (ServiceException e) {
            log.debug("Cannot get Order on user {}", user);
            throw new ActionException("Cannot get Order on user " + user, e);
        }
        if (null == order) return result;
        req.setAttribute("order", order);
        Money sum = Money.of(CurrencyUnit.USD,0d);
        Map<Product, Integer> products = order.getProducts();
        for (Product product : products.keySet()) {
            Integer valueToMultiplyBy = products.get(product);
            Money cost = product.getCost();
            Money moneyToAdd =  cost.multipliedBy(valueToMultiplyBy);
            sum = sum.plus(moneyToAdd);
        }

        req.setAttribute("sum", sum);
        return result;
    }
}
