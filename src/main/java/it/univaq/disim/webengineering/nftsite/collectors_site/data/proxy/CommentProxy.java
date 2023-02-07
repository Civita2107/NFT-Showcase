package it.univaq.disim.webengineering.nftsite.collectors_site.data.proxy;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.NftDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.UserDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.impl.CommentImpl;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemProxy;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;

public class CommentProxy extends CommentImpl implements DataItemProxy {

    protected boolean modified;
    protected int userKey = 0;
    protected DataLayer dataLayer;

    public CommentProxy(DataLayer dataLayer) {
        super();
        this.dataLayer = dataLayer;
        this.modified = false;
        this.userKey = 0;
    }

    public void setUserKey(int userKey) {
        this.userKey = userKey;
        super.setUser(null);
    }

    @Override
    public void setUser(User user) {
        super.setUser(user);
        this.userKey = user.getKey();
        this.modified = true;
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        this.modified = true;
    }

    @Override
    public void setNft(Nft nft) {
        super.setNft(nft);
        this.modified = true;
    }

    @Override
    public Nft getNft() {
        if (super.getNft() == null) {
            try {
                super.setNft(((NftDAO) dataLayer.getDAO(Nft.class)).getNft(this));
            } catch (DataException e) {
                Logger.getLogger(CommentProxy.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return super.getNft();
    }

    @Override
    public User getUser() {
        if (super.getUser() == null && userKey > 0) {
            try {
                super.setUser(((UserDAO) dataLayer.getDAO(User.class)).getUser(userKey));
            } catch (DataException e) {
                Logger.getLogger(UserProxy.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return super.getUser();
    }

    @Override
    public void setModified(boolean dirty) {
        this.modified = dirty;
    }

    @Override
    public boolean isModified() {
        return modified;
    }
    
}
