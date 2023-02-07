package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.stream.*;



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
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;

public class WalletDAOimp extends DAO implements WalletDAO{

private PreparedStatement SWalletbyId,SWalletAddress,SWallets,DWalletbyAddress,IWallet;


    public WalletDAOimp(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            SWalletbyId = connection.prepareStatement("SELECT * FROM wallets WHERE userId=?");
            SWallets= connection.prepareStatement("SELECT * FROM wallets");
            IWallet =connection.prepareStatement("INSERT INTO wallet (address,nft,userId) VALUES(?,?,?,?,?)", RETURN_GENERATED_KEYS); 
            DWalletbyAddress = connection.prepareStatement("DELETE from wallet where address=?");
            SWalletAddress =  connection.prepareStatement("SELECT * from wallet WHERE address=?"); 

            
        } catch (SQLException ex) {
            throw new DataException("Error initializing collectors data layer", ex);
        }
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
    public List<Wallet> searchWalletByStringsLogged(HttpSession session) throws DataException {
    int userId = (int) session.getAttribute("userId");
    List<Wallet> userWallets = new ArrayList<>();

    // Connessione al database e recupero dei dati
   // Connection conn = null;
   // PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
        // Apertura della connessione
        //conn = DAO.getConnection();

        // Creazione della query per recuperare i wallet dell'utente
      //  String query = "SELECT * FROM wallets WHERE userId=?";
      //  TODO stmt = conn.prepareStatement(query);
      
        SWallets.setInt(1, userId);

        // Esecuzione della query
        
        rs = SWalletbyId.executeQuery();

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
    public Wallet searchWalletByStrings(String address) throws DataException {

      Wallet wallet = null;

          try (PreparedStatement ps = SWalletAddress){// TODO connection.prepareStatement("SELECT * from wallet WHERE address=?")) 
              ps.setString(1, address);
              try (ResultSet rset = ps.executeQuery()) {
                  while (rset.next()) {
                      wallet.setAddress(rset.getString("address"));
                      wallet.setUserId(rset.getInt("id"));
                      wallet.setNfts(rset.getArray("nfts")); //TODO trovare un modo di converitre List in array
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
  
      // Connessione al database e recupero dei dati
   //   Connection conn = null;
    //  Statement stmt = null;
      ResultSet rs = null;
      try {
          // Apertura della connessione
         // conn = DB.getConnection();
  
          // Creazione della query per recuperare tutti i wallet
         // String query = "SELECT * FROM wallets";
         // stmt = conn.createStatement();
        


          // Esecuzione della query
         // rs = stmt.executeQuery(query); TODO
            rs=SWallets.executeQuery();

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
      }
  
      return wallets;
  }
  

    @Override
    public List<Wallet> getWallets(int userId) throws DataException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Wallet> wallets = new ArrayList<>();
        try {
           // conn = DB.getConnection(); TODO
            //stmt = conn.prepareStatement("SELECT * FROM wallets WHERE userId = ?"); TODO
           
            SWalletbyId.setInt(1, userId);
            rs = SWalletbyId.executeQuery();
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
    public void storeWallet(Wallet wallet) throws DataException {
        try (PreparedStatement ps = IWallet;){ // TODO connection.prepareStatement("INSERT INTO wallet (address,nft,userId) VALUES(?,?,?,?,?)", RETURN_GENERATED_KEYS)) {
            ps.setString(1, wallet.getAddress());
            ps.setString(2, wallet.getAddress());
            ps.setObject(3,wallet.getNfts());

            ps.executeUpdate();

            
        }
     catch (SQLException ex) {
        throw new DataException("NUOVO UTENTE", ex);
    }
   // return utente;
  }

 


    @Override
    public boolean deleteWallet(Wallet wallet) throws DataException {
            boolean successo = false;
            try (PreparedStatement ps = DWalletbyAddress;){ // TODO connection.prepareStatement("DELETE from wallet where address=?")) 
                ps.setString(1, wallet.getAddress());
                successo = ps.execute();
            }
         catch (SQLException ex) {
            throw new DataException("ERRORE", ex);
        } 
        return successo;
    }

   

    
}
