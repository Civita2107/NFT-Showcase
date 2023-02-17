package it.univaq.disim.webengineering.nftsite.collectors_site.data.model;
import java.util.List;

import it.univaq.disim.webengineering.nftsite.framework.data.DataItem;


public interface User extends DataItem<Integer>  {

    int getId();
    
    void setId(int Id);
    
    String getUsername();
    
    void setUsername(String username);

    String getEmail();
    
    void setEmail(String email);

    void setPassword(String password);

    String getPassword();

    List<User> getFollower();

    void addFollower(User follower);

    List<User> getFollowing();

    void addFollowing(User following);

    String getFoto();

    void setFoto(String foto);
    
}
