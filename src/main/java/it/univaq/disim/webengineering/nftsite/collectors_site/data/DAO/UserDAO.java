package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;

public interface UserDAO {

    boolean getUsernameEsistente(String username) throws DataException;

    boolean getEmailEsistente(String email) throws DataException;

    User getCredenziali(String username, String password) throws DataException;

    User createUser(User user) throws DataException;

    User getUser(int id) throws DataException;

    int updateUser(User user) throws DataException;


    void storeUser(User user) throws  DataException;

}
