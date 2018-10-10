package com.rutger.uijtendaal.ikpmd.ui.addmovie;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.google.common.base.Strings;
import com.rutger.uijtendaal.ikpmd.R;
import com.rutger.uijtendaal.ikpmd.data.Movie;
import com.rutger.uijtendaal.ikpmd.data.source.MoviesRepository;
import com.rutger.uijtendaal.ikpmd.ui.movies.MoviesViewModel;


import javax.inject.Inject;

public class AddMovieViewModel extends ViewModel {

    private static final String TAG = MoviesViewModel.class.getName();

    public final MutableLiveData<String> title = new MutableLiveData<>();

    public final MutableLiveData<Float> rating = new MutableLiveData();

    public final MutableLiveData<String> thoughts = new MutableLiveData<>();

    public final MutableLiveData<String> snackbarText = new MutableLiveData<>();

    private final Context mContext;

    private final MoviesRepository mMoviesRepository;

    private AddMovieNavigator mAddMovieNavigator;

    @Inject
    public AddMovieViewModel(Context context, MoviesRepository moviesRepository) {
        mMoviesRepository = moviesRepository;
        mContext = context;
        rating.setValue(3f);
    }

    public void setNavigator(AddMovieNavigator navigator) { mAddMovieNavigator = navigator; }

    public void createMovie() {
        if(Strings.isNullOrEmpty(title.getValue())) {
            snackbarText.postValue(mContext.getString(R.string.empty_movie_title));
        } else {
            Movie movie = new Movie(title.getValue(), rating.getValue(), thoughts.getValue());
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
