package it.univaq.disim.webengineering.nftsite.collectors_site.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.UserDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp.CollectorsDataLayer;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateManagerException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateResult;
import it.univaq.disim.webengineering.nftsite.framework.security.SecurityHelpers;

public class Registration extends CollectorsBaseController {
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute("referrer", request.getParameter("referrer"));
        request.setAttribute("outline_tpl", "");
        result.activate("registration.tpl", request, response);
    }

    private void action_registration(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            request.setAttribute("error", "Inserire tutti i campi obbligatori");
            action_default(request, response);
        } else {
            try {
                CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
                UserDAO userDAO = dataLayer.getUserDAO();
                User user = userDAO.createUser(); //serve un metodo per creare l'utente

                if (user != null) {
                    user.setUsername(username);
                    user.setEmail(email);
                    user.setPassword(SecurityHelpers.encryptPassword(password));

                    userDAO.storeUser(user);

                    int userId = user.getKey();
                    SecurityHelpers.createSession(request, username, userId);
                    if (request.getParameter("referrer") != null) {
                        URLDecoder.decode(request.getParameter("referrer"), StandardCharsets.UTF_8);
                    } else {
                        response.sendRedirect("home");
                    }
                } else {
                    throw new DataException("Registrazione fallita");
                }
            } catch (Exception e) {
                request.setAttribute("error", "Username o email già in uso");
                action_default(request, response);
            }
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (SecurityHelpers.checkSession(request) != null) {
                response.sendRedirect("home");
            }
            if (request.getParameter("username") != null && request.getParameter("email") != null && request.getParameter("password") != null) {
                action_registration(request, response);
            } else {
                String https_redirect_url = SecurityHelpers.checkHttps(request);
                request.setAttribute("https_redirect", https_redirect_url);
                action_default(request, response);
            }
        } catch (IOException | TemplateManagerException e) {
            handleError(e, request, response);
        }
    }
}
