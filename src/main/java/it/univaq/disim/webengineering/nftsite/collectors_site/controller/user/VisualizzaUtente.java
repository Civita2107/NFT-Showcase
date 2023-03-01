package it.univaq.disim.webengineering.nftsite.collectors_site.controller.user;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.univaq.disim.webengineering.nftsite.collectors_site.controller.CollectorsBaseController;
import it.univaq.disim.webengineering.nftsite.collectors_site.controller.Utility;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.UserDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp.CollectorsDataLayer;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Collection;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateManagerException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateResult;

public class VisualizzaUtente extends CollectorsBaseController {
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (request.getMethod().equals("POST")) {
                action_modifica(request, response);
            } else{
                action_default(request, response);
            }
        } catch (IOException | TemplateManagerException | DataException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        String encodedRequestURL = URLEncoder.encode(completeRequestURL, StandardCharsets.UTF_8);
        request.setAttribute("referrer", encodedRequestURL);

        User user;
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        UserDAO userDAO = dataLayer.getUserDAO();
        user = userDAO.getUser((Integer.parseInt(request.getParameter("id"))));

        Set<Collection> collezioni = new HashSet<>(dataLayer.getCollectionDAO().getCollections(user));
        List<Nft> nfts = dataLayer.getNftDAO().getNfts(user);

        boolean following = false;
        User loggedUser = Utility.getUser(request);
        if (loggedUser != null && !loggedUser.getKey().equals(user.getKey())) {
            following = userDAO.isFollowing(loggedUser, user);
        }

        request.setAttribute("following", following);
        request.setAttribute("user", user);
        request.setAttribute("collezioni", collezioni);
        request.setAttribute("nfts", nfts);
        result.activate("user/visualizza.ftl", request, response);
    }
    
    private void action_modifica(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException, DataException {
        try {
            User loggedUser = Utility.getUser(request);
            if (loggedUser == null) {
                accessCheckFailed(request, response);
            }
            User user;
            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
            UserDAO userDAO = dataLayer.getUserDAO();
            user = userDAO.getUser((Integer.parseInt(request.getParameter("id"))));
            user.getFollower();
            if (request.getParameter("follow_action").equals("follow")) {
                user.addFollower(loggedUser);
            }
            else {
                user.removeFollower(loggedUser);
            }
            userDAO.storeUser(user);
            action_default(request, response);
        } catch (DataException | SQLException e) {
            throw new DataException("Errore nell'azionedi follow/unfollow.",e);
        }
    }
}
