package it.univaq.disim.webengineering.nftsite.collectors_site.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp.CollectorsDataLayer;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateManagerException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateResult;
import it.univaq.disim.webengineering.nftsite.framework.security.SecurityHelpers;

public class Login extends CollectorsBaseController{
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());

        request.setAttribute("referrer", request.getParameter("referrer"));
        request.setAttribute("outline", result); //bisogna vedere se va implementato
        result.activate("login.ftl", request, response);
    }

    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException {
        String username = request.getParameter("user");
        String password = request.getParameter("password");

        if (username.isBlank() || password.isBlank()) {
            request.setAttribute("error", "Inserire username e/o password");
            action_default(request, response);
        } else {
            try {
                password = SecurityHelpers.encryptPassword(password);
                CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
                User user = dataLayer.getUserDAO().identityCheck(username, password);

                if (user != null) {
                    int userId = user.getKey();
                    SecurityHelpers.createSession(request, username, userId);
                    if (request.getParameter("referrer") != null) {
                        URLDecoder.decode(request.getParameter("referrer"), StandardCharsets.UTF_8);
                        response.sendRedirect(request.getParameter("referrer"));
                    } else {
                        response.sendRedirect("home");
                    }
                } else {
                    throw new DataException("Login fallito");
                }
            } catch (DataException e) {
                request.setAttribute("error", "Username e/o password errati");
                action_default(request, response);
            }
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws DataLayerException
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (SecurityHelpers.checkSession(request) != null) {
                response.sendRedirect("home");
            }
            if (request.getParameter("username") != null && request.getParameter("password") != null) {
                action_login(request, response);
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
