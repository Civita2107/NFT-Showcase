package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;

import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Collection;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Comment;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

public interface NftDAO {


    Nft searchNft(String title,String contractAddress) throws DataLayerException;

    List<Nft> getNfts(Collection collection) throws DataException;

    List<Nft> getNfts(User user) throws DataException;

    Nft getNft(int key) throws DataException;

    Nft getNft(Comment comment) throws DataException;

    List<Nft> getNftByKeyword(String keyword) throws DataException;

    Nft getNft(User user);
    
}
