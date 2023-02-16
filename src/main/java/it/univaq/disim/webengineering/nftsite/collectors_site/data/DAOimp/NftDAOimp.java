package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.NftDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Collection;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Comment;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.proxy.NftProxy;
import it.univaq.disim.webengineering.nftsite.framework.data.DAO;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

public class NftDAOimp extends DAO implements NftDAO {

    private PreparedStatement sNftByCollection,sNftByUser,sNftByKeyword,sNft,sNftByComment,iNft,sNftByRandom,sNftByTitleOrCA;
    
    public NftDAOimp(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();

            sNftByCollection = connection.prepareStatement("SELECT * FROM Nft INNER JOIN nft_collection ON Nft.ID = Nft_collection.ID_nft INNER JOIN Collection ON Collection.ID = nft_collection.IDcollection WHERE Collection.ID = ? ");

            sNftByUser = connection.prepareStatement("Select * FROM users AS u INNER JOIN wallet as w On u.id = w.user_id INNER JOIN nft as n On w.wallet_address = n.wallet_address WHERE u.id = ?");
            sNftByTitleOrCA = connection.prepareStatement("SELECT * FROM nft where title=? or contract_address=?");

            sNftByKeyword = connection.prepareStatement("SELECT * FROM nft WHERE title LIKE ? OR description LIKE ?");
            sNft= connection.prepareStatement("SELECT * FROM nft WHERE id=?");
            sNftByComment = connection.prepareStatement("SELECT * FROM comment as c INNER JOIN wallet as w ON c.userId = w.userId INNER JOIN nft as n ON w.Address = n.contractAddress where c.text =?");
            iNft = connection.prepareStatement("INSERT INTO nft (token_id,contract_address,wallet_address,collection,title,description,metadata) VALUES(?,?,?,?,?,?,?)",
            Statement.RETURN_GENERATED_KEYS); 
            
            sNftByRandom = connection.prepareStatement("SELECT * FROM nft ORDER BY RAND() LIMIT 20");
            

        } catch (SQLException ex) {
            throw new DataException("Error initializing collectors data layer", ex);
        }
    }
    
    public Nft createNft() {
        return new NftProxy(getDataLayer());
    }
    private Nft createNft(ResultSet rs) throws DataException {
       NftProxy a = (NftProxy) createNft();
        try {
            a.setKey(rs.getInt("token_id"));
            a.setTitle(rs.getString("title"));
            a.setContractAddress(rs.getString("contract_address"));
            a.setDescription(rs.getString("description"));
            a.setMetadata(rs.getString("metadata"));
            a.setWalletAddress(rs.getString("wallet_address"));
            
            //a.setPubblica(rs.getBoolean("pubblica"));
            //a.setVersion(rs.getLong("versione"));


        } catch (SQLException ex) {
            throw new DataException("Unable to create nft object form ResultSet", ex);
        }
        return a;
    }

    @Override
    public Nft searchNft(String title,String contractAddress) throws DataLayerException, DataException {
        
        try  {
            sNftByTitleOrCA.setString(1, title);
            sNftByTitleOrCA.setString(2, contractAddress);

            try (ResultSet rset = sNftByTitleOrCA.executeQuery()) {
                if (rset.next()) {
                    Nft nft = createNft(rset);

                    return nft;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
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
    public void storeNft(List<Nft> lnft) throws DataException {
        try (PreparedStatement ps = iNft) {
            for(Nft nft: lnft){
            ps.setInt(1, Integer.parseInt(nft.getTokenId().substring(2),16));
            ps.setString(2, nft.getContractAddress());
            ps.setString(3, nft.getWalletAddress());
            ps.setString(4, null);
            ps.setString(5, nft.getTitle());
            ps.setString(6, nft.getDescription());
            ps.setString(7, nft.getMetadata());


            if (iNft.executeUpdate() == 1) {
                try (ResultSet keys = iNft.getGeneratedKeys()) {
                    if (keys.next()) {
                        int key = keys.getInt(1);
                        nft.setKey(key);
                        dataLayer.getCache().add(Nft.class, nft);
                    }
                }
            }
        }
        } catch (SQLException ex) {
            throw new DataException("Errore", ex);
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
                        Nft nft = createNft(rs);
                        //dataLayer.getCache().add(Nft.class, d);
                        return nft;

                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Nft by ID", ex);
            }
        }
        return d;
    }

    @Override
    public Nft getNft(Comment comment) throws DataException {
        try {
            sNftByComment.setInt(1, comment.getKey());
            try (ResultSet rs = sNftByComment.executeQuery()) {
                Nft list = createNft(rs);
               return list;
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Nft from" + comment.getText(), ex);
        }
    }


    @Override
    public List<Nft> getNftByKeyword(String keyword) throws DataException {
       List<Nft> result = new ArrayList<>();
        try {
            sNftByKeyword.setString(1, "%" + keyword + "%");
            sNftByKeyword.setString(2, "%" + keyword + "%");
            try (ResultSet rs = sNftByKeyword.executeQuery()) {
                while (rs.next()) {
                    result.add(createNft(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Nft by keyword", ex);
        }

        return result;
    }

    /**
     * Returns a list of <i>at most</i> 20 Nfts randomly choosen and ordered
     *
     * @return list = list of Nfts
     * @throws DataException 
     */
     @Override
    public List<Nft> getRandomNfts() throws DataException {
        try {
            try (ResultSet rs = sNftByRandom.executeQuery()) {
                List<Nft> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(createNft(rs));
                }
                return result;
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load random Nfts", ex);
        }
    }

    @Override
    public List<Nft> getNft(User user) throws DataException {
        List<Nft> nftList = new ArrayList<>();

        try {
            sNftByUser.setInt(1, user.getKey());
            try (ResultSet rs = sNftByUser.executeQuery()) {
                while (rs.next()) {
                    Nft nft = createNft(rs);

                    //popola l'oggetto NFT con i dati del ResultSet
                    nftList.add(nft);
                }
                return nftList;
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Nft from", ex);
        }
    }

}

    
    

   


    
    

