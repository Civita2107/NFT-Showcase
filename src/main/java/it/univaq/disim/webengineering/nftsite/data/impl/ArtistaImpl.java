package it.univaq.disim.webengineering.nftsite.data.impl;

import it.univaq.disim.webengineering.nftsite.data.model.Artista;

public class ArtistaImpl implements Artista {
    
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
}
