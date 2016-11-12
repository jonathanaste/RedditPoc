package jonas.com.redditpoc.ui.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

public abstract class EndlessScroll extends RecyclerView.OnScrollListener {

    private boolean isLoading;
    private boolean userScrolled;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // Here get the child count, item count and visible items
        // from layout manager
        int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        int pastVisibleItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

        // Now check if userScrolled is true and also check if
        // the item is end then update recycler view and set
        // userScrolled to false
        if (!isLoading && userScrolled && (visibleItemCount + pastVisibleItems) == totalItemCount) {
            userScrolled = false;
            isLoading = true;
            loadMore();
        }
    }


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        // If scroll state is touch scroll then set userScrolled
        // true
        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
            userScrolled = true;

        }
    }

    public abstract void loadMore();

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
