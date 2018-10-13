package com.rutger.uijtendaal.ikpmd.di;

import com.rutger.uijtendaal.ikpmd.ui.addmovie.AddMovieActivity;
import com.rutger.uijtendaal.ikpmd.ui.addmovie.AddMovieActivityModule;
import com.rutger.uijtendaal.ikpmd.ui.moviedetails.MovieDetailsActivity;
import com.rutger.uijtendaal.ikpmd.ui.moviedetails.MovieDetailsActivityModule;
import com.rutger.uijtendaal.ikpmd.ui.movies.MoviesActivity;
import com.rutger.uijtendaal.ikpmd.ui.movies.MoviesActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Dagger Module. Holds all the Activity modules and their subcomponents.
 *
 */
@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = MoviesActivityModule.class)
    abstract MoviesActivity moviesActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = AddMovieActivityModule.class)
    abstract AddMovieActivity addMovieActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = MovieDetailsActivityModule.class)
    abstract MovieDetailsActivity movieDetailsActivity();

}
