package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.NftDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Collection;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.framework.data.DB;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

public class NftDAOimp implements NftDAO {


    @Override
    public Nft searchNft(String title,String contractAddress) throws DataLayerException {
        
        Nft nft=null;
        title=null;
        contractAddress=null;
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM nft where title="+title+" or "+"contractAddress="+contractAddress)) {
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
    
    

   


    
    
}
