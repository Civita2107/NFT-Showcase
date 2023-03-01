package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.CollectionDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.impl.NftImpl;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Collection;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.proxy.CollectionProxy;
import it.univaq.disim.webengineering.nftsite.framework.data.DAO;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemProxy;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;
import it.univaq.disim.webengineering.nftsite.framework.data.OptimisticLockException;

public class CollectionDAOimpl extends DAO implements CollectionDAO {
    private PreparedStatement sCollectionByID;
    private PreparedStatement sCollections, sCollectionsPubbliche, sNftsByCollection, sCollectionsCondivise, sCollectionsByUser, sCollectionsByKeyword, sVisualizza,sNftCollection;
    private PreparedStatement iCollection, sCollectionsCondiviseByCollection, iVisualizza,uCollection, uPubblica, dCollection, dVisualizza, dVisualizzaUser;

    public CollectionDAOimpl(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();

            sCollections = connection.prepareStatement("SELECT * FROM collection where public = 1");
            //sCollectionsPubbliche = connection.prepareStatement("SELECT ID AS collectionID FROM collection where public = 1 AND user = ?");
            sCollectionsPubbliche = connection.prepareStatement("SELECT * FROM collection where public = 1 AND user = ?");
            sCollectionByID = connection.prepareStatement("SELECT * FROM collection WHERE id=?");
            sCollectionsByUser = connection.prepareStatement("SELECT * FROM collection WHERE user=?");
            sCollectionsByKeyword = connection.prepareStatement("SELECT * FROM collection WHERE public = 1 AND nome LIKE ?");

            iCollection = connection.prepareStatement("INSERT INTO collection (nome,public,user) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uCollection = connection.prepareStatement("UPDATE collection SET nome=?,public=?,user=? WHERE id=?");
            uPubblica = connection.prepareStatement("UPDATE collection SET public=?, nome=? WHERE id=?");
            dCollection = connection.prepareStatement("DELETE FROM collection WHERE id=?");

            //sNftCollection = connection.prepareStatement("Select n.id,n.token_id,n.contract_address,n.wallet_address,n.collection,n.title,n.description,n.metadata From collection as c INNER JOIN nft as n ON n.collection=c.id where c.id=?");
            sNftCollection = connection.prepareStatement("Select n.* From nft as n where n.collection=?");

        } catch (SQLException ex) {
            throw new DataException("Error initializing collectors data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {
        try {

            sCollectionByID.close();

            sCollectionsByKeyword.close();
            sCollections.close();
            sCollectionsPubbliche.close();
            sVisualizza.close();
            sCollectionsByUser.close();
            sCollectionsCondivise.close();
            sNftsByCollection.close();
            sCollectionsCondiviseByCollection.close();

            iCollection.close();
            iVisualizza.close();
            uCollection.close();
            uPubblica.close();
            dCollection.close();
            dVisualizza.close();
            dVisualizzaUser.close();

        } catch (SQLException ex) {
            throw new DataException("Error destroying collectors data layer", ex);
        }
        super.destroy();
    }

    @Override
    public Collection createCollection() {
        return new CollectionProxy(getDataLayer());
    }

    private CollectionProxy createCollection(ResultSet rs) throws DataException {
        CollectionProxy a = (CollectionProxy) createCollection();
        try {
            a.setKey(rs.getInt("id"));
            a.setNome(rs.getString("nome"));
            a.setPubblica(rs.getBoolean("public"));
            a.setUserKey((rs.getInt("user")));
        } catch (SQLException ex) {
            throw new DataException("Unable to create collection object form ResultSet", ex);
        }
        return a;
    }

    @Override
    public List<Nft> showNft(int collection_key) throws DataException {
        List<Nft> a  = new ArrayList<>();

            try {
                sNftCollection.setInt(1, collection_key);
                try (ResultSet rs = sNftCollection.executeQuery()) {
                    while (rs.next()) {
                        Nft nft = new NftImpl(
                            rs.getString("title"), 
                            rs.getString("token_id"), 
                            rs.getString("contract_address"), 
                            rs.getString("description"), 
                            rs.getString("metadata"),
                            rs.getString("wallet_address"));
                            nft.setKey(rs.getInt("id"));
                        a.add(nft);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load nft by collection", ex);
            }
        
        return a;
    }
   
//Funziona
    @Override
    public Collection getCollection(int collection_key) throws DataException {
        Collection a = null;
        if (dataLayer.getCache().has(Collection.class, collection_key)) {
            a = dataLayer.getCache().get(Collection.class, collection_key);
        } else {
            try {
                sCollectionByID.setInt(1, collection_key);
                try (ResultSet rs = sCollectionByID.executeQuery()) {
                    if (rs.next()) {
                        a = createCollection(rs);
                        dataLayer.getCache().add(Collection.class, a);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Collection by ID", ex);
            }
        }
        return a;
    }
//Funziona
    @Override
    public List<Collection> getCollections() throws DataException {
        List<Collection> result = new ArrayList<>();

        try (ResultSet rs = sCollections.executeQuery()) {
            while (rs.next()) {
                result.add(createCollection(rs));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Collections", ex);
        }
        return result;
    }
//Funziona
    @Override
    public List<Collection> getCollections(User user) throws DataException {
        List<Collection> result = new ArrayList<>();

        try {
            sCollectionsByUser.setInt(1, user.getKey());
            try (ResultSet rs = sCollectionsByUser.executeQuery()) {
                while (rs.next()) {
                    result.add(createCollection(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Collections by User", ex);
        }
        return result;
    }

//Funziona
    @Override
    public List<Collection> getCollectionsPubbliche(User user) throws DataException {
        List<Collection> result = new ArrayList<>();

        try {
            sCollectionsPubbliche.setInt(1, user.getKey());
            try (ResultSet rs = sCollectionsPubbliche.executeQuery()) {
                while (rs.next()) {
                    result.add(createCollection(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Collections pubbliche", ex);
        }
        return result;
    }

//Funziona
    @Override
    public void setPubblica(Collection collection, Boolean stato) throws DataException {
        try {
            uPubblica.setBoolean(1, stato);
            uPubblica.setInt(2, collection.getKey());
            uPubblica.setString(3, collection.getNome());
            uPubblica.executeUpdate();
        } catch (SQLException ex) {
            throw new DataException("Unable to set pubblica", ex);
        }
    }
//Funziona
    @Override
    public void storeCollection(Collection collection) throws DataException {
        try {
            if (collection.getKey() != null && collection.getKey() > 0) {

                if (collection instanceof DataItemProxy && !((DataItemProxy) collection).isModified()) {
                    return;
                }
                uCollection.setString(1, collection.getNome());
                uCollection.setBoolean(2, collection.isPubblica());
                uCollection.setInt(3, collection.getUser().getKey());

                uCollection.setInt(4, collection.getKey());

                if (uCollection.executeUpdate() == 0) {
                    throw new OptimisticLockException(collection);
                } 
            } else {
                iCollection.setString(1, collection.getNome());
                iCollection.setBoolean(2, collection.isPubblica());
                iCollection.setInt(3, collection.getUser().getKey());

                if (iCollection.executeUpdate() == 1) {
                    try (ResultSet keys = iCollection.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            collection.setKey(key);
                            dataLayer.getCache().add(Collection.class, collection);
                        }
                    }
                }

            }

            dataLayer.getCache().add(Collection.class, collection);
        } catch (SQLException ex) {
            throw new DataException("Unable to store Collection", ex);
        }
    }

   
//Funziona
    @Override
    public List<Collection> getCollectionsByKeyword(String keyword) throws DataException {
        try {
            sCollectionsByKeyword.setString(1, "%" + keyword + "%");
            try (ResultSet rs = sCollectionsByKeyword.executeQuery()) {
                List<Collection> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(createCollection(rs));
                }
                return result;
            } catch (DataException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Collections by keyword", ex);
        }
    }
//Funziona
    @Override
    public void deleteCollection(Collection collection) throws SQLException {
        try {
            dCollection.setInt(1, collection.getKey());
            dCollection.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Unable to delete Collection", ex);
        }
    }
}

