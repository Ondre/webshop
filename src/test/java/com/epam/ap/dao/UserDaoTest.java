package com.epam.ap.dao;

import com.epam.ap.entity.User;
import org.junit.Assert;
import org.junit.Test;


public class UserDaoTest {
    JdbcDaoFactory jdf = new JdbcDaoFactory();
    Dao<User> userDao = jdf.getUserDao();
    User user = new User();

    public UserDaoTest() throws DaoFactoryException {
        user.setLogin("Andrey");
        user.setPassword("asdasd");
    }

    @Test
    public void create() throws Exception {
        user = userDao.create(this.user);
        Assert.assertTrue(user.getId() != 0);
    }

    @Test
    public void read() throws Exception {
        userDao.get(user.getId());

    }

    @Test
    public void update() throws Exception {
        user.setLogin("Ondre");
        user.setPassword("sadsad");
        userDao.update(user);
    }

    @Test
    public void delete() throws Exception{
        userDao.delete(user.getId());
    }

    @Test
    public void CRUDTest() throws Exception {
        create();
        read();
        update();
        delete();
    }

}