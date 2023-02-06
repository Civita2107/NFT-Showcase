package it.univaq.disim.webengineering.nftsite.collectors_site.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp.CollectorsDataLayer;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;

public class Utility {
    
    private Utility() {

    }

    public static User getUser(HttpServletRequest request) throws DataException{
        User user;
        HttpSession s = request.getSession(false);
        if (s != null) {
            int userId = (int) s.getAttribute("userid");
            CollectorsDataLayer datalayer = 
        }
    }
}
