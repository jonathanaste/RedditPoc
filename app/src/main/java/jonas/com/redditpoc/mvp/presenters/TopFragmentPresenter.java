package jonas.com.redditpoc.mvp.presenters;

import java.util.ArrayList;

import jonas.com.redditpoc.interfaces.DataListener;
import jonas.com.redditpoc.RedditApplication;
import jonas.com.redditpoc.model.Children;
import jonas.com.redditpoc.model.Post;
import jonas.com.redditpoc.mvp.views.TopFragmentView;
import jonas.com.redditpoc.backend.responses.TopResponse;

public class TopFragmentPresenter {

    private static final int MAX_POST_QUANTITY = 50;
    private TopFragmentView view;
    private String after;
    private int count;

    public TopFragmentPresenter(TopFragmentView view){
        this.view = view;
    }

    public void getTopPost(){
        RedditApplication.requestManager.getTopPosts(new DataListener<TopResponse>() {
            @Override
            public void onError(String msg) {
                view.onError(msg);
            }

            @Override
            public void onDataUpdate(TopResponse response) {
                if(response!=null && response.getData() != null){
                    after = response.getData().getAfter();
                    count = response.getData().getChildren().size();

                    view.populateData(getPosts(response));
                }else{
                    view.showEmptyState();
                }
            }
        });
    }

    private ArrayList<Post> getPosts(TopResponse response){
        ArrayList<Post> post = new ArrayList<>();
        for(Children children : response.getData().getChildren()){
            post.add(children.getPost());
        }
        return post;
    }

    public void loadMore() {

        if(count<MAX_POST_QUANTITY) {
            view.showLoadMoreProgress();
            RedditApplication.requestManager.getTopPosts(new DataListener<TopResponse>() {
                @Override
                public void onError(String msg) {
                    view.onError(msg);
                    view.hideLoadMoreProgress();
                }

                @Override
                public void onDataUpdate(TopResponse response) {
                    view.hideLoadMoreProgress();
                    if (response != null && response.getData() != null) {
                        after = response.getData().getAfter();
                        count += response.getData().getChildren().size();
                        view.loadMore(getPosts(response));
                    } else {
                        view.showEmptyState();
                    }
                }
            }, after, count);
        }
    }

}
