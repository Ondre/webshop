package com.epam.ap.service;

import com.epam.ap.entity.User;
import com.epam.ap.util.PasswordEncrypt;
import org.junit.Test;


public class UserServiceTest {
    @Test
    public void login() throws Exception {
        UserService service = new UserService();
        User login = service.login("Andrey", "asdasd");
        System.out.println(login);
    }

    @Test
    public void register() throws Exception {
        UserService service = new UserService();
        User u = new User();
        u.setLogin("lalka");
        u.setPassword(PasswordEncrypt.hashPassword("asdasd"));
        u.setEmail("asd@asd.asd");
        u.setRole("USER");
        User user = service.registerUser(u);
        System.out.println(user);
    }
}