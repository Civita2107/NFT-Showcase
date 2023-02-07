package it.univaq.disim.webengineering.nftsite.collectors_site.data.proxy;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.NftDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.impl.FotoImpl;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemProxy;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;

public class FotoProxy extends FotoImpl implements DataItemProxy {
    
    protected boolean modified;
    protected int key;

    protected DataLayer dataLayer;

    public FotoProxy(DataLayer d) {
        super();
        this.dataLayer = d;
        this.modified = false;
        this.key = 0;
    }

    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }

    @Override
    public void setFotoData(InputStream is) {
        super.setFotoData(is);
        this.modified = true;
    }

    @Override
    public void setFotoType(String type) {
        super.setFotoType(type);
        this.modified = true;
    }

    @Override
    public void setFotoSize(long size) {
        super.setFotoSize(size);
        this.modified = true;
    }

    @Override
    public void setFilename(String fileName) {
        super.setFilename(fileName);
        this.modified = true;
    }

    @Override
    public Nft getNft() {
        if (super.getNft() == null && key > 0) {
            try {
                super.setNft(((NftDAO) dataLayer.getDAO(Nft.class)).getNft(key));
            } catch(DataException e) {
                Logger.getLogger(FotoProxy.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return super.getNft();
    }

    @Override
    public void setNft(Nft nft) {
        super.setNft(nft);
        if (nft != null) {
            this.key = nft.getKey();
        } else {
            this.key = 0;
        }
        this.modified = true;
    }

    @Override
    public void setModified(boolean dirty) {
        this.modified = dirty;
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    public void setKey(int key) {
        this.key = key;
        super.setNft(null);
    }
}
