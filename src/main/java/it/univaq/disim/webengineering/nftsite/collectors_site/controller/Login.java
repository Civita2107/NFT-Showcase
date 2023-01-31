package it.univaq.disim.webengineering.nftsite.collectors_site.controller;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

public class Login {
    
    private void actionb_default(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());

        request.setAttribute("referrer", request.getParameter("referrer"));
        request.setAttribute("outline", result);
        result.activate("login.ftl", request, response);
    }
}
