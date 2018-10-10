package com.rutger.uijtendaal.ikpmd.ui.movies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.rutger.uijtendaal.ikpmd.data.Movie;
import com.rutger.uijtendaal.ikpmd.data.source.MoviesRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * ViewModel of the MoviesActivity.
 *
 */
public class MoviesViewModel extends ViewModel {

    private static final String TAG = MoviesViewModel.class.getName();

    private final LiveData<List<Movie>> mMovies;

    private final MoviesRepository mMoviesRepository;

    private MoviesNavigator mNavigator;

    @Inject
    public MoviesViewModel(MoviesRepository moviesRepository) {
        mMoviesRepository = moviesRepository;
        mMovies = mMoviesRepository.getMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return mMovies;
    }

    // Set the Navigator Activity through the MoviesNavigator interface.
    public void setNavigator(MoviesNavigator navigator) { mNavigator = navigator; }

    // Called by the movies_act.xml through data binding.
    public void addNewMovie() {
        if (mNavigator != null) {
            mNavigator.addNewMovie();
        }
    }
}
