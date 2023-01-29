package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;

import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Artista;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

public interface ArtistaDAO {
    
    void storeArtista(Artista artista) throws  DataLayerException;
    
    Artista getArtista(int id) throws DataLayerException;

    List<Nft> getNfts(Artista artista) throws DataLayerException;

}
