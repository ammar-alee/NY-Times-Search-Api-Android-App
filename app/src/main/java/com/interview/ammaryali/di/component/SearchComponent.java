package com.interview.ammaryali.di.component;

import com.interview.ammaryali.di.module.SearchModule;
import com.interview.ammaryali.view.search.SearchActivity;

import dagger.Component;

@Component(modules = SearchModule.class)
public interface SearchComponent {

    void inject(SearchActivity searchActivity);
}