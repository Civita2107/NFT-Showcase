package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Wallet;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

import java.io.IOException;
import java.util.List;

import org.asynchttpclient.AsyncHttpClient;

public interface WalletDAO {

    List<Wallet> searchWalletByStringsLogged(String value, Integer numberOfElements, int idUser) throws DataLayerException;

    Wallet searchWalletByStrings(String value) throws DataLayerException;

    List<Wallet> getWallets() throws DataLayerException;

    List<Wallet> getWallets(int idUser) throws DataLayerException;

    boolean deleteWallet(Wallet wallet) throws DataLayerException;


    void storeWallet(Wallet wallet) throws DataException, DataLayerException;

    AsyncHttpClient getNfts(Wallet wallet) throws DataException, IOException;
}
