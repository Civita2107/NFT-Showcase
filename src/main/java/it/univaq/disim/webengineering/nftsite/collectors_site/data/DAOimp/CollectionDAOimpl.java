package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp;

import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Collection;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;


import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.CollectionDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.impl.CollectionImpl;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.impl.UserImpl;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DAO;
import it.univaq.disim.webengineering.nftsite.framework.data.DB;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

public class CollectionDAOimpl extends DAO implements CollectionDAO {

    public CollectionDAOimpl(DataLayer d) {
        super(d);
    }

    @Override
    public Integer getTotalPageBySearch(String viewItem) throws DataLayerException{

        viewItem.replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![");

        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) as totali FROM (SELECT * FROM Collection WHERE nome LIKE ?) as tot")){
                ps.setString(1, "%" + viewItem + "%");
                //ps.setInt(2, (Integer.valueOf(numPage)*4)-4);
                //ps.setInt(3, (Integer.valueOf(numPage)*4));
                try(ResultSet rset = ps.executeQuery()){
                    if(rset.next()){
                        return (int) Math.ceil(rset.getDouble("totali")/4);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Integer getTotalPage() throws DataLayerException{
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("select CAST(count(*) as float) as tot from Collection")){
                try(ResultSet rset = ps.executeQuery()){
                    if(rset.next()){
                        return (int) Math.ceil(rset.getDouble("tot")/4);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
   
    @Override
    public List<Collection> searchCollectionByStrings(String value) throws DataLayerException {
        List<Collection> collezioni = new ArrayList();

        value.replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![");
                


        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM collezion c join user coll on c.id=coll.id WHERE nome LIKE ? ESCAPE '!'")) {
                ps.setString(1, "%" + value + "%");
                try (ResultSet rset = ps.executeQuery()) {
                    while (rset.next()) {
                        Collection c = new CollectionImpl();
                        c.setNome(rset.getString("nome"));
                        c.setContractAddress(rset.getString("contractAddress"));
                        collezioni.add(c);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("GET DISCHI BY PAGE", ex);
        } catch (DataLayerException ex) {
           //?? Logger.getLogger(DiscoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return collezioni;
    }

    @Override
    public List<Collection> getCollezioni() throws DataLayerException {

        List<Collection> collezioni = new ArrayList();
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM Collection")) {

                try (ResultSet rset = ps.executeQuery()) {
                    while (rset.next()) {
                        Logger.getLogger(CollectionDAOImpl.class.getName()).log(Level.SEVERE, null, rset.getString("nome"));
                        Collection c = new CollectionImpl();
                        c.setNome(rset.getString("nome"));
                        c.setContractAddress(rset.getString("contractAddress"));
                        collezioni.add(c);
                      
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("GET COLLEZIONI BY PAGE", ex);
        }   catch (DataLayerException ex) {
                Logger.getLogger(CollectionDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        return collezioni;
    }

    @Override
    public List<Collection> getCollezioni(String contractAddress) throws DataLayerException {

        List<Collection> collezioni = new ArrayList();
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM collezion c where c.contractAddress=?")) {
                ps.setString(1, contractAddress);
                try (ResultSet rset = ps.executeQuery()) {
                    while (rset.next()) {
                        CollectionImpl c = new CollectionImpl();
                        c.setNome(rset.getString("nome"));
                        c.setContractAddress(rset.getString("contractAddress"));
                        collezioni.add(c);

                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("GET COLLEZIONI BY PAGE", ex);
        }   catch (DataLayerException ex) {
            Logger.getLogger(CollectionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return collezioni;
    }
   
 


    //rivedere
    @Override
    public String getCACollection() throws DataLayerException {
        String contractAddress = "";
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("SELECT contractAddress FROM collection order by contractAddress desc limit 1 ";)) {
                try (ResultSet rset = ps.executeQuery()) {
                    while (rset.next()) {
                        contractAddress=rset.getString("contractAddress");

                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("GET COLLEZIONI BY PAGE", ex);
        }   catch (DataLayerException ex) {
            Logger.getLogger(CollectionDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return contractAddress;
    }

    @Override
    public Collection getCollezione(Nft nft) throws DataLayerException {

        Collection c = new CollectionImpl();
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM collezion where contractAddress=?")) {
                ps.setString(1, nft.getContractAddress());
                try (ResultSet rset = ps.executeQuery()) {
                    if (rset.next()) {
                        c.setNome(rset.getString("nome"));
                        c.setContractAddress(rset.getString("contractAddress"));
                       


                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("GET COLLEZIONI BY PAGE", ex);
        }   catch (DataLayerException ex) {
            Logger.getLogger(CollectionDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return c;
    }



    @Override
    public List<User> getUser(int id) throws DataLayerException {

        List<User> user = new ArrayList<>();
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM User where id!=?")) {
                ps.setInt(1, id);
                try (ResultSet rset = ps.executeQuery()) {
                    while (rset.next()) {
                        User c = new UserImpl(); //creare un costruttore vuoto per UserImpl
                        c.setUsername(getCACollection());(rset.getString("username"));
                        c.setId(rset.getInt("id"));
                        user.add(c);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("GET user BY PAGE", ex);
        }   catch (DataLayerException ex) {
            Logger.getLogger(CollectionDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

}
