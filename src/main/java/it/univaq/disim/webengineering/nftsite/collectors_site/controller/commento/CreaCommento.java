package it.univaq.disim.webengineering.nftsite.collectors_site.controller.commento;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.univaq.disim.webengineering.nftsite.collectors_site.controller.CollectorsBaseController;
import it.univaq.disim.webengineering.nftsite.collectors_site.controller.Utility;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp.CollectorsDataLayer;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.impl.CommentImpl;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Comment;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateManagerException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateResult;
import it.univaq.disim.webengineering.nftsite.framework.security.SecurityHelpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreaCommento extends CollectorsBaseController {
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataException, IOException {
        if (request.getMethod().equals("POST")) {
            action_crea(request, response);
        } else {
            try {
                HttpSession s = SecurityHelpers.checkSession(request);
                String https_redirect_url = SecurityHelpers.checkHttps(request);
                request.setAttribute("https-redirect", https_redirect_url);
                if (s == null) {
                    action_notLogged(request, response);
                } else {
                    User user = Utility.getUser(request);
                    if (user != null) {
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
        request.setAttribute("referrer", request.getParameter("referrer"));
    }

    private void action_crea(HttpServletRequest request, HttpServletResponse response) {

        try {
            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
            User user = Utility.getUser(request);
            Nft nft = Utility.getNft(request);

            String text = request.getParameter("nome");
            Comment comment = new CommentImpl(text, user, nft);
            dataLayer.getCommentDAO().storeComment(comment);
            response.sendRedirect("visualizza-commenti");
        } catch (Exception e) {
            handleError(e, request, response);
        }
    }

    private void action_notLogged(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException, ServletException {
        accessCheckFailed(request, response);
    }
}
