package com.interview.ammaryali.net;

import com.interview.ammaryali.model.ArticleResponse;
import com.interview.ammaryali.utils.Constants;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET(Constants.API_ARTICLE_SEARCH)
    Observable<ArticleResponse> getArticle(
            @Query(Constants.API_KEY) String key,
            @Query(Constants.NY_SORT_ORDER) String sortOrder,
            @Query(Constants.PAGE) int pageNumber,
            @Query(Constants.QUERY) String query);
}