package com.rutger.uijtendaal.ikpmd.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.rutger.uijtendaal.ikpmd.data.Movie;

/**
 * Room Database containing Movies Table
 */
@Database(entities = {Movie.class}, version = 6, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {

    public abstract MoviesDao moviesDao();
}
