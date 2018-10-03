package com.rutger.uijtendaal.ikpmd.data.source;

import android.support.annotation.NonNull;

import com.rutger.uijtendaal.ikpmd.data.Movie;
import com.rutger.uijtendaal.ikpmd.data.source.local.MoviesDao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MoviesRepository implements MoviesDataSource {

    private final MoviesDataSource mMoviesLocalDataSource;

    @Inject
    MoviesRepository(MoviesDataSource moviesLocalDataSource) {
        mMoviesLocalDataSource = moviesLocalDataSource;
    }

    @Override
    public void getMovies(@NonNull LoadMoviesCallback callback) {

    }

    @Override
    public void getMovie(@NonNull String movieId, @NonNull GetMovieCallback callback) {

    }

    @Override
    public void saveMovie(@NonNull Movie movie) {

    }

    @Override
    public void deleteMovie(@NonNull String movieId) {

    }
}
