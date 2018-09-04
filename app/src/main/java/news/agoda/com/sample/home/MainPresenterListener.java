package news.agoda.com.sample.home;

import android.support.annotation.StringRes;

import java.util.List;

import news.agoda.com.sample.data.NewsEntity;

public interface MainPresenterListener {

    void displayNewsList(List<NewsEntity> newsEntityList);

    void hideList();

    void showProgress();

    void hideProgress();

    void showError(String errorMessage);

    void showError(@StringRes int errorResId);
}
