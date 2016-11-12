package jonas.com.redditpoc.interfaces;

/**
 * This is the listener that will be use by {@link jonas.com.redditpoc.backend.requests.BaseRequest}
 * to return the data <T>
 *
 * @param <T> the response
 */
public interface DataListener<T> {

    void onError(String msg);

    void onDataUpdate(T response);

}
