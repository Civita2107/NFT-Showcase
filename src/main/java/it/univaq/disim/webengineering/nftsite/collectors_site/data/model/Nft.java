package it.univaq.disim.webengineering.nftsite.collectors_site.data.model;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItem;


public interface Nft extends DataItem<Integer> {
    
    String getTitle();
    Artista getArtista();
    String getTokenId();
    String getContractAddress();
    String getDescription();
    String getMetadata();
    String getWalletAddress();
    void setTitolo(String nome);
    void setArtista(Artista artista);
    void setAnno(String anno);
}
