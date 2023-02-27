package it.univaq.disim.webengineering.nftsite.collectors_site.controller.commento;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.univaq.disim.webengineering.nftsite.collectors_site.controller.CollectorsBaseController;
import it.univaq.disim.webengineering.nftsite.collectors_site.controller.Utility;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.CommentDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.NftDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp.CollectorsDataLayer;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Comment;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateManagerException;
import it.univaq.disim.webengineering.nftsite.framework.result.TemplateResult;

public class VisualizzaCommenti extends CollectorsBaseController {
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
            action_default(request, response);
        } catch (IOException | TemplateManagerException | DataException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        String encodedRequestURL = URLEncoder.encode(completeRequestURL, StandardCharsets.UTF_8);
        request.setAttribute("referrer", encodedRequestURL);

        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        NftDAO nftDAO = dataLayer.getNftDAO();
        Nft nft = nftDAO.getNft(Integer.parseInt(request.getParameter("id")));

        CommentDAO commentDAO = dataLayer.getCommentDAO();
        List<Comment> comments = commentDAO.getCommentsUser(nft);
        
        if(Utility.getUser(request) != null)
            request.setAttribute("user", Utility.getUser(request).getKey());
        else
            request.setAttribute("user", 0);


        request.setAttribute("comments", comments);
        request.setAttribute("nft", nft);

        result.activate("commento/visualizzaComm.ftl", request, response);
    }
}
