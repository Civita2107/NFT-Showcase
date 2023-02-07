package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.asynchttpclient.AsyncHttpClient;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Wallet;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;

public interface WalletDAO {

    List<Wallet> searchWalletByStringsLogged(HttpSession session) throws DataException;

    Wallet searchWalletByStrings(String value) throws DataException;

    List<Wallet> getWallets() throws DataException;

    List<Wallet> getWallets(int idUser) throws DataException;

    boolean deleteWallet(Wallet wallet) throws DataException;


    void storeWallet(Wallet wallet) throws DataException;

    AsyncHttpClient getNfts(Wallet wallet) throws DataException, IOException;
}
