package it.univaq.disim.webengineering.nftsite.collectors_site.data.model;
import java.util.List;

import it.univaq.disim.webengineering.nftsite.framework.data.DataItem;



public interface Acquistati extends DataItem<Integer> {
    
    String getNome();

    User getUtente();

    List<Nft> getNft();

    void setNome(String nome);

    void setPubblica(boolean pubblica);

    void setUtente(User utente);

    boolean isPubblica();

    void setNft(List<Nft> nft);
}


