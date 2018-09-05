package news.agoda.com.sample.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import news.agoda.com.sample.R;
import news.agoda.com.sample.data.NewsEntity;
import news.agoda.com.sample.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragment.OnItemClickListener} interface
 * to handle interaction events.
 */
public class ListFragment extends Fragment implements MainPresenterListener {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView textView;

    private MainPresenter mainPresenter;
    private ArrayList<NewsEntity> newsEntityList = new ArrayList<>();
    private NewsListAdapter adapter;

    private OnItemClickListener mListener;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = rootView.findViewById(android.R.id.list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        progressBar = rootView.findViewById(R.id.progressBar);
        textView = rootView.findViewById(R.id.textView);

        adapter = new NewsListAdapter(newsEntityList, mListener);

        if (newsEntityList != null && !newsEntityList.isEmpty()) {
            adapter = new NewsListAdapter(newsEntityList, mListener);
            recyclerView.setAdapter(adapter);
        } else if (savedInstanceState != null) {
            String newsJson = savedInstanceState.getString(Constants.EXTRA_NEWS);
            newsEntityList = new Gson().fromJson(newsJson,
                    new TypeToken<ArrayList<NewsEntity>>() {}.getType());

            adapter = new NewsListAdapter(newsEntityList, mListener);
            recyclerView.setAdapter(adapter);
        } else {
            mainPresenter = new MainPresenter(this);
            mainPresenter.loadNews();
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemClickListener) {
            mListener = (OnItemClickListener) context;
            Fresco.initialize(context);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnItemClickListener {
        void onItemClick(NewsEntity newsEntity);
    }

    @Override
    public void displayNewsList(final List<NewsEntity> list) {
        recyclerView.setVisibility(View.VISIBLE);
        this.newsEntityList = (ArrayList<NewsEntity>) list;
        adapter = new NewsListAdapter(newsEntityList, mListener);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void hideList() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMessage) {
        textView.setVisibility(View.VISIBLE);
        textView.setText(errorMessage);
    }

    @Override
    public void showError(int errorResId) {
        showError(getString(errorResId));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        String jsonList = new Gson().toJson(newsEntityList,
                new TypeToken<ArrayList<NewsEntity>>(){}.getType());
        outState.putString(Constants.EXTRA_NEWS, jsonList);

        super.onSaveInstanceState(outState);
    }
}
