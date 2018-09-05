package news.agoda.com.sample.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
    private ProgressDialog progressDialog;

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
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getString(R.string.loading));

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
        this.newsEntityList = list;
        adapter = new NewsListAdapter(getActivity(), R.layout.list_item_news, newsEntityList);
        listView.setAdapter(adapter);
    }

    @Override
    public void hideList() {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.empty_list);
        listView.setEmptyView(textView);
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (progressDialog.isShowing())
            progressDialog.hide();
    }

    @Override
    public void showError(String errorMessage) {
        TextView textView = new TextView(getActivity());
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

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        if (savedInstanceState != null) {
//            String newsJson = savedInstanceState.getString(Constants.EXTRA_NEWS);
//            newsEntityList = new Gson().fromJson(newsJson,
//                    new TypeToken<ArrayList<NewsEntity>>(){}.getType());
//
//            NewsListAdapter adapter = new NewsListAdapter(getActivity(), R.layout.list_item_news, newsEntityList);
//            listView.setAdapter(adapter);
//        }
//    }
}