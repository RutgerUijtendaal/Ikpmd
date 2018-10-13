package com.rutger.uijtendaal.ikpmd.ui.addmovie;

import android.content.Context;

import com.rutger.uijtendaal.ikpmd.api.OmdbService;
import com.rutger.uijtendaal.ikpmd.di.ActivityScoped;
import com.rutger.uijtendaal.ikpmd.di.FragmentScoped;
import com.rutger.uijtendaal.ikpmd.ui.addmovie.suggestions.MoviesAutoCompleteAdapter;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AddMovieActivityModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AddMovieFragment addMovieFragment();

    @Provides
    @ActivityScoped
    static MoviesAutoCompleteAdapter getMoviesAutoCompleteAdapter(Context context, OmdbService omdbService) {
        return new MoviesAutoCompleteAdapter(context, omdbService);
    }
}
