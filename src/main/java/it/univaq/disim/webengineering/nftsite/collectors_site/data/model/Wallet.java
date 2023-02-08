package it.univaq.disim.webengineering.nftsite.collectors_site.data.model;
import java.sql.Array;

import it.univaq.disim.webengineering.nftsite.framework.data.DataItem;

public interface Wallet extends DataItem<Integer> {
    
    String getAddress();

    Array getNfts();

    void setAddress(String d);

    void setNfts(Array nfts);

    void setUserId(int id);
    
    int getUserId();
}
