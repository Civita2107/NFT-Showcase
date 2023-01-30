package it.univaq.disim.webengineering.nftsite.collectors_site.data.impl;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Collection;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemImpl;

public class CollectionImpl extends DataItemImpl<Integer> implements Collection {
    private String contractAddress;
    private String walletAddress;
    private String nome;
    private int totalSupply;

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
    
}
