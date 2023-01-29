package it.univaq.disim.webengineering.nftsite.collectors_site.data.impl;

import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Wallet;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemImpl;


public class WalletImpl extends DataItemImpl<Integer> implements Wallet  {

    private String username;
    private List<Nft> nfts;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public List<Nft> getNfts() {
        return nfts;
    }

    @Override
    public void setUsername(String user) {
        this.username = user;
    }

    @Override
    public void setNfts(List<Nft> nfts) {
        this.nfts = nfts;
    }
    
}
