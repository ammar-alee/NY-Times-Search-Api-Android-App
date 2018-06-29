package com.interview.ammaryali.presenter;

import com.interview.ammaryali.job.NetworkJob;
import com.interview.ammaryali.model.ArticleRequest;
import com.interview.ammaryali.model.ArticleResponse;
import com.interview.ammaryali.utils.Constants;
import com.interview.ammaryali.utils.Utils;
import com.interview.ammaryali.view.search.ArticleSearchView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SearchPresenter extends BasePresenter {

    private ArticleSearchView mView;

    public SearchPresenter(ArticleSearchView view, EventBus eventBus) {
        super(eventBus);
        mView = view;
    }

    public void getArticles(String query, int page) {
        if (!Utils.isNetworkAvailable(mView.getContext())) {
            mView.showError(Constants.NETWORK_NOT_AVAILABLE);
        } else {
            mView.loaderVisibility(true);
            ArticleRequest articleRequest = new ArticleRequest();
            articleRequest.setApiKey(Constants.KEY);
            articleRequest.setPage(page);
            articleRequest.setQuery(query);
            articleRequest.setSortOrder(Constants.SORT_ORDER);
            NetworkJob networkJob = new NetworkJob(mEventBus, Constants.ARTICLE_SEARCH);
            networkJob.searchArticle(articleRequest);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkJobFinished(NetworkJob.FinishedEvent event) {
        if (event.isSuccess()) {
            switch (event.getAction()) {
                case Constants.ARTICLE_SEARCH:
                    ArticleResponse response = (ArticleResponse) event.getObject();
                    mView.articleList(response.getResponse().getDocs());
                    break;
            }
        } else {
            mView.showError(event.getErrorMessage());
        }
    }
}