package it.univaq.disim.webengineering.nftsite.collectors_site.data.impl;

import java.util.List;
import java.sql.Array;


import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Wallet;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemImpl;


public class WalletImpl extends DataItemImpl<Integer> implements Wallet  {

    private String address;
    private Array nfts;
    private int userId;



    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public Array getNfts() {
        return nfts;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void setNfts(Array nfts) {
        this.nfts = nfts;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }


    
}
