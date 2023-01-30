package it.univaq.disim.webengineering.nftsite.collectors_site.data.impl;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Comment;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemImpl;

public class CommentImpl extends DataItemImpl<Integer> implements Comment {

    private int id;
    private Utente user;
    private Nft nft;
    private String text;

    public CommentImpl(String text,Utente user,Nft nft){
        super();
        this.user = user;
        this.nft = nft;
        this.text = text;
    }

    public CommentImpl(){
        super();
        this.user = null;
        this.nft = null;
        this.text = "";
    }

    public int getId(){
        return this.id;
    }
    public Utente getUser(){
        return this.user;
    }
    public Nft getNft(){
        return this.nft;
    }
    public String getText(){
        return this.text;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setUser(Utente user){
        this.user = user;
    }
    public void setNft(Nft nft){
        this.nft = nft;
    }
    public void setText(String text){
        this.text = text;
    }



}
