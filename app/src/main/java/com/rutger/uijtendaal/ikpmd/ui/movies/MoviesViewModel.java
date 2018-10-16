package com.rutger.uijtendaal.ikpmd.ui.movies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.rutger.uijtendaal.ikpmd.data.Movie;
import com.rutger.uijtendaal.ikpmd.data.source.MoviesDataSource;
import com.rutger.uijtendaal.ikpmd.data.source.MoviesRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * ViewModel of the MoviesActivity.
 *
 */
public class MoviesViewModel extends ViewModel {

    private static final String TAG = MoviesViewModel.class.getName();

    public final MutableLiveData<List<Movie>> mMovies = new MutableLiveData<>();

    private final MoviesRepository mMoviesRepository;

    private MoviesNavigator mNavigator;

    @Inject
    public MoviesViewModel(MoviesRepository moviesRepository) {
        mMoviesRepository = moviesRepository;
        init();
    }

    public void init() {
        loadMovies();
    }

    // Set the Navigator Activity through the MoviesNavigator interface.
    public void setNavigator(MoviesNavigator navigator) { mNavigator = navigator; }

    // Called by the movies_act.xml through data binding.
    public void addNewMovie() {
        if (mNavigator != null) {
            mNavigator.addNewMovie();
        }
    }

    public void deleteMovies() {
        mMoviesRepository.deleteMovies();
    }

    private void loadMovies() {
        mMoviesRepository.getMovies(new MoviesDataSource.LoadMoviesCallback() {
            @Override
            public void onMoviesLoaded(List<Movie> movies) {
                mMovies.setValue(movies);
            }
        });
    }

}
