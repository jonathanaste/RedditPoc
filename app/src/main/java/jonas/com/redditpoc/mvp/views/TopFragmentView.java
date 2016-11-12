package jonas.com.redditpoc.mvp.views;

import jonas.com.redditpoc.model.Data;

public interface TopFragmentView {

    void populateData(Data response);

    void onError (String error);

    void showEmptyState();
}
