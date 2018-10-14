package com.rutger.uijtendaal.ikpmd.ui.moviedetails;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.rutger.uijtendaal.ikpmd.data.Movie;
import com.rutger.uijtendaal.ikpmd.data.source.MoviesRepository;
import com.rutger.uijtendaal.ikpmd.ui.MovieViewModel;

import javax.inject.Inject;

public class MovieDetailsViewModel extends MovieViewModel {

    private static final String TAG = MovieDetailsViewModel.class.getName();

    private MovieDetailsNavigator mNavigator;

    @Inject
    public MovieDetailsViewModel(Context context, MoviesRepository moviesRepository) {
        super(context, moviesRepository);
    }

    public void setNavigator(MovieDetailsNavigator navigator) {
        mNavigator = navigator;
    }

    public void editMovie() {
        if(mNavigator != null) {
            mNavigator.editMovie();
        }
    }
}
