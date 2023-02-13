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
    private boolean pubblica;
  
    public NftImpl() {
        super();
        this.title = "";
        this.tokenId = "";
        this.contractAddress = "";
        this.description = "";
        this.metadata = "";
       
    }

    
   

    public NftImpl(String tokenId, String contractAddress,String walletAddress) {
        this.tokenId = tokenId;
        this.contractAddress = contractAddress;
        this.walletAddress=walletAddress;
    }




    public NftImpl(String title, String tokenId, String contractAddress, String description, String metadata,String walletAddress) {
        super();
        this.title = title;
        this.tokenId = tokenId;
        this.contractAddress = contractAddress;
        this.description = description;
        this.metadata = metadata;
        this.walletAddress=walletAddress;
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
    @Override
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
    @Override
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }
    @Override
    public String getDescription() {
        return description;
    }
    @Override
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String getMetadata() {
        return metadata;
    }
    @Override
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
    @Override
    public String getWalletAddress() {
        return walletAddress;
    }
    @Override
    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    @Override
    public boolean isPubblica() {
        return this.pubblica;

    }

    @Override
    public void setPubblica(boolean pubblica){
        this.pubblica = pubblica;
    }





}

