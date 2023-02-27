package it.univaq.disim.webengineering.nftsite.collectors_site.controller.nft;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.univaq.disim.webengineering.nftsite.collectors_site.controller.CollectorsBaseController;
import it.univaq.disim.webengineering.nftsite.collectors_site.controller.Utility;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.CommentDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.NftDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.UserDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.WalletDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp.CollectorsDataLayer;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Comment;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Wallet;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateManagerException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateResult;

public class VisualizzaNft extends CollectorsBaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_default(request, response);
        } catch (IOException | TemplateManagerException | DataException e) {
            handleError(e, request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response)
            throws IOException, TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        String completeRequestURL = request.getRequestURL()
                + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        String encodedRequestURL = URLEncoder.encode(completeRequestURL, StandardCharsets.UTF_8);
        request.setAttribute("referrer", encodedRequestURL);

        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        int id = (Integer.parseInt(request.getParameter("id")));
        NftDAO nftDAO = dataLayer.getNftDAO();
        Nft nft = nftDAO.getNft(id);
        WalletDAO walletDAO = dataLayer.getWalletDAO();
        Wallet wallet = walletDAO.searchWalletByStrings(nft.getWalletAddress());
        UserDAO userDAO = dataLayer.getUserDAO();
        User user = userDAO.getUser(wallet.getUserId());
        List<Nft> nfts = walletDAO.getNftsObject(wallet);
        Collections.shuffle(nfts);
        CommentDAO commentDAO = dataLayer.getCommentDAO();
        List<Comment> user_comment = commentDAO.getCommentsUser(nft);

        try {
            if (wallet.getUserId() == Utility.getUser(request).getKey()) {
                request.setAttribute("verificato", true);
            }
        } catch (NullPointerException e) {
            //
        }
        request.setAttribute("user_comment", user_comment);
        request.setAttribute("nfts", nfts);
        request.setAttribute("user", user);
        request.setAttribute("id", id);
        request.setAttribute("nft", nft);
        result.activate("nft/visualizza.ftl", request, response);
    }

}
