package com.epam.ap.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Action {

    ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, IOException;
}