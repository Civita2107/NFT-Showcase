package it.univaq.disim.webengineering.nftsite.collectors_site.data.impl;

import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Utente;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemImpl;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Acquistati;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;


public class Acquistatiimpl extends DataItemImpl<Integer> implements Acquistati{

    private String nome;
    private boolean pubblica;
    private Utente utente;
    private List<Nft> nft;

    public Acquistatiimpl(String nome, boolean pubblica, Utente utente, List<Nft> nft) {
        this.nome = nome;
        this.pubblica = pubblica;
        this.utente = utente;
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
    public Utente getUtente() {
        return utente;
    }
    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
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
