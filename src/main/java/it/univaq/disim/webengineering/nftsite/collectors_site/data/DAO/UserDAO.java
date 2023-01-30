package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;

import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

public interface UserDAO {
    
    void storeUser(User user) throws  DataLayerException;
    
    User getUser(int id) throws DataLayerException;

   // List<Nft> getNfts(User user) throws DataLayerException;

}
