package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;

import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Comment;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;

public interface CommentDAO {

    List<Comment> getCommentsUser(Nft nft) throws DataException;

    void storeComment(Comment comment) throws DataException;

    Comment getComment(int key) throws DataException;

    void deleteComment(Comment comment) throws DataException;
    
    List<Comment> getComments(Nft nft) throws DataException;
    
}
