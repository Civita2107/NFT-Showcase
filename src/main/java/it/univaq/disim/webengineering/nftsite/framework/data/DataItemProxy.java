package it.univaq.disim.webengineering.nftsite.framework.data;

public interface DataItemProxy {
    
    boolean isModified();

    void setModified(boolean dirty);
}
