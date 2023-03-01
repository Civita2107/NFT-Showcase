package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;

import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Collection;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Comment;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Wallet;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

public interface NftDAO {


    Nft searchNft(String title,String contractAddress) throws DataLayerException, DataException;

    List<Nft> getNfts(Collection collection) throws DataException;

    List<Nft> getNfts(User user) throws DataException;

    Nft getNft(int key) throws DataException;

    Nft getNft(Comment comment) throws DataException;

    List<Nft> getNftByKeyword(String keyword) throws DataException;

    List<Nft> getNft(User user) throws DataException;

    List<Nft> getRandomNfts() throws DataException;

    public List<Nft> getRandomFolloersNfts(User user) throws DataException;
    
    void storeNft(List<Nft> nft) throws DataException;

    List<Nft> getNftsByWallet(Wallet wallet) throws DataException;

    void updateNftColl(Nft nft) throws DataException;
}
