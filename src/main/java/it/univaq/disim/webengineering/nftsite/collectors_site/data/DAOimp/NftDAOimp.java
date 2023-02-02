package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.NftDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.framework.data.DB;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

public class NftDAOimp implements NftDAO {

    @Override
    public void storeNft(Nft nft) throws DataLayerException {
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO nft (nome, artista, anno) values ("+"'"+nft.getTitle()+"','"+nft.getArtista()+"','"+nft.getAnno()+"'")) {
                ps.executeUpdate();
            } catch (SQLException ex) {
                throw new DataLayerException("ARTISTA MANCANTE", ex);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        
    }

    @Override
    public Nft getNft(int id) throws DataLayerException {
        
        Nft nft=null;
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM nft where id="+id)) {
                try (ResultSet rset = ps.executeQuery()) {
                    if (rset.next()) {
                        nft = (Nft)rset;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nft;
    }
    

    @Override
    public Artista getArtistaNft(Nft nft) throws DataLayerException {
            
        Artista artista=null;
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM nft where artista="+nft.getArtista())) {
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

    
    
}
