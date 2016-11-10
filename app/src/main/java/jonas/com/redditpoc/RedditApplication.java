package jonas.com.redditpoc;

import android.app.Application;

import jonas.com.redditpoc.controllers.RequestManager;

public class RedditApplication extends Application {

    public static RequestManager requestManager;

    @Override
    public void onCreate() {
        super.onCreate();
        requestManager = RequestManager.getInstance(this);
    }
}
