package it.univaq.disim.webengineering.nftsite.collectors_site.data.DAO;

import java.util.List;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Acquistati;
import it.univaq.disim.webengineering.nftsite.framework.data.DataException;

public interface AcquistatiDAO {
    
    void storeAcquistati(Acquistati acquistati) throws DataException;
    List<Acquistati> getAcquistati() throws DataException; // i propri acquisti
    List<Acquistati> getAcquisti(int id) throws DataException; //per vedere gli acquisti degli altri utenti
    void setPubblica(boolean pubblica) throws DataException;
    
}
