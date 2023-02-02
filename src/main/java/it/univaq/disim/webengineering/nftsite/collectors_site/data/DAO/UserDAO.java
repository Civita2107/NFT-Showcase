package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;

import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayerException;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface UserDAO {

    boolean getUsernameEsistente(String username) throws DataLayerException, IOException;

    boolean getEmailEsistente(String email) throws DataLayerException, IOException;

    User getCredenziali(String username, String password) throws DataLayerException;

    //User nuovoUtente(User user) throws DataLayerException;

    User getUser(int id) throws DataLayerException;

    int updateUser(User user) throws DataLayerException;


    void storeUser(User user) throws  DataLayerException;

}
