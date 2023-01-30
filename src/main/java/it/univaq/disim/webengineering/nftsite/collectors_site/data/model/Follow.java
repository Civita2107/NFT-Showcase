package it.univaq.disim.webengineering.nftsite.collectors_site.data.model;

import java.util.List;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItem;

public interface Follow extends DataItem<Integer> {
    int getId();
    User getFollower();
    User getFollowing();
    void setId(int id);
    void setFollower(User follower);
    void setFollowing(User following);
}
