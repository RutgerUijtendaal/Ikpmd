package com.rutger.uijtendaal.ikpmd.ui.movies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.rutger.uijtendaal.ikpmd.data.Movie;
import com.rutger.uijtendaal.ikpmd.data.source.MoviesRepository;

import java.util.List;

import javax.inject.Inject;


public class MoviesViewModel extends ViewModel {

    private static final String TAG = MoviesViewModel.class.getName();

    private final LiveData<List<Movie>> mMovies;

    private final MoviesRepository mMoviesRepository;

    @Inject
    public MoviesViewModel(MoviesRepository moviesRepository) {
        this.mMoviesRepository = moviesRepository;
        mMovies = mMoviesRepository.getMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return mMovies;
    }

}
