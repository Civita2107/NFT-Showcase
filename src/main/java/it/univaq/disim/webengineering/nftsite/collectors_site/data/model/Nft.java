package it.univaq.disim.webengineering.nftsite.collectors_site.data.model;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItem;


public interface Nft extends DataItem<Integer> {
    
    String getNome();
    Artista getArtista();
    String getAnno();
    void setNome(String nome);
    void setArtista(Artista artista);
    void setAnno(String anno);
}
