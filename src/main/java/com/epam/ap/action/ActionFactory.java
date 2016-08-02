package com.epam.ap.action;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {

    private Map<String, Action> actions;

    public ActionFactory() {
        actions = new HashMap<>();

        // todo: load from config
        actions.put("GET/", new ShowPageAction("home"));
        actions.put("GET/login", new ShowPageAction("login"));
        actions.put("GET/register", new ShowPageAction("register"));
        actions.put("POST/register", new RegisterAction());
        actions.put("POST/login", new LoginAction());
        actions.put("GET/logout", new LogoutAction());
        actions.put("GET/home", new ShowPageAction("home"));
        actions.put("GET/allusers", new ShowUsersAction());
        actions.put("GET/market", new ShowMarketAction());
        actions.put("GET/search", new SearchProductAction());
        actions.put("POST/search", new SearchProductAction());
        actions.put("POST/cart/add", new AddProductToCartAction());
        actions.put("POST/cart/remove", new RemoveProductFromCardAction());
        actions.put("GET/cart", new ShowCartAction());
        actions.put("GET/contacts", new ShowPageAction("contacts"));
        actions.put("GET/about", new ShowPageAction("about"));

    }

    public Action getAction(String actionName) {
        return actions.get(actionName);
    }
}