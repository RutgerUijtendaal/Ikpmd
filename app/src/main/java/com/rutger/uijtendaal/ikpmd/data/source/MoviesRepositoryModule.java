package com.rutger.uijtendaal.ikpmd.data.source;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.rutger.uijtendaal.ikpmd.data.source.local.MoviesDao;
import com.rutger.uijtendaal.ikpmd.data.source.local.MoviesDatabase;
import com.rutger.uijtendaal.ikpmd.util.AppExecutors;
import com.rutger.uijtendaal.ikpmd.util.DiskIOThreadExecutor;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Used by Dagger to inject the required arguments into the {@link MoviesRepository}.
 */
@Module
abstract public class MoviesRepositoryModule {

    private static final int THREAD_COUNT = 3;

    @Singleton
    @Provides
    static MoviesDatabase getMoviesDatabase(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), MoviesDatabase.class, "Movies.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    static MoviesDao getMovieDao(MoviesDatabase db) {
        return db.moviesDao();
    }


    @Singleton
    @Provides
    static AppExecutors getAppExecutors() {
        return new AppExecutors(new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());
    }
}

