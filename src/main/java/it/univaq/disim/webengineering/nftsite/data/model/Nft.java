package it.univaq.disim.webengineering.nftsite.data.model;

public interface Nft {
    
    String getNome();
    Artista getArtista();
    String getAnno();
    void setNome(String nome);
    void setArtista(Artista artista);
    void setAnno(String anno);
}
