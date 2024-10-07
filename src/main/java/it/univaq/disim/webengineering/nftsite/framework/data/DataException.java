package it.univaq.disim.webengineering.nftsite.framework.data;

/**
 *
 * @author Giuseppe Della Penna
 */
public class DataException extends Exception {

    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataException(Throwable cause) {
        super(cause);
    }

    

    @Override
    public String getMessage() {
        if (getCause() != null && getCause().getMessage() != null) {
            return super.getMessage() + " (" + getCause().getMessage() + ")";
        }
        else{
            return super.getMessage();
        }
    }
}