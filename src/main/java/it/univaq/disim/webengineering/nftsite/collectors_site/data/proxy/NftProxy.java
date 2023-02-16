package it.univaq.disim.webengineering.nftsite.collectors_site.data.proxy;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.WalletDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.impl.NftImpl;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Wallet;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemProxy;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;

public class NftProxy extends NftImpl implements DataItemProxy{

    protected boolean modified;
    protected int walletKey;
    protected DataLayer dataLayer;

    public NftProxy(DataLayer d) {
        super();
        this.dataLayer = d;
        this.walletKey = 0;
        this.modified = false;
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
        this.modified = true;
    }

    @Override
    public void setTokenId(String tokenId) {
        super.setTokenId(tokenId);
        this.modified = true;
    }

    @Override
    public void setContractAddress(String contractAddress) {
        super.setContractAddress(contractAddress);
        this.modified = true;
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description);
        this.modified = true;
    }

    @Override
    public void setMetadata(String metadata) {
        super.setMetadata(metadata);
        this.modified = true;
    }

    @Override
    public void setWalletAddress(String walletAddress) {
        super.setWalletAddress(walletAddress);
        this.modified = true;
    }

    @Override
    public String getWalletAddress() {
        if (super.getWalletAddress() == null) {
            try {
                super.setWalletAddress(((WalletDAO) dataLayer.getDAO(Wallet.class)).getWalletAddress(this));
            } catch(DataException e) {
                Logger.getLogger(WalletProxy.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return  super.getWalletAddress();
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
