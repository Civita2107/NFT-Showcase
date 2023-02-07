package it.univaq.disim.webengineering.nftsite.collectors_site.data.proxy;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.impl.NftImpl;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemProxy;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;

public class NftProxy extends NftImpl implements DataItemProxy{

    protected boolean modified;
    protected int key;
    protected DataLayer dataLayer;

    public NftProxy(DataLayer d) {
        super();
        this.dataLayer = d;
        this.key = 0;
        this.modified = false;
    }

    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }
    
    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void setModified(boolean dirty) {
        this.modified = dirty;
    }
    
}
