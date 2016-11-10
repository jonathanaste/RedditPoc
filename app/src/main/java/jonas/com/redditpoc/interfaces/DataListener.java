package jonas.com.redditpoc.interfaces;

public interface DataListener<T> {

    void onError(String msg);

    void onDataUpdate(T response);

}
