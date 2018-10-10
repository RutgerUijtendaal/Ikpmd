package com.rutger.uijtendaal.ikpmd.data.source;

import android.arch.lifecycle.LiveData;

import com.rutger.uijtendaal.ikpmd.data.Movie;

import java.util.List;


/**
 *
 * Entry  point for accessing movie data
 *
 */
public interface MoviesDataSource {

    LiveData<List<Movie>> getMovies();

    LiveData<Movie> getMovie(String movieId);

    void saveMovie( Movie movie);

    void deleteMovie( String movieId);

    void deleteMovies();

}
