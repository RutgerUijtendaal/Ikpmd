package com.rutger.uijtendaal.ikpmd.ui.addmovie;

import com.rutger.uijtendaal.ikpmd.di.FragmentScoped;
import com.rutger.uijtendaal.ikpmd.ui.movies.MoviesFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AddMovieActivityModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AddMovieFragment addMovieFragment();
}
