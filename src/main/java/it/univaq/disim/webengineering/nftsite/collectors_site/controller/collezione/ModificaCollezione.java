
package it.univaq.disim.webengineering.nftsite.collectors_site.controller.collezione;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.univaq.disim.webengineering.nftsite.collectors_site.controller.CollectorsBaseController;
import it.univaq.disim.webengineering.nftsite.collectors_site.controller.Utility;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.CollectionDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.NftDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp.CollectorsDataLayer;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Collection;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateManagerException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateResult;
import it.univaq.disim.webengineering.nftsite.framework.security.SecurityHelpers;

public class ModificaCollezione extends CollectorsBaseController {
        /**

     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException
     * @throws DataException
     * @throws IOException
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataException, IOException {
        if (request.getMethod().equals("POST")) {
            action_modifica(request, response);
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

        int id = Integer.parseInt(request.getParameter("id"));

        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        List<User> usersVisualizzatori = new ArrayList<>();

        Collection collection = dataLayer.getCollectionDAO().getCollection(id);
        List<Nft> nftsAll = dataLayer.getNftDAO().getNfts(Utility.getUser(request));
        List<Nft> nftsSelected = dataLayer.getNftDAO().getNfts(collection);
        List<User> users = dataLayer.getUserDAO().getUsers();
        users.remove(dataLayer.getUserDAO().getUser(Utility.getUser(request).getKey()));
        request.setAttribute("nftsAll", nftsAll);
        request.setAttribute("nftsSelected", nftsSelected);
        request.setAttribute("users", users);
        request.setAttribute("collection", collection);
        request.setAttribute("condivisa", "pubblica"); // rivedere questa riga come impostare se pubb o priv
        request.setAttribute("visualizzatori", usersVisualizzatori);
        result.activate("collezione/modifica.ftl", request, response);
    }

    private void action_modifica(HttpServletRequest request, HttpServletResponse response) {

        try {
            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
            CollectionDAO collectionDAO = dataLayer.getCollectionDAO();
            NftDAO nftDAO = dataLayer.getNftDAO();
            List<Nft> nfts = new ArrayList<>();
            int id = Integer.parseInt(request.getParameter("id"));

            Collection collection = collectionDAO.getCollection(id);
            collection.setNome(request.getParameter("nome"));
            collectionDAO.deleteCollection(collectionDAO.getCollection(id));

            for (String nft : request.getParameterValues("nfts")) {
                nfts.add(nftDAO.getNft(Integer.parseInt(nft)));
            }

            collection.setNfts(nfts);
            if (request.getParameter("pubblica").equals("pubblica") && !collection.isPubblica()) {
                collection.setPubblica(true);
            }
            if (request.getParameter("pubblica").equals("privata")) {
                collection.setPubblica(false);
            }
       
            collectionDAO.storeCollection(collection);

            if (request.getParameter("pubblica").equals("condivisa")) {
                collection.setPubblica(false);
                collectionDAO.setPubblica(collection, false);
                //controllo se è gia condivisa quindi query su visualizza
                
            }

            response.sendRedirect("visualizza-collezione?id=" + collection.getKey());
        } catch (IOException | DataException | SQLException e) {
            handleError(e, request, response);
        }

    } 

    private void action_notLogged(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException, ServletException {
        accessCheckFailed(request, response);
    }

}
