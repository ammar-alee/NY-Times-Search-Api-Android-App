package com.interview.ammaryali.view.search;

import android.content.Context;

import com.interview.ammaryali.model.Article;

import java.util.List;

public interface ArticleSearchView {

    Context getContext();

    void articleList(List<Article> fetchedArticles);

    void showError(String message);

    void loaderVisibility(boolean visibility);
}
