package com.rutger.uijtendaal.ikpmd.data.source;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.rutger.uijtendaal.ikpmd.data.source.Remote.MoviesFb;
import com.rutger.uijtendaal.ikpmd.data.Movie;
import com.rutger.uijtendaal.ikpmd.data.source.local.MoviesDao;
import com.rutger.uijtendaal.ikpmd.util.AppExecutors;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * MoviesRepository Class.
 *
 * Movie Data from different sources is combined here and exposed to the ViewModels.
 *
 */
@Singleton
public class MoviesRepository implements MoviesDataSource {

    private static final String TAG = MoviesRepository.class.getName();

    private final MoviesDao mMoviesDao;
    private final AppExecutors mAppExecutors;
    private final MoviesFb mMoviesFb;

    @Inject
    MoviesRepository(MoviesDao moviesDao, AppExecutors appExecutors, MoviesFb moviesFb) {
        mMoviesDao = moviesDao;
        mAppExecutors = appExecutors;
        mMoviesFb = moviesFb;
    }

    @Override
    public LiveData<List<Movie>> getMovies() {
        return mMoviesDao.getMovies();
    }

    @Override
    public LiveData<Movie> getMovie(String movieId) {
        return mMoviesDao.getMovieById(movieId);
    }

    @Override
    public void saveMovie(Movie movie) {
        mAppExecutors.diskIO().execute(() ->
                mMoviesDao.insertMovie(movie)
        );
        mMoviesFb.insertMovie(movie);
    }

    @Override
    public void deleteMovie(String movieId) {
        mAppExecutors.diskIO().execute(() ->
                mMoviesDao.deleteMovieById(movieId)
        );
        mMoviesFb.deleteMovie(movieId);
    }

    @Override
    public void deleteMovies() {
        mAppExecutors.diskIO().execute(mMoviesDao::deleteMovies);
        mMoviesFb.deleteMovies();
    }
}
