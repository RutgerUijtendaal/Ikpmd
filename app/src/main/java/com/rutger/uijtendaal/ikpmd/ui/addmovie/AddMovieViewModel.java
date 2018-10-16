package com.rutger.uijtendaal.ikpmd.ui.addmovie;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.google.common.base.Strings;
import com.rutger.uijtendaal.ikpmd.R;
import com.rutger.uijtendaal.ikpmd.api.OmdbService;
import com.rutger.uijtendaal.ikpmd.data.Movie;
import com.rutger.uijtendaal.ikpmd.data.source.MoviesRepository;
import com.rutger.uijtendaal.ikpmd.ui.MovieViewModel;
import com.rutger.uijtendaal.ikpmd.ui.movies.MoviesViewModel;

import javax.inject.Inject;


public class AddMovieViewModel extends MovieViewModel {

    private static final String TAG = MoviesViewModel.class.getName();

    public final MutableLiveData<String> snackbarText = new MutableLiveData<>();

    private AddMovieNavigator mAddMovieNavigator;

    private boolean mIsEditTask;

    @Inject
    public AddMovieViewModel(Context context, MoviesRepository moviesRepository) {
        super(context, moviesRepository);
    }

    public void setNavigator(AddMovieNavigator navigator) { mAddMovieNavigator = navigator; }

    public void init(String taskId) {
        if(taskId == null) {
            mIsEditTask = false;
            rating.set(3f);
        } else {
            mIsEditTask = true;
            setMovieById(taskId);
        }
    }

    public void saveMovie() {
        if(Strings.isNullOrEmpty(title.get())) {
            snackbarText.postValue(mContext.getString(R.string.empty_movie_title));
            return;
        }

        if(mIsEditTask) {
            Movie newMovie = new Movie(getMovieId(), title.get(), rating.get(), notes.get());
            mMoviesRepository.saveMovie(newMovie);
            Log.d(TAG, "edit navigate");
            mAddMovieNavigator.onMovieSaved();
            return;
        }

        Movie movie = new Movie(title.get(), rating.get(), notes.get());
        mMoviesRepository.saveMovie(movie);
        Log.d(TAG, "added navigate");
        mAddMovieNavigator.onMovieSaved();

    }

    public void setTitle(String s) {
        title.set(s);
    }

}
