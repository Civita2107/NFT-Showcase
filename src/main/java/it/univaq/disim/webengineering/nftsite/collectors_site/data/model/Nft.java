package it.univaq.disim.webengineering.nftsite.collectors_site.data.model;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItem;


public interface Nft extends DataItem<Integer> {
    
    String getTitle();

    void setTitle(String title);

    String getTokenId();

    void setTokenId(String tokenId);

    String getContractAddress(); //da qui prendiamo il wallet del creatore

    void setContractAddress(String contractAddress);

    String getDescription();

    void setDescription(String description);

    String getMetadata();

    void setMetadata(String metadata); //bytecode della foto

    String getWalletAddress(); //da qui prendiamo il wallet del possessore

    void setWalletAddress(String walletAddress);
}
