package com.epam.ap.action;

import com.epam.ap.entity.Product;
import com.epam.ap.service.ProductService;
import com.epam.ap.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowMarketAction implements Action {
    private ActionResult market = new ActionResult("market");
    public static final Logger log = LoggerFactory.getLogger(ShowMarketAction.class);
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, IOException {
        int page = 1;
        int recordsPerPage = 12;
        long noOfRecords;
        String orderValue = null;
        int order = 0;
        if (req.getParameter("orderBy") != null) {
            order = Integer.parseInt(req.getParameter("orderBy"));
        }
        log.debug("OrderValue param is {} ",req.getParameter("orderBy"));
        switch (order) {
            case 1: orderValue = "COST ASC" ; break;
            case 2: orderValue = "COST DESC"; break;
            case 3: orderValue = "TITLE ASC"; break;
            default: break;
        }

        if(req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));
        log.debug("Page param is {} parsed of {} ", page, req.getParameter("page"));
        List<Product> products;
        ProductService service = new ProductService();
        try {
            service.setOrder(orderValue);
            products = service.getAll(page, recordsPerPage);
            noOfRecords = service.getRowsCount();
        } catch (ServiceException e) {
            throw new ActionException("Cannot initialise products", e);
        }
        int pagesCount = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        if ((page > pagesCount) || (page < 1)){
            log.debug("Return null from ShowMarketAction");
            return null;}

        if (order == 1 || order == 2 || order == 3) {
            req.setAttribute("order", order);
        }
        req.setAttribute("pagesCount", pagesCount);
        req.setAttribute("currentPage", page);
        req.setAttribute("products", products);

        return market;
    }
}
