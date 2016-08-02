package com.epam.ap.entity;

import java.util.HashMap;
import java.util.Map;

public class Order extends BaseEntity {
    private User user;
    private Map<Product, Integer> products;

    public Order() {
        products = new HashMap<>();
       for (Product product : products.keySet()) {
        product.getTitle();
           product.getCost();
           products.get(product);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public void addProduct(Product product, Integer amount){
        if (products==null) products = new HashMap<>();
        products.put(product,amount);
    }

    @Override
    public String toString() {
/*        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            System.out.println("entry = " + ((Product)entry.getKey()).toString());
            System.out.println("entry = " + entry.getValue());
        }*/
        return "Order{" +
                "user=" + user +
                ", products=" + products +
                '}';
    }
}
