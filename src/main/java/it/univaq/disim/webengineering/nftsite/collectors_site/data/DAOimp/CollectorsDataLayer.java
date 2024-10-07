package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp;

import java.sql.SQLException;

import javax.sql.DataSource;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.CollectionDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.CommentDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.NftDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.UserDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.WalletDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Collection;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Comment;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Wallet;
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

