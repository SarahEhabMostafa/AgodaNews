package news.agoda.com.sample.home;

import android.support.annotation.NonNull;

import news.agoda.com.sample.R;
import news.agoda.com.sample.data.ParentEntity;

public class MainPresenter implements MainContract {
    private MainPresenterListener presenterListener;
    private MainRepository mainRepository;

    public MainPresenter(@NonNull MainPresenterListener presenterListener) {
        this.presenterListener = presenterListener;
        mainRepository = new MainRepository(this);
    }

    public void loadNews() {
        presenterListener.showProgress();
        mainRepository.getUpdatedNews();
    }

    @Override
    public void onSuccess(ParentEntity parentEntity) {
        if(parentEntity.getResults() != null && !parentEntity.getResults().isEmpty()) {
            presenterListener.hideProgress();
            presenterListener.displayNewsList(parentEntity.getResults());
        } else {
            presenterListener.hideList();
            presenterListener.hideProgress();
            presenterListener.showError(R.string.empty_list);
        }
    }

    @Override
    public void onFailure(String errorMessage) {
        presenterListener.hideProgress();
        presenterListener.hideList();
        presenterListener.showError(errorMessage);
    }

    @Override
    public void onFailure(int errorResId) {
        presenterListener.hideProgress();
        presenterListener.hideList();
        presenterListener.showError(errorResId);
    }
}
