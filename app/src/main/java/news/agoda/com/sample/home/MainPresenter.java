package news.agoda.com.sample.home;

import android.support.annotation.NonNull;

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
        presenterListener.hideProgress();
        presenterListener.displayNewsList(parentEntity.getResults());
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
