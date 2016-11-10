package jonas.com.redditpoc.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import jonas.com.redditpoc.R;
import jonas.com.redditpoc.presenters.TopFragmentPresenter;
import jonas.com.redditpoc.views.TopFragmentView;
import jonas.com.redditpoc.adapters.TopAdapter;
import jonas.com.redditpoc.model.Data;

public class TopFragment extends Fragment implements TopFragmentView {

    private TopFragmentPresenter presenter;
    private TopAdapter adapter;
    private RecyclerView recyclerView;


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
    }

    private void initViews(View view) {
        adapter = new TopAdapter(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.top_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void populateData(Data response) {
        adapter.setData(response.getChildren());
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyState() {
        recyclerView.setVisibility(View.GONE);
    }
}
