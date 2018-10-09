package com.rutger.uijtendaal.ikpmd.ui.movies;

import com.rutger.uijtendaal.ikpmd.di.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MoviesActivityModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MoviesFragment moviesFragment();

}
