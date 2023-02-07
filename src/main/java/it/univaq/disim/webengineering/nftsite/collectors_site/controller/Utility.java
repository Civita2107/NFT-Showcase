package it.univaq.disim.webengineering.nftsite.collectors_site.controller;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.asynchttpclient.AsyncHttpClient;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.UserDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.WalletDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp.CollectorsDataLayer;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Wallet;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;

public class Utility {
    
    private Utility() {

    }


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

    public static List<Nft> getNfts()
}
