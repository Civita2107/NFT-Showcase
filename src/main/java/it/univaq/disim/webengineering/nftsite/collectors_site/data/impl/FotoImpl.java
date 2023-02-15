package it.univaq.disim.webengineering.nftsite.collectors_site.data.impl;
import java.io.InputStream;

import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Foto;
import it.univaq.disim.webengineering.nftsite.collectors_site.data.model.Nft;
import it.univaq.disim.webengineering.nftsite.framework.data.DataItemImpl;


public class FotoImpl extends DataItemImpl<Integer> implements Foto {

    private InputStream FotoData;
    private String FotoType;
    private long FotoSize;
    private String fileName;
    private Nft nft;

    public FotoImpl(InputStream FotoData, String FotoType, long FotoSize, String fileName) {
        super();
        this.FotoData = FotoData;
        this.FotoType = FotoType;
        this.FotoSize = FotoSize;
        this.fileName = fileName;
    }

    public FotoImpl() {
        super();
        this.FotoData = null;
        this.FotoType = "";
        this.FotoSize = 0;
        this.fileName = "";
    }



    @Override
    public InputStream getFotoData() {
        return FotoData;
    }

    @Override
    public void setFotoData(InputStream FotoData) {
        this.FotoData = FotoData;
    }

    @Override
    public String getFotoType() {
        return FotoType;
    }

    @Override
    public void setFotoType(String FotoType) {
        this.FotoType = FotoType;
    }

    @Override
    public long getFotoSize() {
        return FotoSize;
    }

    @Override
    public void setFotoSize(long FotoSize) {
        this.FotoSize = FotoSize;
    }

    @Override
    public String getFilename() {
        return fileName;
    }

    @Override
    public void setFilename(String fileName) {
        this.fileName = fileName;
    }

    
    @Override
    public Nft getNft() {
        return this.nft;
    }

    @Override
    public void setNft(Nft nft) {
        this.nft = nft;
    }

}
