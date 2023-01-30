package it.univaq.disim.webengineering.nftsite.collectors_site.data.impl;

import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemImpl;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Acquistati;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;


public class AcquistatiImpl extends DataItemImpl<Integer> implements Acquistati{

    private String nome;
    private boolean pubblica;
    private User User;
    private List<Nft> nft;

    public AcquistatiImpl(String nome, boolean pubblica, User User, List<Nft> nft) {
        this.nome = nome;
        this.pubblica = pubblica;
        this.User = User;
        this.nft = nft;
    }

    @Override
    public String getNome() {
        return nome;
    }
    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }
    @Override
    public boolean isPubblica() {
        return pubblica;
    }
    @Override
    public void setPubblica(boolean pubblica) {
        this.pubblica = pubblica;
    }
    @Override
    public User getUser() {
        return User;
    }
    @Override
    public void setUser(User User) {
        this.User = User;
    }
    @Override
    public List<Nft> getNft() {
        return nft;
    }
    @Override
    public void setNft(List<Nft> nft) {
        this.nft = nft;
    }
    
    
    

    
    
}
