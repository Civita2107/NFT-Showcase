package it.univaq.disim.webengineering.nftsite.collectors_site.data.impl;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Comment;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.User;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemImpl;

public class CommentImpl extends DataItemImpl<Integer> implements Comment {

    private int id;
    private User user;
    private Nft nft;
    private String text;

    public CommentImpl(String text,User user,Nft nft){
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

    @Override
    public int getId(){
        return this.id;
    }

    @Override
    public User getUser(){
        return this.user;
    }

    @Override
    public Nft getNft(){
        return this.nft;
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public void setId(int id){
        this.id = id;
    }

    @Override
    public void setUser(User user){
        this.user = user;
    }

    @Override
    public void setNft(Nft nft){
        this.nft = nft;
    }

    @Override
    public void setText(String text){
        this.text = text;
    }



}
