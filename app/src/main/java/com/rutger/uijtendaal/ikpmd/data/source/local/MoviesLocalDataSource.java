package com.rutger.uijtendaal.ikpmd.data.source.local;

import android.support.annotation.NonNull;

import com.rutger.uijtendaal.ikpmd.data.Movie;
import com.rutger.uijtendaal.ikpmd.data.source.MoviesDataSource;
import com.rutger.uijtendaal.ikpmd.util.AppExecutors;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MoviesLocalDataSource implements MoviesDataSource {

    private final MoviesDao mMoviesDao;

    private final AppExecutors mAppExecutors;

    @Inject
    public MoviesLocalDataSource(@NonNull AppExecutors executors, @NonNull MoviesDao moviesDao) {
        mMoviesDao = moviesDao;
        mAppExecutors = executors;
    }

    @Override
    public void getMovies(@NonNull final LoadMoviesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Movie> movies = mMoviesDao.getMovies();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(movies.isEmpty()) {
                            callback.onDataNotAvailable();
                        } else {
                            callback.onMoviesLoaded(movies);
                        }
                    }
                });
            }

        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getMovie(@NonNull final String movieId, @NonNull final GetMovieCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Movie movie = mMoviesDao.getMovieById(movieId);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(movie != null) {
                            callback.onMovieLoaded(movie);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }

        };
        mAppExecutors.diskIO().execute(runnable);

    }

    @Override
    public void saveMovie(@NonNull final Movie movie) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mMoviesDao.insertMovie(movie);
            }
        };

        mAppExecutors.diskIO().execute(runnable);

    }

    @Override
    public void deleteMovie(@NonNull final String movieId) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mMoviesDao.deleteMovieById(movieId);
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }
}
