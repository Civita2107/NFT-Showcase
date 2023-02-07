package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO.FotoDAO;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Foto;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.proxy.FotoProxy;
import it.univaq.disim.webengineering.nftsite.framework.data.DAO;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;

public class FotoDAOimpl extends DAO implements FotoDAO {

    private PreparedStatement sFotoByID, sFotoByDisco, sFotoByFileName, sFoto, iFoto, dFotoByDisco;

    public FotoDAOimpl(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            //precompiliamo tutte le query utilizzate nella classe
            sFotoByID = connection.prepareStatement("SELECT * FROM Foto WHERE ID=?");
            sFotoByDisco = connection.prepareStatement("SELECT ID FROM Foto WHERE IDDisco=?");
            sFotoByFileName = connection.prepareStatement("SELECT * FROM Foto WHERE filename=?");
            sFoto = connection.prepareStatement("SELECT ID FROM Foto");
            dFotoByDisco = connection.prepareStatement("DELETE FROM Foto WHERE ID=? AND IDDisco=?");
            iFoto = connection.prepareStatement("INSERT INTO Foto (size, type, filename, IDDisco, versione) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException ex) {
            throw new DataException("Error initializing newspaper data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {

        try {
            sFotoByID.close();
            sFotoByDisco.close();
            dFotoByDisco.close();
        } catch (SQLException ex) {
            throw new DataException("Error closing prepared statements", ex);
        }
        super.destroy();

    }

    @Override
    public Foto createFoto() {
        return new FotoProxy(getDataLayer());
    }

    //helper
    private FotoProxy createFoto(ResultSet rs) throws DataException {
        FotoProxy i = (FotoProxy) createFoto();
        try {
            i.setKey(rs.getInt("ID"));
            i.setFotoSize(rs.getLong("size"));
            i.setFotoType(rs.getString("type"));
            i.setFilename(rs.getString("filename"));
            i.setVersion(rs.getLong("versione"));
          //  i.setNftKey(rs.getInt("IDDisco"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create Foto object form ResultSet", ex);
        }
        return i;
    }

    @Override
    public Foto getFoto(String filename) throws DataException {
        Foto i = null;
        try {
            sFotoByFileName.setString(1, filename);
            try (ResultSet rs = sFotoByFileName.executeQuery()) {
                if (rs.next()) {
                    i = createFoto(rs);
                    dataLayer.getCache().add(Foto.class, i);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Foto by filename", ex);
        }
        return i;
    }

    @Override
    public Foto getFoto(int Foto_key) throws DataException {
        Foto i = null;
        //prima vediamo se l'oggetto è già stato caricato
        //first look for this object in the cache
        try {
            sFotoByID.clearParameters();
            sFotoByID.setInt(1, Foto_key);
            try (ResultSet rs = sFotoByID.executeQuery()) {
                if (rs.next()) {
                    i = createFoto(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Foto by ID", ex);
        }
        return i;
    }

    @Override
    public List<Foto> getFoto() throws DataException {
        List<Foto> Foto = new ArrayList<>();
        try (ResultSet rs = sFoto.executeQuery()) {
            while (rs.next()) {
                Foto.add(getFoto(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Foto", ex);
        }
        return Foto;
    }

    @Override
    public List<Foto> getFoto(Nft Nft) throws DataException {
        List<Foto> result = new ArrayList<>();
        try {
            sFotoByDisco.setInt(1, Nft.getKey());
            try (ResultSet rs = sFotoByDisco.executeQuery()) {
                while (rs.next()) {
                    result.add(getFoto(rs.getInt("ID")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Fotos by Nft", ex);
        }
        return result;
    }

    @Override
    public void storeFoto(List<Foto> foto) throws DataException {
        try {
            for (Foto photo : foto) {
                iFoto.setInt(1, (int) photo.getFotoSize());
                iFoto.setString(2, photo.getFotoType());
                iFoto.setString(3, photo.getFilename());
                iFoto.setInt(4, photo.getNft().getKey());
                iFoto.setInt(5, 1);
                iFoto.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to store Foto", ex);
        }
    }

    @Override
    public void deleteFoto(Nft Nft, Foto Foto) throws DataException {
        try {
            dFotoByDisco.setInt(1, Foto.getKey());
            dFotoByDisco.setInt(2, Nft.getKey());
            dFotoByDisco.executeUpdate();
        } catch (SQLException ex) {
            throw new DataException("Unable to delete Foto", ex);
        }

    }

  
    
}
