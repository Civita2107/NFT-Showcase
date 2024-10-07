package it.univaq.disim.webengineering.nftsite.framework.data;


public class DataLayerException extends Exception {

    public DataLayerException(String message) {
        super(message);
    }

    public DataLayerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataLayerException(Throwable cause) {
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
