package it.univaq.disim.webengineering.nftsite.collectors_site.controller.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import it.univaq.disim.webengineering.nftsite.collectors_site.controller.CollectorsBaseController;
import it.univaq.disim.webengineering.nftsite.collectors_site.controller.Utility;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.UserDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp.CollectorsDataLayer;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateManagerException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateResult;
import it.univaq.disim.webengineering.nftsite.framework.security.SecurityHelpers;

public class ModificaUtente extends CollectorsBaseController {
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException
     * @throws TemplateManagerException
     * @throws IOException
     * @throws DataException
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, TemplateManagerException, IOException, DataException {
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

                if (request.getMethod().equals("POST")) {
                    action_modifica(request, response);
                } else{
                    action_default(request, response);
                }
            }
        } catch (TemplateManagerException | DataException | IOException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException, DataException {

        TemplateResult result = new TemplateResult(getServletContext());

        result.activate("user/modifica.ftl", request, response); //nb

    }

    private void action_modifica(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException, DataException {

        try {
            byte[] foto = null;
            String username = null;
            String email = null;
            String password = null;
            String nuovaPassword = null;
            String value;

            ServletFileUpload upload = new ServletFileUpload();
            FileItemIterator iterator = upload.getItemIterator(request);
            while (iterator.hasNext()) {
                FileItemStream item = iterator.next();
                if (item.isFormField()) {
                    value = new BufferedReader(new InputStreamReader(item.openStream())).readLine();
                    if (item.getFieldName().equals("username")) {
                        username = value;
                    }
                    if (item.getFieldName().equals("email")) {
                        email = value;
                    }
                    if (item.getFieldName().equals("password")) {
                        password = value;
                    }
                    if (item.getFieldName().equals("nuovaPassword")) {
                        nuovaPassword = value;
                    }
                } else if (item.getFieldName().equals("foto")) {
                    if (!item.getName().equals("")) {    
                        if (!item.getContentType().equals("image/png") && !item.getContentType().equals("image/jpeg")) {
                            throw new DataException("Formato file non supportato. (formati supportati: PNG, JPG, JPEG)");
                        }
                        foto = item.openStream().readAllBytes();
                    }
                }
            }
            
            User user = Utility.getUser(request);
            CollectorsDataLayer dataLayer = (CollectorsDataLayer) request.getAttribute("datalayer");
            UserDAO userDAO = dataLayer.getUserDAO();

            if (password == null || password.isEmpty()) {
                throw new DataException("Password mancante");
            }

            if (!SecurityHelpers.encryptPassword(password).equals(user.getPassword())) {
                throw new DataException("Password errata");
            }

            if (nuovaPassword != null && nuovaPassword.equals(password)) {
                throw new DataException("La nuova password deve essere diversa da quella attuale");
            }

            if (nuovaPassword == null || nuovaPassword.isEmpty()) {
                nuovaPassword = password;
            }

            if (username == null || username.isEmpty()) {
                throw new DataException("È richiesto uno username");
            }

            if (!username.equals(user.getUsername()) && userDAO.getUserByUsername(username) != null) {
                throw new DataException("Username già in uso");
            }
            
            if (email == null || email.isEmpty()) {
                throw new DataException("È richiesta un'email");
            }

            if (!email.equals(user.getEmail()) && userDAO.getUserByEmail(email) != null) {
                throw new DataException("Email già in uso");
            }

            if (foto != null) {
                user.setFoto(foto);
            }
            
            user.setPassword(SecurityHelpers.encryptPassword(nuovaPassword));
            user.setUsername(username);
            user.setEmail(email);
            userDAO.storeUser(user);

            SecurityHelpers.createSession(request, username, user.getKey());
            response.sendRedirect("visualizza-utente?id=" + user.getKey());

        } catch (DataException e) {
            System.out.println("e1");
            System.out.println(e.getMessage());
            request.setAttribute("error", e.getMessage());
            action_default(request, response);
        }
        catch (SQLException | FileUploadException ex){
            System.out.println("e2");
            request.setAttribute("error", "Si è verificato un errore inaspettato, siamo spiacenti");
            action_default(request, response);
        }
    }

    private void action_notLogged(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException, ServletException {
        accessCheckFailed(request, response);
    }

}
