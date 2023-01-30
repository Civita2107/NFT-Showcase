package it.univaq.disim.webengineering.nftsite.collectors_site.data.model;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItem;

public interface Comment extends DataItem<Integer> {
    int getId();
    Utente getUser();
    Nft getNft();
    String getText();
    void setId(int id);
    void setUser(Utente user);
    void setNft(Nft nft);
    void setText(String text);
}
