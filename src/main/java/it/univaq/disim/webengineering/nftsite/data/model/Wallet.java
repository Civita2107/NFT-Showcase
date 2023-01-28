package it.univaq.disim.webengineering.nftsite.data.model;

import java.util.List;

public interface Wallet {
    
    String getUsername();
    List<Nft> getNfts();
    void setUsername(String user);
    void setNfts(List<Nft> nft);
}
