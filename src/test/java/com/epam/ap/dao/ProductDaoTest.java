package com.epam.ap.dao;

import com.epam.ap.entity.Product;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class ProductDaoTest {
    @Test
    public void fillDb() throws DaoFactoryException, DaoException, IOException {
        Dao dao = DaoFactory.newInstance().getProductDao();
        URL title = ProductDaoTest.class.getClassLoader().getResource("title");
        URL price = ProductDaoTest.class.getClassLoader().getResource("price");
        BufferedReader priceReader = new BufferedReader(new FileReader(price.getPath()));
        BufferedReader titleReader = new BufferedReader(new FileReader(title.getPath()));
        for (int i = 0; i < 250; i++) {
            Product p = new Product();
            p.setCost(Money.of(CurrencyUnit.USD, Integer.valueOf(priceReader.readLine())));
            p.setTitle(titleReader.readLine().trim());
            p.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam non.");
            dao.create(p);
        }
        System.out.println("ALL DONE");
    }

}