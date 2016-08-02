package com.epam.ap.action;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActionTest {

    @Test
    public void test(){
        System.out.println(isValid("qwerty", "^[a-zA-Z]\\w{3,14}$"));
    }


    private boolean isValid(String string,String pattern){
        if (string == null) return false;
        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(string);
        return matcher.matches();
    }


}