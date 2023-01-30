package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;

import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Collection;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public interface NftDAO {

    void storeNft(Nft nft) throws DataLayerException;

    Nft getNft(int id) throws DataLayerException;

    Collection getCollection(Collection collection) throws DataLayerException;

    List<Nft> searchNftByStrings(String value, Integer numberOfElements, Integer id) throws DataLayerException;

    Nft getNftById(int id) throws DataLayerException, SQLException, NoSuchAlgorithmException;

    List<Nft> getNftByCollezione(int id, Integer numberOfElements) throws DataLayerException;

    List<Nft> getNftByCollezione(int id) throws DataLayerException;

    Nft getNftByWallet(int id, int idWallet) throws DataLayerException;

    void insertNft(Nft nft, String[] id, int idWallet) throws DataLayerException, NoSuchAlgorithmException;

    boolean deleteNft(int id) throws DataLayerException;

}
