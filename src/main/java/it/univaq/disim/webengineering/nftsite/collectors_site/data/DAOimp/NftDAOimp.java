package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.NftDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.framework.data.DAO;
import it.univaq.disim.webengineering.nftsite.framework.data.DB;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

public class NftDAOimp extends DAO implements NftDAO {


    public NftDAOimp(DataLayer d) {
        super(d);
    }

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
