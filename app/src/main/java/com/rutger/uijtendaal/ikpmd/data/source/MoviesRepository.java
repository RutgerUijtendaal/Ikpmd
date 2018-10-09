package com.rutger.uijtendaal.ikpmd.data.source;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rutger.uijtendaal.ikpmd.util.MediatorResource;
import com.rutger.uijtendaal.ikpmd.data.Movie;
import com.rutger.uijtendaal.ikpmd.data.source.local.MoviesDao;
import com.rutger.uijtendaal.ikpmd.util.AppExecutors;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MoviesRepository implements MoviesDataSource {

    private static final String TAG = MoviesRepository.class.getName();

    private final MoviesDao mMoviesDao;
    private final AppExecutors mAppExecutors;
    private DatabaseReference fbRef;

    @Inject
    MoviesRepository(@NonNull MoviesDao moviesDao, @NonNull AppExecutors appExecutors) {
        this.mMoviesDao = moviesDao;
        this.mAppExecutors = appExecutors;
        fbRef = FirebaseDatabase.getInstance().getReference("movies");
        deleteMovies();
        Log.d(TAG, "MoviesRepository created");
    }

    @Override
    public LiveData<List<Movie>> getMovies() {
        return new MediatorResource<List<Movie>>(mAppExecutors) {

            @NonNull
            @Override
            protected void saveFbResult(@NonNull DataSnapshot dataSnapshot) {
                mMoviesDao.insertMovie(snapShotToMovie(dataSnapshot));
            }

            @NonNull
            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                return mMoviesDao.getMovies();
            }

            @NonNull
            @Override
            protected DatabaseReference getFbRef() {
                return fbRef;
            }
        }.asLiveData();

    }

    @Override
    public LiveData<Movie> getMovie(String movieId) {
        return new MediatorResource<Movie>(mAppExecutors) {

            @NonNull
            @Override
            protected void saveFbResult(DataSnapshot dataSnapshot) {
                mMoviesDao.insertMovie(snapShotToMovie(dataSnapshot));
            }

            @NonNull
            @Override
            protected LiveData<Movie> loadFromDb() {
                return mMoviesDao.getMovieById(movieId);
            }

            @NonNull
            @Override
            protected DatabaseReference getFbRef() {
                return fbRef;
            }
        }.asLiveData();
    }

    @Override
    public void saveMovie(Movie movie) {
        Log.d(TAG, "movie added");
        mAppExecutors.diskIO().execute(() ->
            mMoviesDao.insertMovie(movie)
        );
        fbRef.child(movie.getId()).setValue(movie);
    }

    @Override
    public void deleteMovie(String movieId) {
        mAppExecutors.diskIO().execute(() ->
                mMoviesDao.deleteMovieById(movieId)
        );
        fbRef.child(movieId).removeValue();
    }

    @Override
    public void deleteMovies() {
        Log.d(TAG, "movies deleted");
        mAppExecutors.diskIO().execute(() ->
                mMoviesDao.deleteMovies()
        );
        fbRef.removeValue();
    }


    private Movie snapShotToMovie(DataSnapshot dataSnapshot) {
        String id = dataSnapshot.child("id").getValue(String.class);
        String title = dataSnapshot.child("title").getValue(String.class);
        int rating = dataSnapshot.child("rating").getValue(Integer.class);
        String thoughts = dataSnapshot.child("thoughts").getValue(String.class);
        Movie movie = new Movie(id, title, rating, thoughts);
        return movie;
    }
}
