package com.rutger.uijtendaal.ikpmd.ui.movies.movieslist;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.Observable;
import android.databinding.ObservableField;

import com.rutger.uijtendaal.ikpmd.data.Movie;

import java.lang.ref.WeakReference;

import javax.annotation.Nullable;
import javax.inject.Inject;

public class MovieItemViewModel extends ViewModel {

    public final MutableLiveData<String> title = new MutableLiveData<>();

    public final MutableLiveData<Float> rating = new MutableLiveData();

    public final MutableLiveData<String> thoughts = new MutableLiveData<>();

    private final ObservableField<Movie> mMovieObservable = new ObservableField<>();

    private final Context mContext;

    @Nullable
    private WeakReference<MovieItemNavigator> mNavigator;

    @Inject
    public MovieItemViewModel(Context context) {
        mContext = context;

        mMovieObservable.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Movie movie = mMovieObservable.get();
                if(movie != null) {
                    title.setValue(movie.getTitle());
                    rating.setValue(movie.getRating());
                    thoughts.setValue(movie.getThoughts());
                } else {
                    title.setValue("No data");
                    rating.setValue(3f);
                    thoughts.setValue("");
                }
            }
        });
    }

    public void setMovie(Movie movie) {
        mMovieObservable.set(movie);
    }

    public void setNavigator(MovieItemNavigator navigator) {
        mNavigator = new WeakReference<>(navigator);
    }

    public void movieClicked() {
        String movieId = getMovieId();
        if( movieId == null) {
            return;
        }

        if(mNavigator != null && mNavigator.get() != null) {
            mNavigator.get().openMovieItem(movieId);
        }
    }

    private String getMovieId() {
        return mMovieObservable.get().getId();
    }
}


