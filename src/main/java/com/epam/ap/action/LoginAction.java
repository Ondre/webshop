package com.epam.ap.action;

import com.epam.ap.entity.User;
import com.epam.ap.service.ServiceException;
import com.epam.ap.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginAction implements Action {
    Logger log = LoggerFactory.getLogger(LoginAction.class);
    private ActionResult home = new ActionResult("home", true);
    private ActionResult loginAgain = new ActionResult("login");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String login = req.getParameter("login").toLowerCase();
        String password = req.getParameter("password");

        log.debug("Tryin log in user {} via password {}", login, password);

        UserService service = new UserService();
        User loggedUser;
        try {
            loggedUser = service.login(login, password);
        } catch (ServiceException e) {
            log.debug("Cannot log user in because of {}", e.getMessage());
            req.setAttribute("errorMessage", "Wrong credentials.");
            return loginAgain;
        }
        if (loggedUser == null) {
            log.debug("Logged user came as null. Wrong password.");
            req.setAttribute("errorMessage", "Wrong credentials.");
            return loginAgain;
        }
        req.getSession().setAttribute("user", loggedUser);
        log.debug("User {} seccessfully loged in", loggedUser);
        return home;
    }
}
