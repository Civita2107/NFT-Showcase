package it.univaq.disim.webengineering.nftsite.collectors_site.data.model;
import java.sql.Array;
import java.util.List;

import it.univaq.disim.webengineering.nftsite.framework.data.DataItem;

public interface Wallet extends DataItem<Integer> {
    
    String getAddress();
    List<Nft> getNfts();
    void setAddress(String d);
    void setNfts(Array array);
    void setUserId(int id);
    int getUserId();
}
