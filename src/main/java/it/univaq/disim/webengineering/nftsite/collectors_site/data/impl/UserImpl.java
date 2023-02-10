package it.univaq.disim.webengineering.nftsite.collectors_site.data.impl;

import java.security.DrbgParameters.Reseed;
import java.util.List;

import freemarker.core.ReturnInstruction.Return;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemImpl;

public class UserImpl extends DataItemImpl<Integer> implements User{

    private int id;
    private String username;
    private String email;
    private String password;
    private List<User> follow;
    private List<User> followers;

    public UserImpl(){}

    public UserImpl(String username, String password, String email) {
        super();
        this.username = username;
        this.email = email;
        this.password=password;
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
        return this.followers;
    }

    @Override
    public void addFollower(User follower) {
       this.followers.add(follower);
    }

    @Override
    public List<User> getFollowing() {
        return this.follow;
    }

    @Override
    public void addFollowing(User following) {
       this.follow.add(following);
    }
}
