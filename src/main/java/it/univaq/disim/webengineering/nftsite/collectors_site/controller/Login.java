package it.univaq.disim.webengineering.nftsite.collectors_site.controller;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import it.univaq.disim.webengineering.nftsite.framework.result.TemplateManagerException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateResult;

public class Login {
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());

        request.setAttribute("referrer", request.getParameter("referrer"));
     //   request.setAttribute("outline", result);
        result.activate("login.ftl", request, response);
    }

    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("user");
        String password = request.getParameter("password");

        if (username.isBlank() && password.isBlank()) {
            request.setAttribute("error", "Inserire username e/o password");
        }
    }
}
