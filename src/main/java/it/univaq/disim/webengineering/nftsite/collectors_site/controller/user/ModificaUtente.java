package it.univaq.disim.webengineering.nftsite.collectors_site.controller.user;

import jdk.jshell.execution.Util;
import javax.servlet.*;
import javax.servlet.http.*;

import it.univaq.disim.webengineering.nftsite.collectors_site.controller.CollectorsBaseController;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.UserDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp.CollectorsDataLayer;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateManagerException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateResult;
import it.univaq.disim.webengineering.nftsite.framework.security.SecurityHelpers;

import java.io.IOException;
import java.util.Objects;

public class ModificaUtente extends CollectorsBaseController {
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, TemplateManagerException, IOException, DataException {
        if (request.getMethod().equals("POST")) {
            action_modifica(request, response);
        } else{
            try{
                HttpSession s = SecurityHelpers.checkSession(request);
                String https_redirect_url = SecurityHelpers.checkHttps(request);
                request.setAttribute("https-redirect", https_redirect_url);
                if(s == null){
                    action_notLogged(request,response);
                } else{
                    User user = Utility.getUser(request);
                    if(user != null){
                        request.setAttribute("user", user);
                    }
                    action_default(request, response);
                }
            } catch (TemplateManagerException | DataException | IOException ex) {
                handleError(ex, request, response);
            }
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException, DataException {

        TemplateResult result = new TemplateResult(getServletContext());

        result.activate("user/modifica.ftl", request, response); //nb

    }

    private void action_modifica(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException, DataException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String nuovaPassword = request.getParameter("nuovaPassword");

        User user = Utility.getUser(request);
        CollectorsDataLayer dataLayer = (CollectorsDataLayer) request.getAttribute("datalayer");
        UserDAO userDAO = dataLayer.getUserDAO();
        request.setAttribute("user",user);
        try {
            if (password != null && !password.isEmpty()) {

                if (SecurityHelpers.encryptPassword(password).equals(user.getPassword())) {

                    if (username != null && !username.isEmpty()) {
                        if (userDAO.getUserByUsername(username) == null) {
                            user.setUsername(username);
                            SecurityHelpers.createSession(request, username, user.getKey());
                        } else {
                            request.setAttribute("error", "Username già in uso");
                            throw new DataException("Username già in uso");
                        }
                    }
                    user.setUsername(user.getUsername());
                    if (email != null && !email.isEmpty()) {
                        if (userDAO.getUserByEmail(email) == null) {
                            user.setEmail(email);
                        } else {
                            request.setAttribute("error", "Email già in uso");
                            throw new DataException("Email già in uso");
                        }
                    }
                    user.setEmail(user.getEmail());
                    if (nuovaPassword != null && !nuovaPassword.isEmpty()) {

                        if (!nuovaPassword.equals(password)) {
                            user.setPassword(SecurityHelpers.encryptPassword(nuovaPassword));
                        } else {
                            request.setAttribute("error", "La nuova password deve essere diversa da quella attuale");
                            throw new DataException("La nuova password deve essere diversa da quella attuale");
                        }
                }
             } else {
                    request.setAttribute("error", "Password errata");
                    throw new DataException("Password errata");
                }

            } else {
                request.setAttribute("error", "Password errata");
                throw new DataException("Password errata");
            }

            userDAO.storeUser(user);
            response.sendRedirect("visualizza-user?id=" + user.getKey());

        } catch (DataException ex) {
            request.setAttribute("error", ex.getMessage());
            action_default(request, response);
        }
    }

    private void action_notLogged(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException, ServletException {
        accessCheckFailed(request, response);
    }

}
