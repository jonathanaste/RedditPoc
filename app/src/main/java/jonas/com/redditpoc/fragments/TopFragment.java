package jonas.com.redditpoc.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import jonas.com.redditpoc.R;
import jonas.com.redditpoc.interfaces.ActivityCallback;
import jonas.com.redditpoc.interfaces.OnItemClickListener;
import jonas.com.redditpoc.model.Children;
import jonas.com.redditpoc.model.Post;
import jonas.com.redditpoc.presenters.TopFragmentPresenter;
import jonas.com.redditpoc.views.TopFragmentView;
import jonas.com.redditpoc.adapters.TopAdapter;
import jonas.com.redditpoc.model.Data;

public class TopFragment extends Fragment implements TopFragmentView, OnItemClickListener {

    public static final String TAG = TopFragment.class.getSimpleName();
    private static final String POST_LIST_EXTRA = "POST_LIST_EXTRA";

    private TopFragmentPresenter presenter;
    private TopAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private View emptyState;
    private TextView emptyStateText;
    private ActivityCallback callback;
    private ArrayList<Children> data;


    public static TopFragment newInstance() {
        return new TopFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // If savedInstanceState is not null means that we can get the data from there.
        if (savedInstanceState != null) {
            data = (ArrayList<Children>) savedInstanceState.getSerializable(POST_LIST_EXTRA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        presenter = new TopFragmentPresenter(this);

        loadData();
    }

    private void loadData() {
        // If we have data on this array means that we are coming from a previous instance.
        if (data != null && !data.isEmpty()) {
            adapter.setData(data);
        } else { // Getting the information from backend.
            presenter.getTopPost();
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void initViews(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        emptyState = view.findViewById(R.id.empty_state);
        emptyStateText = (TextView) emptyState.findViewById(R.id.description);
        recyclerView = (RecyclerView) view.findViewById(R.id.top_recycler_view);

        adapter = new TopAdapter(getContext(), this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void populateData(Data response) {
        data = response.getChildren();
        emptyState.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        adapter.setData(response.getChildren());
    }

    @Override
    public void onError(String error) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        emptyStateText.setText(R.string.error);
        emptyState.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyState() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        emptyStateText.setText(R.string.cant_load_data);
        emptyState.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        this.callback = (ActivityCallback) context;
        super.onAttach(context);
    }

    @Override
    public void onItemClick(Post post) {
        callback.onItemSelected(ImageFragment.newInstance(post));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Saving the current list to get it back on savedInstanceState
        outState.putSerializable(POST_LIST_EXTRA, data);
    }

}
