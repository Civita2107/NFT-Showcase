package it.univaq.disim.webengineering.nftsite.collectors_site.data.model;
import java.util.List;

import it.univaq.disim.webengineering.nftsite.framework.data.DataItem;

public interface Wallet extends DataItem<Integer> {
    
    String getUsername();
    List<Nft> getNfts();
    void setUsername(String user);
    void setNfts(List<Nft> nft);
}
