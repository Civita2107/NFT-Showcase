package it.univaq.disim.webengineering.nftsite.data.impl;

import it.univaq.disim.webengineering.nftsite.data.model.Artista;
import it.univaq.disim.webengineering.nftsite.data.model.Nft;

public class NftImpl implements Nft {
    
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
