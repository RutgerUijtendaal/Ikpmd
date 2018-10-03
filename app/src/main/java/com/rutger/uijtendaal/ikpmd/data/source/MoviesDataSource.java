package com.rutger.uijtendaal.ikpmd.data.source;

import android.support.annotation.NonNull;

import com.rutger.uijtendaal.ikpmd.data.Movie;

import java.util.List;

/**
 *
 * Entry  point for accessing movie data
 *
 */
public interface MoviesDataSource {

    interface LoadMoviesCallback {

        void onMoviesLoaded(List<Movie> movies);

        void onDataNotAvailable();
    }

    interface GetMovieCallback {

        void onMovieLoaded(Movie movie);

        void onDataNotAvailable();
    }

    void getMovies(@NonNull LoadMoviesCallback callback);

    void getMovie(@NonNull String movieId, @NonNull GetMovieCallback callback);

    void saveMovie(@NonNull Movie movie);

    void deleteMovie(@NonNull String movieId);



}
