package it.univaq.disim.webengineering.nftsite.collectors_site.data.model;
import java.io.InputStream;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItem;

public interface Foto extends DataItem<Integer> {

    
    InputStream getFotoData() throws DataException;

    void setFotoData(InputStream is) throws DataException;

    String getFotoType();

    void setFotoType(String type);

    long getFotoSize();

    void setFotoSize(long size);

    public String getFilename();

    public void setFilename(String FotoFilename);

    void setNft(Nft Nft);

    Nft getNft();


}