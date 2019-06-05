package enrich.enrichacademy.model;

/**
 * Created by varunbarve on 21/07/17.
 */

public class ListResponseModel<T> {

    public String message;
    public int status;
    public T[] data;
}
