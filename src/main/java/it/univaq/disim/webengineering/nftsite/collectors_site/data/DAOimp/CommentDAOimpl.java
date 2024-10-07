package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.CommentDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.NftDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.UserDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.impl.CommentImpl;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.impl.UserImpl;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Comment;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.proxy.CommentProxy;
import it.univaq.disim.webengineering.nftsite.framework.data.DAO;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;

public class CommentDAOimpl extends DAO implements CommentDAO {

    private PreparedStatement sCommentByNft, iComment, sComment, dComment, sCommentUser;

    public CommentDAOimpl(DataLayer d) {
        super(d);
    }

    public Comment createComment() {
        return new CommentProxy(getDataLayer());
    }

    private CommentProxy createComment(ResultSet rs) throws DataException {
        try {
            CommentProxy a = (CommentProxy) createComment();
            NftDAOimp nft = new NftDAOimp(this.dataLayer);
            UserDAOimpl user = new UserDAOimpl(this.dataLayer);
            nft.init();
            user.init();

            a.setNft(nft.getNft(rs.getInt("nft_id")));
            a.setText(rs.getString("text"));
            a.setUser(user.getUser(rs.getInt("user_id")));

            return a;
        } catch (SQLException ex) {
            throw new DataException("Unable to create Comment object form ResultSet", ex);
        }
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            sCommentByNft = connection.prepareStatement("SELECT * FROM comment WHERE nft_id = ?");
            iComment = connection.prepareStatement("INSERT INTO comment (user_id,nft_id,text) VALUES(?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            sCommentUser = connection.prepareStatement(
                    "SELECT c.*, u.username,u.email,u.password,u.foto FROM comment as c INNER JOIN users as u ON u.id = c.user_id INNER JOIN nft as n ON n.id = c.nft_id WHERE n.id =?");
            sComment = connection.prepareStatement("SELECT * FROM comment WHERE id = ?");

            dComment = connection.prepareStatement("DELETE FROM comment WHERE id=?");

        } catch (SQLException ex) {
            throw new DataException("Error initializing collectors data layer", ex);
        }
    }

    @Override
    public List<Comment> getCommentsUser(Nft nft) throws DataException {
        List<Comment> commenti_utenti = new ArrayList<>();

        try {
            sCommentUser.setInt(1, nft.getKey());
            try (ResultSet rs = sCommentUser.executeQuery()) {
                while (rs.next()) {

                    Comment commento = createComment(rs);

                    commenti_utenti.add(commento);

                }

            }

        } catch (SQLException ex) {
            throw new DataException("Unable to get comments from ResultSet", ex);

        }
        return commenti_utenti;

    }

    @Override
    public List<Comment> getComments(Nft nft) throws DataException {
        List<Comment> commenti = new ArrayList<>();

        try {
            sCommentUser.setInt(1, nft.getKey());
            try (ResultSet rs = sCommentUser.executeQuery()) {
                while (rs.next()) {
                    if (rs.next()) {
                        Comment commento = createComment(rs);

                        commenti.add(commento);
                    }
                }
            }

        } catch (SQLException ex) {
            throw new DataException("Unable to get comments from ResultSet", ex);

        }

        return commenti;

    }

    @Override
    public Comment getComment(int key) throws DataException {
        Comment a = null;
        if (dataLayer.getCache().has(Comment.class, key)) {
            a = dataLayer.getCache().get(Comment.class, key);
        } else {
            try {
                sComment.setInt(1, key);
                try (ResultSet rs = sComment.executeQuery()) {
                    if (rs.next()) {
                        a = createComment(rs);
                        dataLayer.getCache().add(Comment.class, a);
                    }
                }
            } catch (DataException ex) {
                Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                throw new DataException("Unable to load comment by ID", ex);
            }
        }
        return a;

    }

    @Override
    public void storeComment(Comment comment) throws DataException {

        try (PreparedStatement ps = iComment) {
            ps.setInt(1, comment.getUser().getKey());
            ps.setInt(2, comment.getNft().getKey());
            ps.setString(3, comment.getText());

            if (iComment.executeUpdate() == 1) {
                try (ResultSet keys = iComment.getGeneratedKeys()) {
                    if (keys.next()) {
                        int key = keys.getInt(1);
                        comment.setKey(key);
                        dataLayer.getCache().add(Comment.class, comment);
                    }
                }
            }

        } catch (SQLException ex) {
            throw new DataException("Errore", ex);
        }

    }

    @Override
    public void deleteComment(Comment comment) throws DataException {

        try (PreparedStatement ps = dComment;) {
            ps.setInt(1, comment.getKey());
            ps.execute();
        } catch (SQLException ex) {
            throw new DataException("ERRORE", ex);
        }
    }

}
