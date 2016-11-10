package jonas.com.redditpoc.presenters;

import jonas.com.redditpoc.interfaces.DataListener;
import jonas.com.redditpoc.RedditApplication;
import jonas.com.redditpoc.views.TopFragmentView;
import jonas.com.redditpoc.model.TopResponse;

public class TopFragmentPresenter implements DataListener<TopResponse> {

    TopFragmentView view;

    public TopFragmentPresenter(TopFragmentView view){
        this.view = view;
    }

    public void getTopPost(){
        RedditApplication.requestManager.getTopPosts(this);
    }

    @Override
    public void onDataUpdate(TopResponse response) {
        if(response!=null && response.getData() != null){
            view.populateData(response.getData());
        }else{
            view.showEmptyState();
        }
    }

    @Override
    public void onError(String msg) {
        view.onError(msg);
    }


}
