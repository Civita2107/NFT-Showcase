package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;


import java.sql.SQLException;
import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Collection;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

public interface CollectionDAO {


  Collection createCollection();

  Collection getCollection(int collection_key) throws DataException;


  List<Collection> getCollections() throws DataException;

  List<Collection> getCollections(User user) throws DataException;

  List<Collection> getCollectionsCondivise(User user) throws DataException;

  List<Integer> getUsersVisualizza(Collection collection) throws DataException;

  boolean getCollectionsCondivise(Collection collection) throws DataException;

  List<Collection> getCollectionsPubbliche(User user) throws DataException;

  void storeCollection(Collection collection) throws DataException;

  void storeVisualizza(Collection collection, User user) throws DataException;

  void deleteCollection(Collection collection) throws SQLException;

  void deleteVisualizza(Collection collection) throws SQLException;

  void deleteVisualizza(Collection collection, User user) throws SQLException;

  void setPubblica(Collection collection, Boolean stato) throws DataException;

  List<Collection> getCollectionsByKeyword(String keyword) throws DataException;

  void deleteNftsCollection(Collection collection, List<Nft> nfts) throws SQLException;
  
  List<Nft> showNft(int collection_key) throws DataException;
}