package news.agoda.com.sample.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
    private ListView listView;
    private ProgressBar progressBar;
    private TextView textView;

    private MainPresenter mainPresenter;
    private List<NewsEntity> newsEntityList = new ArrayList<>();
    private NewsListAdapter adapter;

    private OnItemClickListener mListener;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        listView = rootView.findViewById(android.R.id.list);
        progressBar = rootView.findViewById(R.id.progressBar);
        textView = rootView.findViewById(R.id.textView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsEntity newsEntity = newsEntityList.get(position);
                mListener.onItemClick(newsEntity);
            }
        });

        adapter = new NewsListAdapter(getActivity(),
                R.layout.list_item_news, newsEntityList);

        if (newsEntityList != null && !newsEntityList.isEmpty()) {
            adapter = new NewsListAdapter(getActivity(), R.layout.list_item_news, newsEntityList);
            listView.setAdapter(adapter);
        } else if (savedInstanceState != null) {
            String newsJson = savedInstanceState.getString(Constants.EXTRA_NEWS);
            newsEntityList = new Gson().fromJson(newsJson,
                    new TypeToken<ArrayList<NewsEntity>>() {}.getType());

            adapter = new NewsListAdapter(getActivity(), R.layout.list_item_news, newsEntityList);
            listView.setAdapter(adapter);
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
        listView.setVisibility(View.VISIBLE);
        this.newsEntityList = list;
        adapter = new NewsListAdapter(getActivity(), R.layout.list_item_news, newsEntityList);
        listView.setAdapter(adapter);
    }

    @Override
    public void hideList() {
        listView.setVisibility(View.GONE);
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
        listView.setEmptyView(textView);
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
