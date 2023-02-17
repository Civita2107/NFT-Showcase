package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Wallet;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;

public interface WalletDAO {

    List<Wallet> searchWalletByStringsLogged(HttpSession session) throws DataException;

    Wallet searchWalletByStrings(String value) throws DataException;

    List<Wallet> getWallets() throws DataException;

    List<Wallet> getWallets(int idUser) throws DataException;

    void deleteWallet(Wallet wallet_address) throws DataException;

    List<Nft> getNftsObject(Wallet wallet) throws DataException;

    void storeWallet(Wallet wallet) throws DataException;

    String getNfts(Wallet wallet) throws DataException, IOException;

    String getNftsMetdata(String contractAddress, String tokenId) throws IOException;
    
    void saveNfts(Wallet wallet) throws DataException;
    
    Wallet getWalletByNft(Nft nft) throws DataException;
}
