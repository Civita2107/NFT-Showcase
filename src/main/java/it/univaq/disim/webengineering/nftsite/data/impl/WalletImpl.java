package it.univaq.disim.webengineering.nftsite.data.impl;

import java.util.List;

import it.univaq.disim.webengineering.nftsite.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.data.model.Wallet;

public class WalletImpl implements Wallet {

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
