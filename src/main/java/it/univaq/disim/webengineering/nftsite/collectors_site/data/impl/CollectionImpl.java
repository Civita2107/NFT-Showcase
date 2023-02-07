package it.univaq.disim.webengineering.nftsite.collectors_site.data.impl;

import java.lang.reflect.Array;
import java.util.*;


import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Collection;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemImpl;

public class CollectionImpl extends DataItemImpl<Integer> implements Collection {
    private String contractAddress;
    private String walletAddress;
    private String nome;
    private int totalSupply;
    private boolean pubblica;
    private User user;
    private List<Nft> nfts;

    @Override
    public String getContractAdrress() {
        return this.contractAddress;
    }

    @Override
    public String getwalletAddress() {
        return this.walletAddress;
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public int getTotalSupply() {
        return this.totalSupply;
    }

    @Override
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    @Override
    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void setTotalSupply(int totalSupply) {
        this.totalSupply = totalSupply;
    }

    @Override
    public boolean isPubblica() {
        return this.pubblica;

    }

    @Override
    public void setPubblica(boolean pubblica){
        this.pubblica = pubblica;
    }

    @Override
    public void setUser(User user){
        this.user=user;
    }

    @Override
    public User getUser(){
        return this.user;
    }
    
    @Override
    public List<Nft> getNfts(){
        return this.nfts;
    }

    @Override
    public void setNfts(List<Nft> nfts){
        this.nfts=nfts;
    }
}
