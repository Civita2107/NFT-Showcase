package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import java.io.IOException;
import java.lang.System.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

import javax.servlet.http.HttpSession;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.WalletDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.impl.UserImpl;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.impl.WalletImpl;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Wallet;
import it.univaq.disim.webengineering.nftsite.framework.data.DAO;
import it.univaq.disim.webengineering.nftsite.framework.data.DB;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

public class WalletDAOimp extends DAO implements WalletDAO{
    

    
    public WalletDAOimp(DataLayer d) {
        super(d);
    }

    @Override
    public AsyncHttpClient getNfts(Wallet wallet) throws IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("GET", "https://eth-mainnet.g.alchemy.com/nft/v2/jdPdLRvO7cZua3xEfIlcFO9lAAWpF3K1/getNFTs?owner="+wallet.getAddress()+"&pageSize=20&withMetadata=false")
          .setHeader("accept", "application/json")
          .execute()
          .toCompletableFuture()
          .thenAccept(System.out::println)
          .join();
        
        client.close();
        return client;
    }

    @Override
    public List<Wallet> searchWalletByStringsLogged(HttpSession session) throws DataLayerException {
    int userId = (int) session.getAttribute("userId");
    List<Wallet> userWallets = new ArrayList<>();

    // Connessione al database e recupero dei dati
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
        // Apertura della connessione
        conn = DB.getConnection();

        // Creazione della query per recuperare i wallet dell'utente
        String query = "SELECT * FROM wallets WHERE userId=?";
        stmt = conn.prepareStatement(query);
        stmt.setInt(1, userId);

        // Esecuzione della query
        rs = stmt.executeQuery();

        // Recupero dei wallet
        while (rs.next()) {
            Wallet wallet = new WalletImpl();
            wallet.setAddress(rs.getString("address"));
            wallet.setUserId(rs.getInt("userId"));
            userWallets.add(wallet);
        }
    } catch (SQLException e) {
        // Gestione dell'errore
        e.printStackTrace();
    } 

    return userWallets;
}


 
    @Override
    public Wallet searchWalletByStrings(String address) throws DataLayerException {

      Wallet wallet = null;

      try (Connection connection = DB.getConnection()) {
          try (PreparedStatement ps = connection.prepareStatement("SELECT * from wallet WHERE address=?")) {
              ps.setString(1, address);
              try (ResultSet rset = ps.executeQuery()) {
                  while (rset.next()) {
                      wallet.setAddress(rset.getString("address"));
                      wallet.setUserId(rset.getInt("id"));
                      wallet.setNfts(rset.getArray("nfts")); //TODO trovare un modo di converitre List in array
                  }
              }
          }
      } catch (SQLException ex) {
          throw new DataLayerException("GET DISCHI BY PAGE", ex);
      } catch (DataLayerException ex) {
          Logger.getLogger(WalletDAOimp.class.getName()).log(Level.SEVERE, null, ex);
      }
      return wallet;
    }

    @Override
    public List<Wallet> getWallets() throws DataLayerException {
      List<Wallet> wallets = new ArrayList<>();
  
      // Connessione al database e recupero dei dati
      Connection conn = null;
      Statement stmt = null;
      ResultSet rs = null;
      try {
          // Apertura della connessione
          conn = DB.getConnection();
  
          // Creazione della query per recuperare tutti i wallet
          String query = "SELECT * FROM wallets";
          stmt = conn.createStatement();
  
          // Esecuzione della query
          rs = stmt.executeQuery(query);
  
          // Aggiunta dei wallet alla lista
          while (rs.next()) {
              Wallet wallet = new WalletImpl();
              wallet.setUserId(rs.getInt("userId"));
              wallet.setAddress(rs.getString("address"));
              wallets.add(wallet);
          }
      } catch (SQLException e) {
          // Gestione dell'errore
          e.printStackTrace();
      } finally {
          // Chiusura delle risorse
          try {
              if (rs != null) rs.close();
              if (stmt != null) stmt.close();
              if (conn != null) conn.close();
          } catch (SQLException e) {
              e.printStackTrace();
          }
      }
  
      return wallets;
  }
  

    @Override
    public List<Wallet> getWallets(int userId) throws DataLayerException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Wallet> wallets = new ArrayList<>();
        try {
            conn = DB.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM wallets WHERE userId = ?");
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Wallet wallet = new WalletImpl();
                wallet.setUserId(rs.getInt("userId"));
                wallet.setAddress(rs.getString("address"));
                wallets.add(wallet);
            }
        } catch (SQLException e) {
            e.printStackTrace();  
        } 
        return wallets;

    }

   
    @Override
    public void storeWallet(Wallet wallet) throws DataLayerException {
      try (Connection connection = DB.getConnection()) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO wallet (address,nft,userId) VALUES(?,?,?,?,?)", RETURN_GENERATED_KEYS)) {
            ps.setString(1, wallet.getAddress());
            ps.setString(2, wallet.getAddress());
            ps.setObject(3,wallet.getNfts().toArray());

            ps.executeUpdate();

            
        }
    } catch (SQLException ex) {
        throw new DataLayerException("NUOVO UTENTE", ex);
    }
   // return utente;
  }

 


    @Override
    public boolean deleteWallet(Wallet wallet) throws DataLayerException {
            boolean successo = false;
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("DELETE from wallet where address=?")) {
                ps.setString(1, wallet.getAddress());
                successo = ps.execute();
            }
        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE", ex);
        } catch (DataLayerException ex) {
            Logger.getLogger(WalletDAOimp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return successo;
    }

   

    
}
