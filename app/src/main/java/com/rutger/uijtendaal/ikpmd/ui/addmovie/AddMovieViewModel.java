package com.rutger.uijtendaal.ikpmd.ui.addmovie;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.google.common.base.Strings;
import com.rutger.uijtendaal.ikpmd.R;
import com.rutger.uijtendaal.ikpmd.data.Movie;
import com.rutger.uijtendaal.ikpmd.data.source.MoviesRepository;
import com.rutger.uijtendaal.ikpmd.viewmodel.MovieViewModel;
import com.rutger.uijtendaal.ikpmd.ui.movies.MoviesViewModel;

import javax.inject.Inject;


public class AddMovieViewModel extends MovieViewModel {

    private static final String TAG = MoviesViewModel.class.getName();

    public final ObservableField<String> toastText = new ObservableField<>();

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
            toastText.set(mContext.getString(R.string.empty_movie_title));
            return;
        }

        if(mIsEditTask) {
            Movie newMovie = new Movie(getMovieId(), title.get(), rating.get(), notes.get(), posterUrl.get());
            mMoviesRepository.saveMovie(newMovie);
            toastText.set("Movie Updated");
            mAddMovieNavigator.onMovieSaved();
            return;
        }

        Movie movie = new Movie(title.get(), rating.get(), notes.get(), posterUrl.get());
        mMoviesRepository.saveMovie(movie);
        toastText.set("Movie Added");
        mAddMovieNavigator.onMovieSaved();

    }

    public String getToastText() {
        return toastText.get();
    }

    public void setPosterUrl(String s) { posterUrl.set(s);}

    public void setTitle(String s) {
        title.set(s);
    }

}
