package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.asynchttpclient.Response;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.UserDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.impl.UserImpl;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DB;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

public class UserDAOimpl implements UserDAO{

    @Override
    public boolean getUsernameEsistente(String username) throws DataLayerException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        HttpServletResponse  response=null;
        
        try {
          // Connessione al database
          conn = DB.getConnection();
          
          // Query per verificare se il nome utente esiste già
          pstmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
          pstmt.setString(1, username);
          rs = pstmt.executeQuery();
          
          if (rs.next()) {
            // messaggio di errore se lo user esiste
           response.sendError(HttpServletResponse.SC_CONFLICT, "User già esistente"); 
    
        } catch (SQLException ex) {

          throw new DataLayerException("USERNAME UTENTE", ex);
        } 
        
        
        return false;
    }

    @Override
    public boolean getEmailEsistente(String email) throws DataLayerException {
  
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      HttpServletResponse  response=null;
      
      try {
        // Connessione al database
        conn = DB.getConnection();
        
        // Query per verificare se la mail utente esiste già
        pstmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
        pstmt.setString(1, email);
        rs = pstmt.executeQuery();
        
        if (rs.next()) {
          // messaggio di errore se la mail esiste
         response.sendError(HttpServletResponse.SC_CONFLICT, "Email già esistente"); 
  
      } catch (SQLException ex) {

        throw new DataLayerException("EMAIL UTENTE", ex);
      } 
      
      
      return false;
    }

    @Override
    public User getCredenziali(String username, String password) throws DataLayerException {
  
          User utente = null;
          try (Connection connection = DB.getConnection()) {
              try (PreparedStatement ps = connection.prepareStatement( "SELECT * FROM user WHERE username=? AND password=?")) {

                  ps.setString(1, username);
                  ps.setString(2, password);
                  try (ResultSet rset = ps.executeQuery()) {
                      if (rset.next()) {
                         // utente.setUsername(rset.getString("username"));
                          utente.setId(rset.getInt("id"));
                          utente.setEmail(rset.getString("email"));
                      }
                  }
              }
          } catch (SQLException ex) {
              throw new DataLayerException("CREDENZIALI UTENTE", ex);
          }
          return utente;
      
    }

    @Override
    public void storeUser(User utente) throws DataLayerException {
          try (Connection connection = DB.getConnection()) {
              try (PreparedStatement ps = connection.prepareStatement("INSERT INTO user(username,password,email) VALUES(?,?,?,?,?)", RETURN_GENERATED_KEYS)) {
                  ps.setString(1, utente.getUsername());
                  ps.setString(2, utente.getPassword());
                  ps.setString(3, utente.getEmail());
  
                  ps.executeUpdate();
  
                  try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                      if (generatedKeys.next()) {
                          utente.setId(generatedKeys.getInt(1));
                      } else {
                          throw new DataLayerException("RECUPERO UTENTE");
                      }
                  }
              }
          } catch (SQLException ex) {
              throw new DataLayerException("NUOVO UTENTE", ex);
          }
          return utente;
    }

    @Override
    public User getUser(int id) throws DataLayerException {
      User utente = null;
      try (Connection connection = DB.getConnection()) {
          try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM user WHERE user.id=?")) {
              ps.setInt(1, id);
              try (ResultSet rset = ps.executeQuery()) {
                  if (rset.next()) {
                      utente = new UserImpl();

                      utente.setUsername(rset.getString("username"));
                      utente.setEmail(rset.getString("email"));
                  }
              }
          }
      } catch (SQLException ex) {
          throw new DataLayerException("DATI UTENTE", ex);
      }
      return utente;
    }

    @Override
    public int updateUser(User user) throws DataLayerException {
      int result;
      try (Connection connection = DB.getConnection()) {
          try (PreparedStatement ps = connection.prepareStatement("UPDATE user set  username=?, email=?, password=? WHERE user.id=?")) {
              ps.setString(1, user.getUsername());
              ps.setString(2, user.getEmail());
              ps.setInt(3, user.getId());
              result = ps.executeUpdate();
          }
      } catch (SQLException ex) {
          throw new DataLayerException("DATI UTENTE", ex);
      }
      return result;
    }


 
    
}
