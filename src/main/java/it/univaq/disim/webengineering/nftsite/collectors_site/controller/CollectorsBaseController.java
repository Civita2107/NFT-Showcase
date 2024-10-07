package it.univaq.disim.webengineering.nftsite.collectors_site.controller;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.sql.DataSource;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.DAOimp.CollectorsDataLayer;
import it.univaq.disim.webengineering.nftsite.framework.controller.AbstractBaseController;
import it.univaq.disim.webengineering.nftsite.framework.data.DataLayer;

public abstract class CollectorsBaseController extends AbstractBaseController {
    
    @Override
    protected DataLayer createDataLayer (DataSource ds) throws ServletException {
        try {
            return new CollectorsDataLayer(ds);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
