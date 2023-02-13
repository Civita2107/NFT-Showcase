package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.json.Json;
import javax.servlet.http.HttpSession;

import org.asynchttpclient.AsyncHttpClient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Wallet;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;

public interface WalletDAO {

    List<Wallet> searchWalletByStringsLogged(HttpSession session) throws DataException;

    Wallet searchWalletByStrings(String value) throws DataException;

    List<Wallet> getWallets() throws DataException;

    List<Wallet> getWallets(int idUser) throws DataException;

    void deleteWallet(Wallet wallet_address) throws DataException;


    void storeWallet(Wallet wallet) throws DataException;

    String getNfts(Wallet wallet) throws DataException, IOException;

    String getNftsMetdata(String contractAddress, String tokenId) throws IOException;
    
    void saveNfts(Wallet wallet);
}
