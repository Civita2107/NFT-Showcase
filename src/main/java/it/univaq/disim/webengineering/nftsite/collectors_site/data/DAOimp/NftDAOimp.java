package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.NftDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Collection;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Comment;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.proxy.NftProxy;
import it.univaq.disim.webengineering.nftsite.framework.data.DAO;
import it.univaq.disim.webengineering.nftsite.framework.data.DB;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

public class NftDAOimp extends DAO implements NftDAO {

    private PreparedStatement sNftByCollection,sNftByUser,sNftByKeyword,sNft,sNftByComment;

    public NftDAOimp(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();

            sNftByCollection = connection.prepareStatement("SELECT * FROM Nft INNER JOIN nft_collection ON Nft.ID = Nft_collection.ID_nft INNER JOIN Collection ON Collection.ID = nft_collection.IDcollection WHERE Collection.ID = ? ");

            sNftByUser = connection.prepareStatement("SELECT * FROM Nft WHERE IDUser = ? ");

            sNftByKeyword = connection.prepareStatement("SELECT ID AS NftId FROM Nft WHERE titolo LIKE ? OR anno LIKE ?"); //correggere TODO
            sNft= connection.prepareStatement("SELECT * FROM Nft WHERE Id=?");
            sNftByComment = connection.prepareStatement("SELECT * FROM comment as c INNER JOIN wallet as w ON c.userId = w.userId INNER JOIN nft as n ON w.Address = n.contractAddress where c.text =?");
            
            

        } catch (SQLException ex) {
            throw new DataException("Error initializing collectors data layer", ex);
        }
    }

    private NftProxy createNft(ResultSet rs) throws DataException {
        NftProxy a = (NftProxy) createNft(rs);
        try {
            a.setKey(rs.getInt("tokenId"));
            a.setTitle(rs.getString("titolo"));
            a.setContractAddress(rs.getString("contractAddress"));
            a.setDescription(rs.getString("description"));
            a.setMetadata(rs.getString("metadata"));
            a.setWalletAddress(rs.getString("walletAddress"));
            a.setPubblica(rs.getBoolean("pubblica"));
            a.setVersion(rs.getLong("versione"));


        } catch (SQLException ex) {
            throw new DataException("Unable to create nft object form ResultSet", ex);
        }
        return a;
    }

    @Override
    public Nft searchNft(String title,String contractAddress) throws DataLayerException {
        
        Nft nft=null;
        title=null;
        contractAddress=null;
        try (Connection connection = DB.getConnection()) {// TODO Da correggere
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

    @Override
    public List<Nft> getNfts(Collection collection) throws DataException {
        try {
            sNftByCollection.setInt(1, collection.getKey());
            try (ResultSet rs = sNftByCollection.executeQuery()) {
                List<Nft> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(createNft(rs));
                }
                return list;
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Nft from" + collection.getNome(), ex);
        }
    }


    @Override
    public List<Nft> getNfts(User user) throws DataException {
        try {
            sNftByUser.setInt(1, user.getKey());
            try (ResultSet rs = sNftByUser.executeQuery()) {
                List<Nft> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(createNft(rs));
                }
                return list;
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Nft from" + user.getUsername(), ex);
        }
    }

    

    
    @Override
    public Nft getNft(int key) throws DataException {
        Nft d = null;
        if (dataLayer.getCache().has(Nft.class, key)) {
            d = dataLayer.getCache().get(Nft.class, key);
        } else {
            try {
                sNft.setInt(1, key);
                try (ResultSet rs = sNft.executeQuery()) {
                    if (rs.next()) {
                        d = createNft(rs);
                        dataLayer.getCache().add(Nft.class, d);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Nft by ID", ex);
            }
        }
        return d;
    }

    //Chiedere
    @Override
    public List<Nft> getNft(Comment comment) throws DataException {
        try {
            sNftByComment.setInt(1, comment.getKey());
            try (ResultSet rs = sNftByComment.executeQuery()) {
                List<Nft> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(createNft(rs));
                }
                return list;
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Nft from" + comment.getText(), ex);
        }
    }


    @Override
    public List<Nft> getNftByKeyword(String keyword) throws DataException {
       List<Nft> result = new ArrayList();
        try {
            sNftByKeyword.setString(1, "%" + keyword + "%");
            sNftByKeyword.setString(2, "%" + keyword + "%");
            try (ResultSet rs = sNftByKeyword.executeQuery()) {
                while (rs.next()) {
                    result.add((Nft) getNft(rs.getInt("nftID")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Nft by keyword", ex);
        }

        return result;
    }

}

    
    

   


    
    

