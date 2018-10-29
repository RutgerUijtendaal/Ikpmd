package com.rutger.uijtendaal.ikpmd.data.source.local;

import com.rutger.uijtendaal.ikpmd.data.Movie;
import com.rutger.uijtendaal.ikpmd.data.source.MoviesDataSource;
import com.rutger.uijtendaal.ikpmd.util.AppExecutors;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Movies Local Data Source. Contains the CRUD operations for movies. Uses AppExecutor to execute
 * database operations on the IO thread.
 */
@Singleton
public class MoviesLocalDs implements MoviesDataSource {

    private MoviesDao mMoviesDao;

    private AppExecutors mAppExecutors;

    @Inject
    public MoviesLocalDs(AppExecutors appExecutors, MoviesDao moviesDao) {
        mAppExecutors = appExecutors;
        mMoviesDao = moviesDao;
    }


    @Override
    public void getMovies(LoadMoviesCallback callback) {
        Runnable runnable = () -> {
            final List<Movie> movies = mMoviesDao.getMovies();
            mAppExecutors.mainThread().execute(new Runnable() {
                @Override
                public void run() {
                    callback.onMoviesLoaded(movies);
                }
            });
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getMovie(String id, GetMovieCallback callback) {
        Runnable runnable = () -> {
            final Movie movie = mMoviesDao.getMovieById(id);
            mAppExecutors.mainThread().execute(new Runnable() {
                @Override
                public void run() {
                    callback.getMovieCallback(movie);
                }
            });
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveMovie(Movie movie) {
        mAppExecutors.diskIO().execute(() ->
                mMoviesDao.insertMovie(movie)
        );
    }

    @Override
    public void deleteMovie(String movieId) {
        mAppExecutors.diskIO().execute(() ->
                mMoviesDao.deleteMovieById(movieId)
        );
    }

    @Override
    public void deleteMovies() {
        mAppExecutors.diskIO().execute(mMoviesDao::deleteMovies);
    }
}
