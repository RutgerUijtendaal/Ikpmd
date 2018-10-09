package com.rutger.uijtendaal.ikpmd.ui.addmovie;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Log;

import com.google.common.base.Strings;
import com.rutger.uijtendaal.ikpmd.R;
import com.rutger.uijtendaal.ikpmd.data.Movie;
import com.rutger.uijtendaal.ikpmd.data.source.MoviesRepository;
import com.rutger.uijtendaal.ikpmd.ui.movies.MoviesViewModel;


import javax.inject.Inject;

public class AddMovieViewModel extends ViewModel {

    private static final String TAG = MoviesViewModel.class.getName();

    public final ObservableField<String> title = new ObservableField<>();

    public final ObservableInt rating = new ObservableInt();

    public final ObservableField<String> thoughts = new ObservableField<>();

    public final ObservableField<String> snackbarText = new ObservableField<>();

    private final Context mContext;

    private final MoviesRepository mMoviesRepository;

    private AddMovieNavigator mAddMovieNavigator;

    public void onActivityCreated(AddMovieNavigator navigator) {
        mAddMovieNavigator = navigator;
    }

    @Inject
    public AddMovieViewModel(Context context, MoviesRepository moviesRepository) {
        mMoviesRepository = moviesRepository;
        mContext = context;
        rating.set(3);
    }

    // Called when clicking the actionbar button
    public void createMovie() {
        if(Strings.isNullOrEmpty(title.get())) {
            snackbarText.set(mContext.getString(R.string.empty_movie_title));
        } else {
            Movie movie = new Movie(title.get(), rating.get(), thoughts.get());
            mMoviesRepository.saveMovie(movie);
            navigateOnMovieSaved();
        }
    }

    private void navigateOnMovieSaved() {
        if (mAddMovieNavigator!= null) {
            mAddMovieNavigator.onMovieSaved();
        }
    }
}
