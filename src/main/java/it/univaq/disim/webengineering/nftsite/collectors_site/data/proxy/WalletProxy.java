package it.univaq.disim.webengineering.nftsite.collectors_site.data.proxy;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.NftDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.impl.WalletImpl;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemProxy;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;

public class WalletProxy extends WalletImpl implements DataItemProxy {

    protected boolean modified;
    protected DataLayer dataLayer;

    public WalletProxy(DataLayer d) {
        super();
        this.dataLayer = d;
        this.modified = false;
    }

    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }

    @Override
    public void setAddress(String address) {
        super.setAddress(address);
        this.modified = true;
    }

    @Override
    public void setUserId(int id) {
        super.setUserId(id);
        this.modified = true;
    }
    /*
     * @Override
     * public void setUser(User user) {
     * super.setUser(user);
     * this.modified = true;
     * }
     */

    @Override
    public void setNfts(List<Nft> nfts) {
        super.setNfts(nfts);
        this.modified = true;
    }

    @Override
    public List<Nft> getNfts() {
        if (super.getNfts() == null) {
            try {
                super.setNfts(((NftDAO) dataLayer.getDAO(Nft.class)).getNftsByWallet(this));
            } catch (DataException e) {
                Logger.getLogger(NftProxy.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return super.getNfts();
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