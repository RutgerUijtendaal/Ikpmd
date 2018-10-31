package com.rutger.uijtendaal.ikpmd.data.source.Remote;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rutger.uijtendaal.ikpmd.data.Movie;
import com.rutger.uijtendaal.ikpmd.data.source.local.MoviesDao;
import com.rutger.uijtendaal.ikpmd.util.AppExecutors;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Movies Remote Firebase Data Source. Allows for inserting and deleting. Because the local SQL
 * provides the source of truth for data in the app no Get methods are available for Firebase.
 * Instead an Event listener is created that listens to changes in the Fb database and syncs them
 * with local.
 *
 * On startup of the app the local database is synced with remote to ensure all data is up to date.
 *
 */
@Singleton
public class MoviesRemoteFb {

    private static final String TAG = MoviesRemoteFb.class.getName();

    DatabaseReference mFb;

    AppExecutors mAppExecutors;

    MoviesDao mMoviesDao;

    @Inject
    public MoviesRemoteFb(MoviesDao moviesDao, AppExecutors appExecutors) {
        mAppExecutors = appExecutors;
        mMoviesDao = moviesDao;
        mFb = FirebaseDatabase.getInstance().getReference("movies");
        syncFirebaseWithLocal();
        setEventListener();
    }

    public void insertMovie(Movie movie) {
        mFb.child(movie.getId()).setValue(movie);
    }

    public void deleteMovie(String movieId) {
        mFb.child(movieId).removeValue();
    }

    public void deleteMovies() {
        mFb.removeValue();
    }

    private void setEventListener() {
        mFb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                mAppExecutors.diskIO().execute(() ->
                        mMoviesDao.insertMovie(snapShotToMovie(dataSnapshot))
                );
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                mAppExecutors.diskIO().execute(() ->
                        mMoviesDao.insertMovie(snapShotToMovie(dataSnapshot))
                );            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Movie movie = snapShotToMovie(dataSnapshot);
                mAppExecutors.diskIO().execute(() ->
                        mMoviesDao.deleteMovieById(movie.getId())
                );
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
                mAppExecutors.diskIO().execute(() ->
                        mMoviesDao.insertMovie(snapShotToMovie(dataSnapshot))
                );            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void syncFirebaseWithLocal() {
        mFb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                syncData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void syncData(DataSnapshot dataSnapshot) {
        mAppExecutors.diskIO().execute(() ->{
                // TODO there is probably a more efficient way to sync the databases
                mMoviesDao.deleteMovies();
                for (DataSnapshot movieChild : dataSnapshot.getChildren()) {
                    mMoviesDao.insertMovie(snapShotToMovie(movieChild));
                }
            }
        );

    }

    /**
     * Map Firebase Data to a Room movie class.
     *
     * Can't map to the Room Movie class as an empty constructor is required, which Rooms does not
     * allow.
     *
     * @param dataSnapshot
     * @return Movie build from the snapshot
     */
    private Movie snapShotToMovie(DataSnapshot dataSnapshot) {
        String id = dataSnapshot.child("id").getValue(String.class);
        String title = dataSnapshot.child("title").getValue(String.class);
        float rating = dataSnapshot.child("rating").getValue(Float.class);
        String notes = dataSnapshot.child("notes").getValue(String.class);
        String posterUrl = dataSnapshot.child("posterUrl").getValue(String.class);
        int timesWatched = dataSnapshot.child("timesWatched").getValue(Integer.class);
        Movie movie = new Movie(id, title, rating, notes, posterUrl, timesWatched);
        return movie;
    }
}
