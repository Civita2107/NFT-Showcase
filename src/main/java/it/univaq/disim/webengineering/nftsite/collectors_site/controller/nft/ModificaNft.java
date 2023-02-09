package it.univaq.disim.webengineering.nftsite.collectors_site.controller.nft;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.univaq.disim.webengineering.nftsite.collectors_site.controller.CollectorsBaseController;
import it.univaq.disim.webengineering.nftsite.collectors_site.controller.Utility;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.NftDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp.CollectorsDataLayer;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateManagerException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateResult;
import it.univaq.disim.webengineering.nftsite.framework.security.SecurityHelpers;

public class ModificaNft extends CollectorsBaseController {
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataException, IOException {
        if (request.getMethod().equals("POST")) {
            action_modifica(request, response);
        } else {
            try {
                HttpSession s = SecurityHelpers.checkSession(request);
                String https_redirecr_url = SecurityHelpers.checkHttps(request);
                request.setAttribute("https-redirect", https_redirecr_url);
                if (s == null) {
                    action_notLogged(request, response);
                } else {
                    User user = Utility.getUser(request);
                    if (user != null) {
                        request.setAttribute("user", user);
                    }
                    action_default(request, response);
                }
            } catch (TemplateManagerException | DataException | IOException e) {
                handleError(e, request, response);
            }
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute("referrer", request.getParameter("referrer"));
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        User user = Utility.getUser(request);

        NftDAO nftDAO = dataLayer.getNftDAO();
        Nft nft = nftDAO.getNft((Integer.parseInt(request.getParameter("id"))));

        if (nft.isPubblica()) {
            request.setAttribute("condivisa", "pubblica");
        } else {
            request.setAttribute("condivisa", "privata");
        }

        result.activate("nft/modifica.tpl", request, response);

    }

    private void action_modifica(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
            User user = Utility.getUser(request);
            NftDAO nftDAO = dataLayer.getNftDAO();
            int id = Integer.parseInt(request.getParameter("id"));
            Nft nft = nftDAO.getNft(id);

            if (request.getParameter("pubblica").equals("pubblica") && !nft.isPubblica()) {
                nft.setPubblica(true);
            }
            if (request.getParameter("pubblica").equals("privata")) {
                nft.setPubblica(false);
            }

         //   nftDAO.storeNft(nft); TODO manca metodo
            
            response.sendRedirect("visualizza-nft?id=" + nft.getKey());
        } catch (Exception e) {
            handleError(e, request, response);
        }
    }

    private void action_notLogged(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException, ServletException {
        accessCheckFailed(request, response);
    }
}
