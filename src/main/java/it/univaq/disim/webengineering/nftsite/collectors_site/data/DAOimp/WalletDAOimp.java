package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.WalletDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.impl.NftImpl;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.impl.WalletImpl;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Wallet;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.proxy.WalletProxy;
import it.univaq.disim.webengineering.nftsite.framework.data.DAO;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;

public class WalletDAOimp extends DAO implements WalletDAO {

    private PreparedStatement SWalletbyId, SWalletAddress, SWallets, DWalletbyAddress, IWallet,sWalletNft,sWalletByNft;

    public WalletDAOimp(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            SWalletbyId = connection.prepareStatement("SELECT * FROM wallet WHERE user_id=?");
            SWallets = connection.prepareStatement("SELECT * FROM wallet");
            IWallet = connection.prepareStatement("INSERT INTO wallet (wallet_address,user_id) VALUES(?,?)");
            DWalletbyAddress = connection.prepareStatement("DELETE from wallet where wallet_address=?");
            SWalletAddress = connection.prepareStatement("SELECT * from wallet WHERE wallet_address=?");
            sWalletNft = connection.prepareStatement("SELECT n.* FROM nft as n INNER JOIN wallet as w WHERE n.wallet_address= ?");
            sWalletByNft = connection.prepareStatement("SELECT w.* FROM wallet as w INNER JOIN nft as n ON n.wallet_address=w.wallet_address WHERE w.wallet_address=?");
        } catch (SQLException ex) {
            throw new DataException("Error initializing collectors data layer", ex);
        }
    }


    @Override
    public Wallet createWallet() {
        return new WalletProxy(getDataLayer());
    }

    private WalletProxy createWallet(ResultSet rs) throws DataException {
        try {
            WalletProxy a = (WalletProxy) createWallet();

            a.setKey(rs.getInt("id"));
            a.setAddress(rs.getString("wallet_address"));
            a.setUserId(rs.getInt("user_id"));

            return a;
        } catch (SQLException ex) {
            throw new DataException("Unable to create User object form ResultSet", ex);
        }
    }


    @Override
    public String getNfts(Wallet wallet) throws IOException {
        String API = "https://eth-mainnet.g.alchemy.com/nft/v2/jdPdLRvO7cZua3xEfIlcFO9lAAWpF3K1/getNFTs?owner="
                + wallet.getAddress()
                + "&pageSize=100&withMetadata=true&excludeFilters[]=SPAM";
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        String nftData = "";
        try {
            Future<Response> future = client.prepareGet(API)
                    .setHeader("accept", "application/json")
                    .execute();

            Response response = future.get();
            nftData = response.getResponseBody();
        } catch (InterruptedException | ExecutionException e) {
            Logger.getLogger(WalletDAOimp.class.getName()).log(Level.SEVERE, "ERROR IN getNft", e);
        } finally {
            client.close();
        }
        return nftData;
    }

    @Override
    public List<Wallet> searchWalletByStringsLogged(HttpSession session) throws DataException {
        int userId = (int) session.getAttribute("user_id");
        List<Wallet> userWallets = new ArrayList<>();

        ResultSet rs;
        try {

            SWallets.setInt(1, userId);

            // Esecuzione della query

            rs = SWalletbyId.executeQuery();

            // Recupero dei wallet
            while (rs.next()) {
                Wallet wallet = createWallet(rs);
                userWallets.add(wallet);
            }
        } catch (SQLException e) {
            // Gestione dell'errore
            Logger.getLogger(WalletDAOimp.class.getName()).log(Level.SEVERE, "ERROR IN searchWalletByStringsLogged", e);
        }

        return userWallets;
    }

    @Override
    public Wallet searchWalletByStrings(String address) throws DataException {

        Wallet wallet = new WalletImpl();

        try {
            SWalletAddress.setString(1, address);
            try (PreparedStatement ps = SWalletAddress) {
                try (ResultSet rset = ps.executeQuery()) {
                    while (rset.next()) {
                        wallet = createWallet(rset);

                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataException("GET DISCHI BY PAGE", ex);
        }
        return wallet;
    }

    @Override
    public List<Wallet> getWallets() throws DataException {
        List<Wallet> wallets = new ArrayList<>();

        ResultSet rs;
        try {

            rs = SWallets.executeQuery();

            // Aggiunta dei wallet alla lista
            while (rs.next()) {
                Wallet wallet = createWallet(rs);
                wallets.add(wallet);
            }
        } catch (SQLException e) {
            // Gestione dell'errore
            Logger.getLogger(WalletDAOimp.class.getName()).log(Level.SEVERE, "ERROR IN getWallets()", e);
        }

        return wallets;
    }

    @Override
    public List<Wallet> getWallets(int userId) throws DataException {
        ResultSet rs;
        List<Wallet> wallets = new ArrayList<>();
        try {

            SWalletbyId.setInt(1, userId);
            rs = SWalletbyId.executeQuery();
            while (rs.next()) {
                Wallet wallet = createWallet(rs);
                wallets.add(wallet);
            }
        } catch (SQLException e) {
            Logger.getLogger(WalletDAOimp.class.getName()).log(Level.SEVERE, "ERROR IN getWallets(int userId)", e);
        }
        return wallets;

    }

    @Override
    public void storeWallet(Wallet wallet) throws DataException {
        try (PreparedStatement ps = IWallet;) {
            ps.setString(1, wallet.getAddress());
            ps.setInt(2, wallet.getUserId());

            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new DataException("Errore", ex);
        }
        // return utente;
    }

    @Override
    public void deleteWallet(Wallet wallet) throws DataException {
        try (PreparedStatement ps = DWalletbyAddress;) {
            ps.setString(1, wallet.getAddress());
            ps.execute();
        } catch (SQLException ex) {
            throw new DataException("ERRORE", ex);
        }
    }

    @Override
    public void saveNfts(Wallet wallet) throws DataException {
        try {

            String nftListJsonString = getNfts(wallet); // Prendo tutti gli nft in stringa

            Gson gson = new Gson();

            JsonObject jsonObject = gson.fromJson(nftListJsonString, JsonObject.class);
            JsonArray nftArray = jsonObject.getAsJsonArray("ownedNfts"); // converto in jsonArray object

            List<Nft> nftList = new ArrayList<>();
            for (int i = 0; i < nftArray.size(); i++) {
                JsonObject nftJson = nftArray.get(i).getAsJsonObject();
                String contractAddress = nftJson.get("contract").getAsJsonObject().get("address").getAsString();
                String tokenId = nftJson.get("id").getAsJsonObject().get("tokenId").getAsString();


                String title = nftJson.get("title").getAsString();
                String description = nftJson.get("description").getAsString();
                String metadata = nftJson.get("media").getAsJsonArray().get(0).getAsJsonObject().get("gateway").getAsString();

                Nft nft = new NftImpl(title, tokenId, contractAddress, description, metadata, wallet.getAddress());
                nftList.add(nft); // ho una list di nft
            }

            NftDAOimp snft = new NftDAOimp(this.dataLayer); // salvo gli nft sul db
            snft.init();
            snft.storeNft(nftList);
        } catch (IOException | DataException e) {
            throw new DataException("ERRORE", e);
        }

    }
    
    @Override
    public List<Nft> getNftsObject(Wallet wallet) throws DataException{
        List<Nft> nftList = new ArrayList<>();
        
       try{ 
        sWalletNft.setString(1, wallet.getAddress());
        try (ResultSet rs = sWalletNft.executeQuery()) {
            while (rs.next()) {
                Nft nft = new NftImpl();
                nft.setKey(rs.getInt("token_id"));
                nft.setTitle(rs.getString("title"));
                nft.setContractAddress(rs.getString("contract_address"));
                nft.setDescription(rs.getString("description"));
                nft.setMetadata(rs.getString("metadata"));
                nft.setWalletAddress(rs.getString("wallet_address"));
                
                nftList.add(nft);
            }
        }
     }
     
     catch (SQLException ex) {
            throw new DataException("Unable to load Nft", ex);
        }
        return nftList;
    }

    @Override
    public Wallet getWalletByNft(Nft nft) throws DataException{
        
       try{ 
        sWalletByNft.setString(1, nft.getWalletAddress());
        try (ResultSet rs = sWalletByNft.executeQuery()) {
                Wallet wallet = createWallet(rs);
                return wallet;
        }
     }
     
     catch (SQLException ex) {
            throw new DataException("Unable to load Nft", ex);
        }
    }



    }

