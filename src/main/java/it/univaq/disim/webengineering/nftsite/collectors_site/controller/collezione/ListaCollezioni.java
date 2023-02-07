package it.univaq.disim.webengineering.nftsite.collectors_site.controller.collezione;

import javax.servlet.*;
import javax.servlet.http.*;

import it.univaq.disim.webengineering.nftsite.collectors_site.controller.CollectorsBaseController;
import it.univaq.disim.webengineering.nftsite.collectors_site.controller.Utility;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.CollectionDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp.CollectorsDataLayer;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.impl.CollectionImpl;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Collection;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateManagerException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateResult;
import it.univaq.disim.webengineering.nftsite.framework.security.SecurityHelpers;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListaCollezioni extends CollectorsBaseController {
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, TemplateManagerException, IOException {
        if (request.getMethod().equals("POST")) {
            try {
                action_condivise(request, response);
            } catch (DataException ex) {
                Logger.getLogger(ListaCollezioni.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                HttpSession s = SecurityHelpers.checkSession(request);
                String https_redirect_url = SecurityHelpers.checkHttps(request);
                request.setAttribute("https-redirect", https_redirect_url);
                if (s == null) {
                    action_pubbliche(request, response);
                } else {
                    User user = Utility.getUser(request);
                    if (user != null) {
                        request.setAttribute("user", user);
                    }
                    action_condivise(request, response);
                }
            } catch (TemplateManagerException | DataException | IOException ex) {
                handleError(ex, request, response);
            }
        }
    }

    private void action_pubbliche(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        String encodedRequestURL = URLEncoder.encode(completeRequestURL, StandardCharsets.UTF_8);
        request.setAttribute("referrer", encodedRequestURL);

        List<Collection> collections;
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        CollectionDAO collectionDAO = dataLayer.getCollectionDAO();
        collections = collectionDAO.getCollections();

        request.setAttribute("collections", collections);
        result.activate("collezione/lista.ftl", request, response);
    }

    private void action_condivise(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute("referrer", request.getParameter("referrer"));

        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        CollectionDAO collectionDAO = dataLayer.getCollectionDAO();

        Set<Collection> collections = new HashSet<>();
        collections.addAll(collectionDAO.getCollections());
        collections.addAll(collectionDAO.getCollections(Utility.getUser(request)));
        request.setAttribute("collections", collections);

        result.activate("collezione/lista.ftl", request, response);
    }
}
