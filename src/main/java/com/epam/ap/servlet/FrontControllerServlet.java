package com.epam.ap.servlet;

import com.epam.ap.action.Action;
import com.epam.ap.action.ActionException;
import com.epam.ap.action.ActionFactory;
import com.epam.ap.action.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FrontControllerServlet", urlPatterns = "/shop/*")
public class FrontControllerServlet extends HttpServlet {
    public static final Logger log = LoggerFactory.getLogger(FrontControllerServlet.class);
    private static final String JSP_PATH = "/WEB-INF/view/";
    private static final String URL_PATTERN = "/shop/";
    private ActionFactory actionFactory = new ActionFactory();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String actionName = req.getMethod() + req.getPathInfo();
        log.debug("Action name: {}", actionName);

        Action action = actionFactory.getAction(actionName);

        if (action == null) {
            log.debug("Action '{}' is not found:", actionName);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");
            return;
        }
        log.debug("{} init by key: '{}'", action.getClass().getSimpleName(), actionName);
        ActionResult result = null;

        try {
            result = action.execute(req, resp);
            if (null != result)
            log.debug("Action result view: {}. Redirect: {}", result.getView(), result.isRedirect());
        } catch (ActionException e) {
            throw new ServletException("Could not perform action", e);
        }
        if (null == result){
            log.debug("Action result '{}' caused some problem:", actionName);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");
            return;
        }

        if (result.isRedirect()) {
            String location = req.getContextPath() + URL_PATTERN + result.getView();
            log.debug("Redirect to: {}", location);

            resp.sendRedirect(location);
        } else {
            String path = JSP_PATH + result.getView() + ".jsp";
            log.debug("Forward to: {}", path);
            req.getRequestDispatcher(path).forward(req, resp);
        }
    }
}
