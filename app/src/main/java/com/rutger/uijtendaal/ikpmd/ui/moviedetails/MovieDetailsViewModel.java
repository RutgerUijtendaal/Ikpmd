package com.rutger.uijtendaal.ikpmd.ui.moviedetails;

import android.content.Context;

import com.rutger.uijtendaal.ikpmd.data.source.MoviesRepository;
import com.rutger.uijtendaal.ikpmd.viewmodel.MovieViewModel;

import javax.inject.Inject;

public class MovieDetailsViewModel extends MovieViewModel {

    private static final String TAG = MovieDetailsViewModel.class.getName();

    private MovieDetailsNavigator mNavigator;

    @Inject
    public MovieDetailsViewModel(Context context, MoviesRepository moviesRepository) {
        super(context, moviesRepository);
    }

    public void refresh() {
        if(getMovieId() != null) {
            setMovieById(getMovieId());
        }
    }

    public void setNavigator(MovieDetailsNavigator navigator) {
        mNavigator = navigator;
    }

    public void deleteMovie() {
        mMoviesRepository.deleteMovie(getMovieId());
        if(mNavigator != null) {
            mNavigator.deleteMovie(getMovieId());
        }
    }

    public void editMovie() {
        if(mNavigator != null) {
            mNavigator.editMovie(getMovieId());
        }
    }
}
