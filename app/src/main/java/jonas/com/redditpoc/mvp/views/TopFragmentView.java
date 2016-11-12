package jonas.com.redditpoc.mvp.views;

import java.util.ArrayList;

import jonas.com.redditpoc.model.Data;
import jonas.com.redditpoc.model.Post;

public interface TopFragmentView {

    void populateData(ArrayList<Post> posts);

    void onError (String error);

    void showEmptyState();

    void hideLoadMoreProgress();

    void showLoadMoreProgress();

    void loadMore(ArrayList<Post> posts);
}
