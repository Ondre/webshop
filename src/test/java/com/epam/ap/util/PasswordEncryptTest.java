package com.epam.ap.util;

import org.junit.Assert;
import org.junit.Test;


public class PasswordEncryptTest {
    @Test
    public void test() throws Exception {
        String hash = PasswordEncrypt.hashPassword("asdasd");
        Assert.assertTrue(PasswordEncrypt.validatePassword("asdasd" , hash));
    }
}