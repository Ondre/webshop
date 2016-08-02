package com.epam.ap.action;

import com.epam.ap.entity.User;
import com.epam.ap.service.ServiceException;
import com.epam.ap.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowUsersAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, IOException {
        int page = 1;
        int recordsPerPage = 2;
        long noOfRecords;
        if(req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));
        UserService service = new UserService();
        List<User> users = null;
        try {
            users = service.getAll(page, recordsPerPage);
            noOfRecords = service.getCountRows();
        } catch (ServiceException e) {
            throw new ActionException("Error while accesing to userService", e);
        }
        int pagesCount = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("pagesCount", pagesCount);
        req.setAttribute("currentPage", page);
        req.setAttribute("users", users);
        return new ActionResult("allusers");
    }
}
