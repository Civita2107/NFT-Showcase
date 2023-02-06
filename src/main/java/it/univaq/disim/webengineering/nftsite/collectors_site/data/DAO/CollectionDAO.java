package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;


import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Collection;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

public interface CollectionDAO {

        
      //  List<Collection> searchCollectionByStringsLogged(String value, Integer numberOfElements, int idUser) throws DataLayerException;

       // List<Collection> searchCollectionByStrings(String value, Integer numberOfElements) throws DataLayerException;

       // List<Collection> getCollezioniLogImp(int numberOfElements, int idUser) throws DataLayerException;

      //  List<Collection> getCollezioniImp(Integer numberOfElements) throws DataLayerException;
        
        Integer getTotalPageBySearch(String viewItem) throws DataLayerException;

        Integer getTotalPage() throws DataLayerException;

        List<Collection> searchCollectionByStrings(String value) throws DataLayerException;

        public List<Collection> getCollezioni() throws DataLayerException;

        public List<Collection> getCollezioni(String contractAddress) throws DataLayerException;

      //  public List<Collection> getCollezioniAssociate(int idUser) throws DataLayerException;


       // public void updateCollection(Collection Collection, String[] id_dischi, String[] id_accesso) throws DataLayerException;


     //   public void insertCollection(Collection Collection, int id_User, String[] id_dischi, String[] id_accesso) throws DataLayerException;

        public String getCACollection() throws DataLayerException;

       // public boolean deleteCollection(int id) throws DataLayerException;

        public Collection getCollezione(Nft nft) throws DataLayerException;

        public List<User> getCollezionisti(int id) throws DataLayerException;

      //  public List<User> getAccessoUserNotByUser(int idP, int id) throws DataLayerException;

      //  public List<User> getAccessoUserByUser(int id) throws DataLayerException;




}
