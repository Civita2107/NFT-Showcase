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
import it.univaq.disim.webengineering.nftsite.framework.data.DAO;
import it.univaq.disim.webengineering.nftsite.framework.data.DB;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

public class NftDAOimp extends DAO implements NftDAO {

    private PreparedStatement sNftByCollection,sNftByUser,sNftByKeyword;

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


        } catch (SQLException ex) {
            throw new DataException("Error initializing collectors data layer", ex);
        }
    }

    private NftProxy createNft(ResultSet rs) throws DataException {
        NftProxy a = (NftProxy) createNft();
        try {
            a.setKey(rs.getInt("ID"));
            a.setTitolo(rs.getString("titolo"));
            a.setAnno(rs.getString("anno"));
            a.setEtichetta(rs.getString("etichetta"));
            a.setGenere(rs.getString("genere"));
            a.setFormato(rs.getString("formato"));
            a.setBarcode(rs.getString("barcode"));
            a.setCollezionistaKey(rs.getInt("IDCollezionista"));
            if (rs.getString("stato") != null && !rs.getString("stato").isBlank()) {
                a.setStato(rs.getString("stato"));
            } else {
                a.setStato(null);
            }
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

    

    //Chiedere
    @Override
    public Nft getNft(int key) throws DataException {
        // TODO Auto-generated method stub
        return null;
    }

    //Chiedere
    @Override
    public Nft getNft(Comment comment) throws DataException {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<Nft> getNftByKeyword(String keyword) {
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
    
    

   


    
    

