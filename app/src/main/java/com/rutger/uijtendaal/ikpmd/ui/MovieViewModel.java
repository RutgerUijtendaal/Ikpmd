package com.rutger.uijtendaal.ikpmd.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.InverseMethod;
import android.databinding.Observable;
import android.databinding.ObservableField;

import com.rutger.uijtendaal.ikpmd.data.Movie;

public abstract class MovieViewModel extends ViewModel {

//    private final LiveData<String> title;
//
//    private final LiveData<Float> rating;
//
//    private final LiveData<String> thoughts;
//
//    public final MutableLiveData<Movie> mMovieObservable = new MutableLiveData<>();

    public final MutableLiveData<String> title = new MutableLiveData<>();

    public final MutableLiveData<Float> rating = new MutableLiveData();

    public final MutableLiveData<String> thoughts = new MutableLiveData<>();

    protected final ObservableField<Movie> mMovieObservable = new ObservableField<>();

    protected final Context mContext;

    public MovieViewModel(Context context) {
        mContext = context;
//
//        title = Transformations.map(mMovieObservable, movie -> movie.getTitle());
//        rating = Transformations.map(mMovieObservable, movie -> movie.getRating());
//        thoughts = Transformations.map(mMovieObservable, movie -> movie.getThoughts());

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

    protected String getMovieId() {
        return mMovieObservable.get().getId();
    }

}
