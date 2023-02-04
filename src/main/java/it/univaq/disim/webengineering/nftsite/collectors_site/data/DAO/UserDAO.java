package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;

import java.io.IOException;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

public interface UserDAO {

    boolean getUsernameEsistente(String username) throws DataLayerException, IOException;

    boolean getEmailEsistente(String email) throws DataLayerException, IOException;

    User getCredenziali(String username, String password) throws DataLayerException;

    //User nuovoUtente(User user) throws DataLayerException;

    User getUser(int id) throws DataLayerException;

    int updateUser(User user) throws DataLayerException;


    void storeUser(User user) throws  DataLayerException;

}
