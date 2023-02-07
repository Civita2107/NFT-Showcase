package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp;

import javax.sql.DataSource;
import java.sql.SQLException;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.*;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.*;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;

public class CollectorsDataLayer extends DataLayer {

    public CollectorsDataLayer(DataSource datasource) throws SQLException {
        super(datasource);
    }

    

    @Override
    public void init() throws DataException {
        //registriamo i nostri dao
        //register our daos
        registerDAO(Collection.class, new CollectionDAOimpl(this));
        registerDAO(Comment.class, new CommentDAOimpl(this));
        registerDAO(Foto.class, new FotoDAOimpl(this));
        registerDAO(Nft.class, new NftDAOimp(this));
        registerDAO(User.class, new UserDAOimpl(this));
        registerDAO(Wallet.class, new WalletDAOimp(this));
        
    }

    //helpers

    public CollectionDAO getCollectionDAO() {
        return (CollectionDAO) getDAO(Collection.class);
    }

    public CommentDAO getCommentDAO() {
        return (CommentDAO) getDAO(Comment.class);
    }

    public FotoDAO getImmagineDAO() {
        return (FotoDAO) getDAO(Foto.class);
    }

    public NftDAO getNftDAO() {
        return (NftDAO) getDAO(Nft.class);
    }

    public UserDAO getUserDAO() {
        return (UserDAO) getDAO(User.class);
    }

    public WalletDAO getWalletDAO() {
        return (WalletDAO) getDAO(Wallet.class);
    }

}

