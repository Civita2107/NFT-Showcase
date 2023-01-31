package it.univaq.disim.webengineering.nftsite.collectors_site.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Error {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            String https_redirect_url = SecurityHelpers(request);
            request.setAttribute("https_redirect", https_redirect_url);
        }
    }
    
}
