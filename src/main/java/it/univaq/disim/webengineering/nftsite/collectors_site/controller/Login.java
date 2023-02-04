package it.univaq.disim.webengineering.nftsite.collectors_site.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp.CollectorsDataLayer;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.controller.AbstractBaseController;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateManagerException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateResult;
import it.univaq.disim.webengineering.nftsite.framework.security.SecurityHelpers;

public class Login extends AbstractBaseController{
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());

        request.setAttribute("referrer", request.getParameter("referrer"));
     //   request.setAttribute("outline", result); //bisogna vedere se va implementato
        result.activate("login.ftl", request, response);
    }

    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("user");
        String password = request.getParameter("password");

        if (username.isBlank() || password.isBlank()) {
            request.setAttribute("error", "Inserire username e/o password");
            action_default(request, response);
        } else {
            try {
                password = SecurityHelpers.encryptPassword(password);
                CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
                User user = dataLayer.getUserDAO().getCredenziali(username, password);

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

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, TemplateManagerException, IOException, DataException {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected DataLayer createDataLayer(DataSource ds) throws ServletException {
        // TODO Auto-generated method stub
        return null;
    }
}
