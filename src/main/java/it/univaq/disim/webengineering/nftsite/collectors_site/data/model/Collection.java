package it.univaq.disim.webengineering.nftsite.collectors_site.data.model;

import java.util.List;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import it.univaq.disim.webengineering.nftsite.framework.data.DataItem;

public interface Collection extends DataItem<Integer> {
    String getContractAdrress();
    String getwalletAddress();
    String getNome();
    int getTotalSupply();
    void setContractAddress(String contractAddress);
    void setWalletAddress(String walletAddress);
    void setNome(String nome);
    void setTotalSupply(int totalSupply);
    boolean isPubblica();
    void setPubblica(boolean pubblica);
    void setUser(User user);
    User getUser();
    List<Nft> getNfts();
    void setNfts(List<Nft> nft);

}
