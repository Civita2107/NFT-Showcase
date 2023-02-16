package it.univaq.disim.webengineering.nftsite.collectors_site.data.model;

import it.univaq.disim.webengineering.nftsite.framework.data.DataItem;

public interface Wallet extends DataItem<Integer> {
    
    String getAddress();

    void setAddress(String d);

    void setUserId(int id);
    
    int getUserId();
}
