package news.agoda.com.sample.home;

import android.support.annotation.StringRes;

import news.agoda.com.sample.data.ParentEntity;

public interface MainContract {
    void onSuccess(ParentEntity parentEntity);

    void onFailure(String errorMessage);

    void onFailure(@StringRes int errorResId);
}
