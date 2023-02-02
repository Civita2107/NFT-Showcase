package it.univaq.disim.webengineering.nftsite.collectors_site.data.model;
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
    
}
