package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Wallet;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

import java.util.List;

public interface WalletDAO {

    List<Wallet> searchWalletByStringsLogged(String value, Integer numberOfElements, int idUser) throws DataLayerException;

    List<Wallet> searchWalletByStrings(String value, Integer numberOfElements) throws DataLayerException;

    List<Wallet> searchWalletByStrings(String value) throws DataLayerException;

    List<Wallet> getWallets() throws DataLayerException;

    List<Wallet> getWallets(int idUser) throws DataLayerException;

    void updateWallet(Nft nft, String[] id_nft) throws DataLayerException;

    void insertWallet(Wallet wallet, int id_user, String[] id_nft) throws DataLayerException;

    int getIdWallet() throws DataLayerException;

    boolean deleteWallet(int id) throws DataLayerException;

    Wallet getWallet(int id) throws DataLayerException;

    void storeWallet(Wallet wallet) throws DataException;
}
