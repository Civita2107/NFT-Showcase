package it.univaq.disim.webengineering.nftsite.collectors_site.data.model;
import java.util.List;

import it.univaq.disim.webengineering.nftsite.framework.data.DataItem;

public interface Wallet extends DataItem<Integer> {
    
    String getAddress();
    List<Nft> getNfts();
    void setAddress(String d);
    void setNfts(List<Nft> nft);
    void setUserId(int id);
    int getUserId();
}
