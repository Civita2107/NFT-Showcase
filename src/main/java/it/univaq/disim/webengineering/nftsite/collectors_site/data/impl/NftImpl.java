package it.univaq.disim.webengineering.nftsite.collectors_site.data.impl;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Artista;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemImpl;


public class NftImpl extends DataItemImpl<Integer> implements Nft {
    
    private String nome;
    private Artista artista;
    private String anno;

    public NftImpl() {
        super();
        this.nome = "";
        this.artista = null;
        this.anno = "";
    }

    public NftImpl(String nome, Artista artista, String anno) {
        super();
        this.nome = nome;
        this.artista = artista;
        this.anno = anno;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public Artista getArtista() {
        return artista;
    }

    @Override
    public String getAnno() {
        return anno;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    @Override
    public void setAnno(String anno) {
        this.anno = anno;
    }

}
