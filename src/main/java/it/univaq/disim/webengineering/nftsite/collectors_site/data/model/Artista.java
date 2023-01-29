package it.univaq.disim.webengineering.nftsite.collectors_site.data.model;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItem;


public interface Artista extends DataItem<Integer> {
    
    String getUsername();
    String getEmail();
    void setUsername(String user);
    void setEmail(String email);
    void setId(int id);
    int getId();
}
