package it.univaq.disim.webengineering.nftsite.collectors_site.data.model;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItem;

public interface Foto extends DataItem<Integer> {

    
    InputStream getImageData() throws DataException;

    void setImageData(InputStream is) throws DataException;

    String getImageType();

    void setImageType(String type);

    long getImageSize();

    void setImageSize(long size);

    public String getFilename();

    public void setFilename(String imageFilename);

    public Disco getDisco();

    public void setDisco(Disco disco);


}