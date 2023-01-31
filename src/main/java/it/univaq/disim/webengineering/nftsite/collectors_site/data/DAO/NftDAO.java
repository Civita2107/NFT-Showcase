package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;

import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Collection;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

public interface NftDAO {

    void storeNft(Nft nft) throws DataLayerException;

    Nft getNft(int id) throws DataLayerException;

    Collection getCollection(Collection collection) throws DataLayerException;
    
}
