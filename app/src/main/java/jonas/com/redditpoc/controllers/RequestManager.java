package jonas.com.redditpoc.controllers;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import jonas.com.redditpoc.interfaces.DataListener;
import jonas.com.redditpoc.requests.TopRequest;
import jonas.com.redditpoc.model.TopResponse;

public class RequestManager {

    private static RequestQueue queue;
    private static RequestManager instance;

    private RequestManager(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public static RequestManager getInstance(Context context) {
        if (instance == null) {
            instance = new RequestManager(context);
        }
        return instance;
    }

    public void getTopPosts(DataListener<TopResponse> listener) {
        TopRequest topRequest = new TopRequest(listener);
        queue.add(topRequest);
    }

}
