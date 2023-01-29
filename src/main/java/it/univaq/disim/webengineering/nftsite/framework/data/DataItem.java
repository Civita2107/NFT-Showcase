package it.univaq.disim.webengineering.nftsite.framework.data;

/**
 *
 * @param <KT> the key type
 */
public interface DataItem<KT> {

    KT getKey();

    long getVersion(); //per evitare accesso concorrente (lock [ottimistico funziona meglio es questo prog][pessimistico che ti fa fare l'aggiornamento ma devi fare refresh per vedere le modifiche]) cosi il primo che arriva ad aprire un record db sia l'unico che ci puo lavorare
                        //succede solo quando facciamo un update quindi il version servirà specialmente a quello
                        //OptimistickLockExeption -> il dato è modificato nel mentre che lo sto modificando e quindi mi fa fare refresh per poi inserire di nuovo

    void setKey(KT key);

    void setVersion(long version);

}
