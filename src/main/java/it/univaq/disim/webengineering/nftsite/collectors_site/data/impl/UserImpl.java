package it.univaq.disim.webengineering.nftsite.collectors_site.data.impl;

import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemImpl;

public class UserImpl extends DataItemImpl<Integer> implements User{

    private int id;
    private String username;
    private String email;
    private String password;

    public UserImpl(){}

    public UserImpl(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public List<User> getFollower() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addFollower(User follower) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<User> getFollowing() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addFollowing(User following) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
