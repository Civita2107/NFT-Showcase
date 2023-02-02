package it.univaq.disim.webengineering.nftsite.collectors_site.data.impl;
import java.io.InputStream;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Foto;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemImpl;


public class FotoImpl extends DataItemImpl<Integer> implements Foto {

    @Override
    public InputStream getImageData() throws DataException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setImageData(InputStream is) throws DataException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getImageType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setImageType(String type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long getImageSize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setImageSize(long size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getFilename() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setFilename(String imageFilename) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
