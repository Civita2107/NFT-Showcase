package it.univaq.disim.webengineering.nftsite.collectors_site.controller.collezione;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.univaq.disim.webengineering.nftsite.collectors_site.controller.CollectorsBaseController;
import it.univaq.disim.webengineering.nftsite.collectors_site.controller.Utility;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.NftDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp.CollectorsDataLayer;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.impl.CollectionImpl;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Collection;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateManagerException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateResult;
import it.univaq.disim.webengineering.nftsite.framework.security.SecurityHelpers;

public class CreaCollezione extends CollectorsBaseController {
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

    private void action_default(HttpServletRequest request, HttpServletResponse response)
            throws IOException, TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute("referrer", request.getParameter("referrer"));

        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        List<Nft> nfts = dataLayer.getNftDAO().getNfts(Utility.getUser(request));
        List<User> users = dataLayer.getUserDAO().getUsers();
        users.remove(dataLayer.getUserDAO().getUser(Utility.getUser(request).getKey()));
        request.setAttribute("nftsAll", nfts);
        request.setAttribute("users", users);

        result.activate("collezione/crea.ftl", request, response);
    }

    private void action_crea(HttpServletRequest request, HttpServletResponse response) {

        try {
            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
            User user = Utility.getUser(request);
            NftDAO nftDAO = dataLayer.getNftDAO();

            String nome = request.getParameter("nome");
            boolean pubblica;
            String pubblicaValue = request.getParameter("pubblica");
            pubblica = pubblicaValue != null;
       
            Collection collection = new CollectionImpl(nome, pubblica, user);

           int key = dataLayer.getCollectionDAO().storeCollection(collection);

            if (request.getParameterValues("nfts") != null) {
                for (String nft : request.getParameterValues("nfts")) {
                    Nft nf = nftDAO.getNft(Integer.parseInt(nft));
                    nf.setCollection(key);
                    nftDAO.updateNftColl(nf);

                }
            }

            
            response.sendRedirect("visualizza-collezione?id="+key);
        } catch (IOException | DataException e) {
            handleError(e, request, response);
        }
    }

    private void action_notLogged(HttpServletRequest request, HttpServletResponse response)
            throws IOException, TemplateManagerException, ServletException {
        accessCheckFailed(request, response);
    }
}
