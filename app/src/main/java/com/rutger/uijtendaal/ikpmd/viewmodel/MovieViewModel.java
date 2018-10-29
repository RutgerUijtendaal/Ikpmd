package com.rutger.uijtendaal.ikpmd.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.Observable;
import android.databinding.ObservableField;

import com.rutger.uijtendaal.ikpmd.data.Movie;
import com.rutger.uijtendaal.ikpmd.data.source.MoviesDataSource;
import com.rutger.uijtendaal.ikpmd.data.source.MoviesRepository;

public abstract class MovieViewModel extends ViewModel {

    public final ObservableField<String> title = new ObservableField<>();

    public final ObservableField<Float> rating = new ObservableField<>();

    public final ObservableField<String> notes = new ObservableField<>();

    public final ObservableField<String> posterUrl = new ObservableField<>();

    protected final ObservableField<Movie> mMovieObservable = new ObservableField<>();

    protected final MoviesRepository mMoviesRepository;

    protected final Context mContext;

    public MovieViewModel(Context context, MoviesRepository moviesRepository) {
        mMoviesRepository = moviesRepository;
        mContext = context;

        mMovieObservable.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Movie movie = mMovieObservable.get();
                if(movie != null) {
                    title.set(movie.getTitle());
                    rating.set(movie.getRating());
                    notes.set(movie.getNotes());
                    posterUrl.set(movie.getPosterUrl());

                } else {
                    title.set("No data");
                    rating.set(3f);
                    notes.set("");
                    posterUrl.set("");
                }
            }
        });
    }

    public void setMovieById(String movieId) {
        mMoviesRepository.getMovie(movieId, new MoviesDataSource.GetMovieCallback() {
            @Override
            public void getMovieCallback(Movie movie) {
                mMovieObservable.set(movie);
            }
        });
    }

    protected String getMovieId() {
        if(mMovieObservable.get() == null) {
            return null;
        }
        return mMovieObservable.get().getId();
    }

}