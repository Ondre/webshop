package com.epam.ap.action;

import com.epam.ap.entity.User;
import com.epam.ap.service.ServiceException;
import com.epam.ap.service.UserService;
import com.epam.ap.util.PasswordEncrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterAction implements Action {

    Logger log = LoggerFactory.getLogger(RegisterAction.class);

    private static final ActionResult home = new ActionResult("login",true);
    private static final ActionResult registerAgain = new ActionResult("register");

    public static final String LOGIN_SYMBOLS = "^[a-zA-Z_0-9-]{4,255}$";
    public static final String PASSWORD_SYMBOLS = "^[a-zA-Z_0-9]{3,14}$";
    public static final String EMAIL_REGEX = ".+@.+";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, IOException {
        UserService service = new UserService();

        User user;

        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String repeat = req.getParameter("repeat");

        req.setAttribute("login",login);
        req.setAttribute("email", email);

        if (!checkLogin(req, resp, service, login)) return registerAgain;
        if (!checkEmail(req, email)) return registerAgain;
        if (!checkPassword(req, password, repeat)) return registerAgain;

        user = new User();
        user.setLogin(login.toLowerCase());
        user.setPassword(PasswordEncrypt.hashPassword(password));
        user.setEmail(email);
        user.setRole("user");
        try {
            user = service.registerUser(user);
        } catch (ServiceException e) {
            log.debug("Exception while register user {} - {}",user, e);
            resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Cannot register user");
            req.setAttribute("error", "Something bad happen. Please try again later.");
            return registerAgain;
        }
        return home;
    }

    private boolean checkEmail(HttpServletRequest req, String email) {
        if (!isValid(email, EMAIL_REGEX)){
            req.setAttribute("error", "You must enter valid email.");
            return false;
        }
        return true;
    }

    private boolean checkLogin(HttpServletRequest req, HttpServletResponse resp, UserService service, String login) throws IOException {
        User user;
        if (!isValid(login,LOGIN_SYMBOLS)) {
            req.setAttribute("error", "Login must be 5 or more symbols and contain uppercase and lowercase latin letters, '_', '-', and number symbols");
            return false;
        }
        try {
            user = service.doExist(login.toLowerCase());
        } catch (ServiceException e) {
            log.debug("Cannot check if user {} exist - {} ",login, e);
            resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Cannot register user");
            req.setAttribute("error", "Something bad happen. Please try again later.");
            return false;
        }
        if (user != null) {
            log.debug("User with nickname {} already exist", login);
            req.setAttribute("error", "User with this login already exist");
            return false;
        }
        return true;
    }

    private boolean checkPassword(HttpServletRequest req, String password, String repeat) {
        if (!password.equals(repeat)) {
            req.setAttribute("error", "Passwords are different. Check them again.");
            return false;
        }
        if (!isValid(password, PASSWORD_SYMBOLS)) {
            req.setAttribute("error", "The password's first character must be a letter, it must contain at least 4 characters and no more than 15 characters and no characters other than letters, numbers and the underscore may be used.");
            return false;
        }
        return true;
    }

    private boolean isValid(String string,String pattern){
        if (string == null) return false;
        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(string);
        return matcher.matches();
    }
}
