package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp;

import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.CommentDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Comment;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.framework.data.DAO;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;

public class CommentDAOimpl extends DAO implements CommentDAO {

    public CommentDAOimpl(DataLayer d) {
        super(d);
        //TODO Auto-generated constructor stub
    }

    @Override
    public List<Comment> getComments(Nft nft) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
