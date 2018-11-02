package com.rutger.uijtendaal.ikpmd.data.source;

import com.rutger.uijtendaal.ikpmd.data.source.Remote.MoviesRemoteFb;
import com.rutger.uijtendaal.ikpmd.data.Movie;
import com.rutger.uijtendaal.ikpmd.data.source.local.MoviesDao;
import com.rutger.uijtendaal.ikpmd.data.source.local.MoviesLocalDs;
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

    private final MoviesRemoteFb mMoviesRemoteFb;
    private final MoviesLocalDs mMoviesLocalDs;

    @Inject
    MoviesRepository(MoviesRemoteFb moviesRemoteFb, MoviesLocalDs moviesLocalDs) {
        mMoviesRemoteFb = moviesRemoteFb;
        mMoviesLocalDs = moviesLocalDs;
    }

    @Override
    public void getMovies(final LoadMoviesCallback callback) {
        mMoviesLocalDs.getMovies(new LoadMoviesCallback() {
            @Override
            public void onMoviesLoaded(List<Movie> movies) {
                callback.onMoviesLoaded(movies);
            }
        });
    }

    @Override
    public void getMovie(String movieId, final GetMovieCallback callback) {
        mMoviesLocalDs.getMovie(movieId, new GetMovieCallback() {
            @Override
            public void getMovieCallback(Movie movie) {
                callback.getMovieCallback(movie);
            }
        });
    }

    @Override
    public void saveMovie(Movie movie) {
        mMoviesLocalDs.saveMovie(movie);
        mMoviesRemoteFb.insertMovie(movie);
    }

    @Override
    public void deleteMovie(String movieId) {
        mMoviesLocalDs.deleteMovie(movieId);
        mMoviesRemoteFb.deleteMovie(movieId);
    }

    @Override
    public void deleteMovies() {
        mMoviesLocalDs.deleteMovies();
        mMoviesRemoteFb.deleteMovies();
    }
}
