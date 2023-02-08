package it.univaq.disim.webengineering.nftsite.collectors_site.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.CollectionDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.NftDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.UserDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.WalletDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp.CollectorsDataLayer;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Collection;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Wallet;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;

public class Utility {
    
    private Utility() {

    }

    /**
     * @return l'utente che ha effettuato il login
     */
    public static User getUser(HttpServletRequest request) throws DataException{
        User user;
        HttpSession s = request.getSession(false);
        if (s != null) {
            int userId = (int) s.getAttribute("userid");
            CollectorsDataLayer datalayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
            UserDAO userDAO = datalayer.getUserDAO();
            user = userDAO.getUser(userId);
            return user;
        } else {
            return null;
        }
    }

    /**
     * @return la lista degli utenti i cui dati contengono la keyword passata come parametro nella request
     */
    public static List<User> getUsers(HttpServletRequest request) throws DataException {
        List<User> users;
        String keyword = request.getParameter("keyword");
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        UserDAO userDAO = dataLayer.getUserDAO();
        users = userDAO.getUsersByKeyword(keyword);

        return users;
    }

    /**
     * @return la lista delle collezioni i cui dati contengono la keyword passata come parametro nella request
     */
    public static List<Collection> getCollections(HttpServletRequest request) throws DataException {
        List<Collection> collections;
        String keyword = request.getParameter("keyword");
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        CollectionDAO collectionDAO = dataLayer.getCollectionDAO();
        collections = collectionDAO.getCollectionsByKeyword(keyword);

        return collections;
    }

    /**
     * @return la lista dei wallets associati ad un utente
     */
    public static List<Wallet> getWallets(HttpServletRequest request) throws DataException {
        List<Wallet> wallets;
        HttpSession s = request.getSession(false);
        if (s != null) {
            int userId = (int) s.getAttribute("userid");
            CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
            WalletDAO walletDAO = dataLayer.getWalletDAO();
            wallets = walletDAO.getWallets(userId);

            return wallets;
        } else {
            return null;
        }
    }

    /**
     * @return la lista degli nfts i cui dati contengono la keyword passata come parametro nella request
     */
    public static List<Nft> getNfts(HttpServletRequest request) throws DataException {
        List<Nft> nfts;
        String keyword = request.getParameter("keyword");
        CollectorsDataLayer dataLayer = ((CollectorsDataLayer) request.getAttribute("datalayer"));
        NftDAO nftDAO = dataLayer.getNftDAO();
        nfts = nftDAO.getNftByKeyword(keyword);

        return nfts;
    }

}
