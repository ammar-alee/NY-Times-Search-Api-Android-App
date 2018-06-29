package com.interview.ammaryali.job;

import android.util.Log;

import com.interview.ammaryali.model.ArticleRequest;
import com.interview.ammaryali.model.ArticleResponse;
import com.interview.ammaryali.net.RestClient;
import com.interview.ammaryali.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NetworkJob {

    public static final String TAG = NetworkJob.class.getSimpleName();
    private EventBus mEventBus;
    private String mAction;

    public NetworkJob(EventBus eventBus, String action) {
        mAction = action;
        mEventBus = eventBus;
    }

    public void searchArticle(ArticleRequest request) {
        RestClient.getInstance()
                .getArticle(request.getApiKey(), request.getSortOrder(), request.getPage(), request.getQuery())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArticleResponse response) {
                        if (response.getStatus().equalsIgnoreCase("ok")) {
                            mEventBus.post(new FinishedEvent(true, mAction, response, null));
                        } else {
                            mEventBus.post(new FinishedEvent(false, mAction, null,
                                    Constants.API_FAILED));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "In onError()");
                        mEventBus.post(new FinishedEvent(false, mAction, null, e.getMessage()));
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "In onComplete()");
                    }
                });
    }

    public static class FinishedEvent {

        private String mAction;
        private boolean mSuccess;
        private Object mObject;
        private String mErrorMessage;

        public FinishedEvent(boolean success, String action, Object object, String localizedMessage) {
            this.mAction = action;
            this.mSuccess = success;
            this.mObject = object;
            this.mErrorMessage = localizedMessage;
        }

        public String getAction() {
            return mAction;
        }

        public boolean isSuccess() {
            return mSuccess;
        }

        public Object getObject() {
            return mObject;
        }

        public String getErrorMessage() {
            return mErrorMessage;
        }
    }
}