package it.univaq.disim.webengineering.nftsite.collectors_site.data.impl;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Follow;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemImpl;

public class FollowImpl extends DataItemImpl<Integer> implements Follow {
    private int id;
    private User follower;
    private User following;

    public FollowImpl(User follower,User following){
        super();
        this.follower = follower;
        this.following = following;
    }

    public FollowImpl(){
        this.follower = null;
        this.following = null;
    }


    @Override
    public int getId() {
        return this.id;
    }
    @Override
    public User getFollower() {
        return this.follower;
    }
    @Override
    public User getFollowing() {
        return this.following;
    }
    @Override
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public void setFollower(User follower) {
        this.follower = follower;
    }
    @Override
    public void setFollowing(User following) {
        this.following = following;
    }
}
