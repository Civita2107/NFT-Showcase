package it.univaq.disim.webengineering.nftsite.collectors_site.data.impl;

import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Wallet;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemImpl;


public class WalletImpl extends DataItemImpl<Integer> implements Wallet  {

    private String address;
    private List<Nft> nfts;
    private int userId;



    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public List<Nft> getNfts() {
        return nfts;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void setNfts(List<Nft> nfts) {
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
