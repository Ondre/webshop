package com.epam.ap.service;

import com.epam.ap.entity.Order;
import org.junit.Test;


public class OrderServiceTest {
    @Test
    public void getOrder() throws Exception {
        OrderService service = new OrderService();
        Order order = service.getOrder(1);
        System.out.println("order = " + order);
    }

}