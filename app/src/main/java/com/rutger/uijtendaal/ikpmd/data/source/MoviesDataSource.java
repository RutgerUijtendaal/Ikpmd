package com.rutger.uijtendaal.ikpmd.data.source;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.rutger.uijtendaal.ikpmd.data.Movie;

import java.util.List;


/**
 *
 * Entry  point for accessing movie data
 *
 */
public interface MoviesDataSource {

    LiveData<List<Movie>> getMovies();

    LiveData<Movie> getMovie(@NonNull String movieId);

    void saveMovie(@NonNull Movie movie);

    void deleteMovie(@NonNull String movieId);

    void deleteMovies();

}
