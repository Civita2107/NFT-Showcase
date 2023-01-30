package it.univaq.disim.webengineering.nftsite.collectors_site.data.impl;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemImpl;


public class NftImpl extends DataItemImpl<Integer> implements Nft {
    
    private String title;
    private String tokenId;
    private String contractAddress;
    private String description;
    private String metadata;
    private String walletAddress;

    public NftImpl() {
        super();
        this.title = "";
        this.tokenId = "";
        this.contractAddress = "";
        this.description = "";
        this.metadata = "";
    }

    public NftImpl(String title, String tokenId, String contractAddress, String description, String metadata) {
        super();
        this.title = title;
        this.tokenId = tokenId;
        this.contractAddress = contractAddress;
        this.description = description;
        this.metadata = metadata;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getTokenId() {
        return tokenId;
    }

    @Override
    public String getContractAddress() {
        return contractAddress;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

}
