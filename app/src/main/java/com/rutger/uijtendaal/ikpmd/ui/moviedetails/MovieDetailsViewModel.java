package com.rutger.uijtendaal.ikpmd.ui.moviedetails;

import android.content.Context;
import android.databinding.Observable;
import android.databinding.ObservableField;

import com.rutger.uijtendaal.ikpmd.data.source.MoviesRepository;
import com.rutger.uijtendaal.ikpmd.viewmodel.MovieViewModel;

import javax.inject.Inject;

public class MovieDetailsViewModel extends MovieViewModel {

    private static final String TAG = MovieDetailsViewModel.class.getName();

    public final ObservableField<Boolean> watchedIsChecked = new ObservableField<>();

    private MovieDetailsNavigator mNavigator;

    @Inject
    public MovieDetailsViewModel(Context context, MoviesRepository moviesRepository) {
        super(context, moviesRepository);
        setupWatchedToggleButton();
    }

    private void setupWatchedToggleButton() {
        watchedIsChecked.set(false);
        watchedIsChecked.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int propertyId) {
                boolean isWatched = watchedIsChecked.get();
                if(isWatched) {
                    timesWatched.set(timesWatched.get() + 1);
                } else {
                    timesWatched.set(timesWatched.get() - 1);
                }
                updateMovie();
            }
        });
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
