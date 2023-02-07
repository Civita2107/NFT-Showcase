package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;

import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;

public interface UserDAO {

 
    User createUser();

    List<User> getUsers() throws DataException;

    User getUser(int user_key) throws DataException;

    void storeUser(User user) throws DataException;

    List<User> getUsersByKeyword(String keyword) throws DataException;

    User identityCheck(String username, String password) throws DataException;

    User getUserByNickname(String nickname) throws DataException;

    User getUserByEmail(String email) throws DataException;

    //Statistiche TOP
    public List<User> usersMostDischi() throws DataException;

    public List<Integer> CountusersMostDischi() throws DataException;
}