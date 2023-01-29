package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;

import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Artista;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

public interface NftDAO {

    void storeNft(Nft nft) throws DataLayerException;

    Nft getNft(int id) throws DataLayerException;

    Artista getArtistaNft(Nft nft) throws DataLayerException;
    
}
