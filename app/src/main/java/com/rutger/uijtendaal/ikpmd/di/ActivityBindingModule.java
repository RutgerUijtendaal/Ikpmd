package com.rutger.uijtendaal.ikpmd.di;

import com.rutger.uijtendaal.ikpmd.movies.MoviesActivity;
import com.rutger.uijtendaal.ikpmd.movies.MoviesActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = MoviesActivityModule.class)
    abstract MoviesActivity moviesActivity();
}
