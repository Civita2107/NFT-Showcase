package it.univaq.disim.webengineering.nftsite.collectors_site.data.proxy;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.UserDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.impl.CollectionImpl;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemProxy;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;

public class CollectionProxy extends CollectionImpl implements DataItemProxy {

    protected boolean modified;
    protected int userKey;
    protected DataLayer dataLayer;

    public CollectionProxy(DataLayer d) {
        super();
        this.dataLayer = d;
        this.modified = false;
        this.userKey = 0;
    }

    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }

    @Override
    public User getUser() {
        if (super.getUser() == null && userKey > 0) {
            try {
                super.setUser(((UserDAO) dataLayer.getDAO(User.class)).getUser(userKey));
            } catch (DataException e) {
                Logger.getLogger(CollectionProxy.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return super.getUser();
    }

    @Override
    public void setUser(User user) {
        super.setUser(user);
        this.userKey = user.getKey();
        this.modified = true;
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
        this.modified = true;
    }

    @Override
    public void setPubblica(boolean pubblica) {
        super.setPubblica(pubblica);
        this.modified = true;
    }

    public void setUserKey(int userKey) {
        this.userKey = userKey;
        super.setUser(null);
    }

    @Override
    public List<Nft> getNfts() {
        if (super.getNfts() == null) {
            try {
                super.setNfts(((NftDAO) dataLayer.getDAO(Nft.class)).getNfts(this));
            } catch (DataException e) {
                Logger.getLogger(UserProxy.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return super.getNfts();
    }

    @Override
    public void setNfts(List<Nft> nfts) {
        super.setNfts(nfts);
        this.modified = true;
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void setModified(boolean dirty) {
        this.modified = dirty;
    }
    
}
