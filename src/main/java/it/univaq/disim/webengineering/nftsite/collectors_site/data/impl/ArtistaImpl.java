package it.univaq.disim.webengineering.nftsite.collectors_site.data.impl;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Artista;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemImpl;


public class ArtistaImpl extends DataItemImpl<Integer> implements Artista {
    
    private int id;
    private String username;
    private String email;

    public ArtistaImpl() {
        super();
        this.username = "";
        this.email = "";
    }

    public ArtistaImpl(String user, String email) {
        super();
        this.username = user;
        this.email = email;
    }

    

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setUsername(String user) {
        this.username = user;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int getId() {
        return id;
    }
    @Override
    public void setId(int id) {
        this.id = id;
    }
}
