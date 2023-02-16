package it.univaq.disim.webengineering.nftsite.collectors_site.data.proxy;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.UserDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.impl.UserImpl;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemProxy;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;

public class UserProxy extends UserImpl implements DataItemProxy {
    
    protected boolean modified;

    protected DataLayer dataLayer;

    public UserProxy(DataLayer dataLayer) {
        super();
        this.dataLayer = dataLayer;
        this.modified = false;
    }

    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = false;
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
        this.modified = true;
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
        this.modified = true;
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
        this.modified = true;
    }

    @Override
    public List<User> getFollower() {
        if (super.getFollower() == null) {
            try {
                List<User> followers = ((UserDAO) dataLayer.getDAO(User.class)).getFollower(this);
                followers.forEach((User follower) -> {
                    super.addFollower(follower);
                });
            } catch (DataException e) {
                Logger.getLogger(UserProxy.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return super.getFollower();
    }

    @Override
    public void addFollower(User user) {
        super.addFollower(user);
        this.modified = true;
    }

    @Override
    public List<User> getFollowing() {
        if (super.getFollowing() == null) {
            try {
                List<User> following = ((UserDAO) dataLayer.getDAO(User.class)).getFollowing(this);
                following.forEach((User follower) -> super.addFollowing(follower));
            } catch (DataException e) {
                Logger.getLogger(UserProxy.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return super.getFollowing();
    }

    @Override
    public void addFollowing(User user) {
        super.addFollower(user);
        this.modified = true;
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
