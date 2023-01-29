package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Wallet;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;

public interface WalletDAO {
    
    void storeWallet(Wallet wallet) throws DataException;
    

}
