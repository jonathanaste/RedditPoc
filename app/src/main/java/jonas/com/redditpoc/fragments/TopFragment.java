package jonas.com.redditpoc.fragments;

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

import jonas.com.redditpoc.R;
import jonas.com.redditpoc.presenters.TopFragmentPresenter;
import jonas.com.redditpoc.views.TopFragmentView;
import jonas.com.redditpoc.adapters.TopAdapter;
import jonas.com.redditpoc.model.Data;

public class TopFragment extends Fragment implements TopFragmentView {

    private TopFragmentPresenter presenter;
    private TopAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private View emptyState;
    private TextView emptyStateText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top, container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        presenter = new TopFragmentPresenter(this);
        presenter.getTopPost();
        progressBar.setVisibility(View.VISIBLE);
    }

    private void initViews(View view) {

        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        emptyState = view.findViewById(R.id.empty_state);
        emptyStateText = (TextView) emptyState.findViewById(R.id.description);

        adapter = new TopAdapter(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.top_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void populateData(Data response) {
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
}
