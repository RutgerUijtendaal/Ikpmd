package com.rutger.uijtendaal.ikpmd.ui.movies.movieslist;

import android.content.Context;

import com.rutger.uijtendaal.ikpmd.data.source.MoviesRepository;
import com.rutger.uijtendaal.ikpmd.ui.MovieViewModel;

import java.lang.ref.WeakReference;

import javax.annotation.Nullable;
import javax.inject.Inject;

public class MovieItemViewModel extends MovieViewModel {

    @Nullable
    private WeakReference<MovieItemNavigator> mNavigator;

    @Inject
    public MovieItemViewModel(Context context, MoviesRepository moviesRepository) {
        super(context, moviesRepository);
    }

    public void setNavigator(MovieItemNavigator navigator) {
        mNavigator = new WeakReference<>(navigator);
    }

    public void movieClicked() {
        String movieId = getMovieId();
        if( movieId == null) {
            return;
        }

        if(mNavigator != null && mNavigator.get() != null) {
            mNavigator.get().openMovieDetails(movieId);
        }
    }
}

