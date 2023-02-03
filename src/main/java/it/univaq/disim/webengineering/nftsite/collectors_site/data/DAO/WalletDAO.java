package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.asynchttpclient.AsyncHttpClient;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Wallet;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

public interface WalletDAO {

    List<Wallet> searchWalletByStringsLogged(HttpSession session) throws DataLayerException;

    Wallet searchWalletByStrings(String value) throws DataLayerException;

    List<Wallet> getWallets() throws DataLayerException;

    List<Wallet> getWallets(int idUser) throws DataLayerException;

    boolean deleteWallet(Wallet wallet) throws DataLayerException;


    void storeWallet(Wallet wallet) throws DataException, DataLayerException;

    AsyncHttpClient getNfts(Wallet wallet) throws DataException, IOException;
}
