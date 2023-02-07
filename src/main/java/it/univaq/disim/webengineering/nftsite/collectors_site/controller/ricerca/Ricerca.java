package it.univaq.disim.webengineering.nftsite.collectors_site.controller.ricerca;

import javax.servlet.http.*;

import it.univaq.disim.webengineering.nftsite.collectors_site.controller.CollectorsBaseController;
import it.univaq.disim.webengineering.nftsite.collectors_site.controller.Utility;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Wallet;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateManagerException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateResult;
import it.univaq.disim.webengineering.nftsite.framework.security.SecurityHelpers;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Ricerca extends CollectorsBaseController {
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = Utility.getUser(request);
            if (user != null) {
                request.setAttribute("user", user);
            }

            String https_redirect_url = SecurityHelpers.checkHttps(request);
            request.setAttribute("https-redirect", https_redirect_url);
            action_default(request, response);
        } catch (DataException ex) {
            handleError(ex, request, response);
        }
    }
    private void action_default(HttpServletRequest request, HttpServletResponse response) {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
            String encodedRequestURL = URLEncoder.encode(completeRequestURL, StandardCharsets.UTF_8);
            request.setAttribute("referrer", encodedRequestURL);

            if (request.getParameter("keyword") != null) {
                List<User> users = Utility.getUsers(request);
                List<Collection> collections = Utility.getCollections(request);
                List<Nft> nfts = Utility.getNfts(request);
                List<Wallet> wallets = Utility.getWallets(request);

                request.setAttribute("keyword", request.getParameter("keyword"));
                request.setAttribute("users", users);
                request.setAttribute("collections", collections);
                request.setAttribute("nfts", nfts);
                request.setAttribute("wallets", wallets);
                result.activate("ricerca/ricerca.ftl", request, response);
            } else {
                response.sendRedirect("home");
            }
        } catch (DataException | TemplateManagerException | IOException ex) {
            handleError(ex, request, response);
        }
    }
}

