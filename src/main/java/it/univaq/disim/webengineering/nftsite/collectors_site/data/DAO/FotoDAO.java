package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;

import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Foto;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;

public interface FotoDAO {

 
    Foto createFoto();

    Foto getFoto(String filename) throws DataException;

    Foto getFoto(int Nft_key) throws DataException;

    List<Foto> getFoto() throws DataException;

    List<Foto> getFoto(Nft Nft) throws DataException;

    void storeFoto(List<Foto> Foto) throws DataException;

    void deleteFoto(Nft Nft, Foto ioto) throws DataException;

}
