package it.univaq.disim.webengineering.nftsite.collectors_site.data.model;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItem;


public interface Utente extends DataItem<Integer>  {

    int getId();
    
    void setId(int Id);
    
    String getUsername();
    
    void setUsername(String username);
    
    String getPassword();
    
    void setPassword(String password);

    String getEmail();
    
    void setEmail(String email);
    
}
