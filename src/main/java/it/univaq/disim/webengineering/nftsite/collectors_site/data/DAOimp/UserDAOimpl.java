package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.UserDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.proxy.UserProxy;
import it.univaq.disim.webengineering.nftsite.framework.data.DAO;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemProxy;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;
import it.univaq.disim.webengineering.nftsite.framework.data.OptimisticLockException;

public class UserDAOimpl extends DAO implements UserDAO{

    private PreparedStatement sUser;
    private PreparedStatement identityCheck, sUserByEmail, sUserByUsername;
    private PreparedStatement sUsers, sUsersByKeyword;
    private PreparedStatement iUser, uUser, dUser;
    private PreparedStatement sFollower,sFollowing;
    private PreparedStatement iMedia;
    //private PreparedStatement UsersMostAttivi;

  
    public UserDAOimpl(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();

            iMedia = connection.prepareStatement("INSERT INTO media (foto) VALUES (?)");

            sFollower = connection.prepareStatement("Select u.* FROM users as u INNER JOIN follow as f ON f.following = u.id where f.following = ?");
            sFollowing = connection.prepareStatement("Select u.* FROM users as u INNER JOIN follow as f ON f.follower = u.id where f.follower = ?");

            sUser = connection.prepareStatement("SELECT * FROM users WHERE id=?");
            sUserByEmail = connection.prepareStatement("SELECT * FROM users WHERE email=?");
            sUserByUsername = connection.prepareStatement("SELECT * FROM users WHERE username=?");
            identityCheck = connection.prepareStatement("SELECT * FROM users WHERE Username=? AND Password=?");

            sUsers = connection.prepareStatement("SELECT * FROM users");
            sUsersByKeyword = connection.prepareStatement("SELECT * FROM users WHERE username LIKE ?");

            iUser = connection.prepareStatement("INSERT INTO users (username,email,password) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uUser = connection.prepareStatement("UPDATE users SET username=?,email=?,password=?,foto=? WHERE id=?");
            dUser = connection.prepareStatement("DELETE FROM users WHERE id=?");

        } catch (SQLException ex) {
            throw new DataException("Error initializing collectors data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {
        try {

            identityCheck.close();
            sUser.close();
            sUserByEmail.close();
            sUserByUsername.close();
            sUsers.close();
            sUsersByKeyword.close();

            iUser.close();
            uUser.close();
            dUser.close();

        } catch (SQLException ex) {
        }
        super.destroy();
    }

    @Override
    public User createUser() {
        return new UserProxy(getDataLayer());
    }

    private UserProxy createUser(ResultSet rs) throws DataException {
        try {
            UserProxy a = (UserProxy) createUser();

            a.setKey(rs.getInt("id"));
            a.setUsername(rs.getString("username"));
            a.setEmail(rs.getString("email"));
            a.setPassword(rs.getString("password"));

            return a;
        } catch (SQLException ex) {
            throw new DataException("Unable to create User object form ResultSet", ex);
        }
    }

    @Override
    public void storeUser(User User) throws DataException, SQLException {
        try {
            if (User.getKey() != null && User.getKey() > 0) {
                if (User instanceof DataItemProxy && !((DataItemProxy) User).isModified()) {
                    return;
                }
                uUser.setString(1, User.getUsername());
                uUser.setString(2, User.getEmail());
                uUser.setString(3, User.getPassword());
                uUser.setString(4, User.getFoto());


                uUser.setLong(5, User.getKey());

                //Tolto versione
                if (uUser.executeUpdate() == 0) {
                    throw new OptimisticLockException(User);
                }
            } else {
                iUser.setString(1, User.getUsername());
                iUser.setString(2, User.getEmail());
                iUser.setString(3, User.getPassword());

                if (iUser.executeUpdate() == 1) {
                    try (ResultSet keys = iUser.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            User.setKey(key);
                            dataLayer.getCache().add(User.class, User);
                        }
                    }
                }
            }
            if ((User instanceof DataItemProxy)) {
                ((DataItemProxy) User).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            if (ex.getMessage().contains("Duplicated entry")) {
                throw new DataException("Duplicated entry", ex);
            } else {
                throw new DataException("Unable to store User", ex);
            }
        }
    }

    @Override
    public List<User> getUsers() throws DataException {
        List<User> result = new ArrayList<>();

        try (ResultSet rs = sUsers.executeQuery()) {
            while (rs.next()) {
                result.add(createUser(rs));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Users", ex);
        }
        return result;
    }

    @Override
    public User getUser(int user_key) throws DataException {
        User a = null;
        if (dataLayer.getCache().has(User.class, user_key)) {
            a = dataLayer.getCache().get(User.class, user_key);
        } else {
            try {
                sUser.setInt(1, user_key);
                try (ResultSet rs = sUser.executeQuery()) {
                    if (rs.next()) {
                        a = createUser(rs);
                        dataLayer.getCache().add(User.class, a);
                    }
                }
            } catch (DataException ex) {
                Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                throw new DataException("Unable to load User by ID", ex);
            }
        }
        return a;
    }

    @Override
    public User getUserByUsername(String username) throws DataException {
        User a = null;
        try {
            sUserByUsername.setString(1, username);
            try (ResultSet rs = sUserByUsername.executeQuery()) {
                if (rs.next()) {
                    a = createUser(rs);
                    dataLayer.getCache().add(User.class, a);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load User by Username", ex);
        }
        return a;
    }

    @Override
    public User getUserByEmail(String email) throws DataException {
        User a = null;
        try {
            sUserByEmail.setString(1, email);
            try (ResultSet rs = sUserByEmail.executeQuery()) {
                if (rs.next()) {
                    a = createUser(rs);
                    dataLayer.getCache().add(User.class, a);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load User by Email", ex);
        }
        return a;
    }

    @Override
    public List<User> getUsersByKeyword(String keyword) throws DataException {
        try {
            sUsersByKeyword.setString(1, "%" + keyword + "%");
            try (ResultSet rs = sUsersByKeyword.executeQuery()) {
                List<User> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(createUser(rs));
                }
                return result;
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Users by keyword", ex);
        }
    }

    @Override
    public User identityCheck(String username, String password) throws DataException {
        try {
            identityCheck.setString(1, username);
            identityCheck.setString(2, password);
            try (ResultSet rs = identityCheck.executeQuery()) {
                if (rs.next()) {
                    User a = createUser(rs);
                    dataLayer.getCache().add(User.class, a);
                    return createUser(rs);
                }
            }
        } catch (SQLException | DataException ex) {
            throw new DataException("User non trovato", ex);
        }
        return null;
    }

    public void storeMedia(byte[] media) throws DataException {
        try (PreparedStatement ps = iMedia ) {
            ps.setBytes(1, media);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataException("Unable to store media for user", e);
        }
    }

    @Override
    public List<User> getFollower(User user) throws DataException {
        List<User> l = new ArrayList<>();
        try {
            sFollower.setInt(1, user.getKey());
            try (ResultSet rs = sFollower.executeQuery()) {
                while(rs.next()){
                    User a = createUser(rs);
                    l.add(a);
                }
            }            
        } catch (DataException ex) {
            Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            throw new DataException("Unable to load follower", ex);
        }
        return l;
    }

    @Override
    public List<User> getFollowing(User user) throws DataException {
        List<User> l = new ArrayList<>();

        try {
            sFollowing.setInt(1, user.getKey());
            try (ResultSet rs = sFollowing.executeQuery()) {
                while(rs.next()){
                    User a = createUser(rs);
                    l.add(a);
                }
            }
        } catch (DataException ex) {
            Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            throw new DataException("Unable to load follower", ex);
        }
        return l;
    }



 
    
}
