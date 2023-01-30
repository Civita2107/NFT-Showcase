package it.univaq.disim.webengineering.nftsite.collectors_site.data.impl;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Utente;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemImpl;

public class Utenteimpl extends DataItemImpl<Integer> implements Utente{

    private int id;
    private String username;
    private String email;

    
    public Utenteimpl(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
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

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }
}
