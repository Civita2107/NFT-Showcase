package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.ArtistaDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Artista;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.framework.data.DB;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

public class ArtistaDAOimp implements ArtistaDAO {





    @Override
    public void storeArtista(Artista artista) throws DataLayerException {
         try (Connection connection = DB.getConnection()) {
             try (PreparedStatement ps = connection.prepareStatement("INSERT INTO artista (username, email) values ("+"'"+artista.getUsername()+"','"+artista.getEmail()+"'")) {
                 ps.executeUpdate();
             
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }

    }
    
    @Override
    public Artista getArtista(int id) throws DataLayerException {
        Artista artista=null;
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM artista where id="+id)) {
                try (ResultSet rset = ps.executeQuery()) {
                    if (rset.next()) {
                        artista = (Artista)rset;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return artista;
    }

    public List<Nft> getNfts(Artista artista) throws DataLayerException {
        List<Nft> nfts=null;
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM nft where artista="+artista.getId())) {
                try (ResultSet rset = ps.executeQuery()) {
                    if (rset.getRow()!=0) {
                        nfts = (List<Nft>)rset.get;//da finire
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nfts;
    }
    
}
