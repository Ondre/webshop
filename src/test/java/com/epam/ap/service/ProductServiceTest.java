package com.epam.ap.service;

import com.epam.ap.entity.Product;
import org.junit.Test;

import java.util.List;


public class ProductServiceTest {
    @Test
    public void findByName() throws Exception {
        ProductService service = new ProductService();
        service.setOrder("COST DESC");
        List<Product> a = service.findByName("бахилы", 1, 5);
        System.out.println("a = " + a);
    }

}