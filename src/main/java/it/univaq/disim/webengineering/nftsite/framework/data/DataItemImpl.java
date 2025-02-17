/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.disim.webengineering.nftsite.framework.data;



/**
 *
 * @param <KT> the key type
 */
public abstract class DataItemImpl<KT> implements DataItem<KT> {
    
    private KT key;
    private long version;

    public DataItemImpl() {
        version = 0;
    }

    @Override
    public KT getKey() {
        return key;
    }

    @Override
    public void setKey(KT key) {
        this.key = key;
    }

    @Override
    public long getVersion() {
        return version;
    }

    @Override
    public void setVersion(long version) {
        this.version = version;
    }

}
