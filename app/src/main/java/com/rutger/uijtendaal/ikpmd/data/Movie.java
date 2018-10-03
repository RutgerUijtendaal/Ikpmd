package com.rutger.uijtendaal.ikpmd.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

//import com.google.common.base.Objects;
//import com.google.common.base.Strings;

import java.util.UUID;

/**
 * Model class for Movie
 *
 */
@Entity(tableName = "movies")
public final class Movie {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private final String mId;

    @NonNull
    @ColumnInfo(name = "title")
    private final String mTitle;

    @NonNull
    @ColumnInfo(name = "runtime")
    private final int mRuntime;

    /**
     *
     * Constructor to create a new movie
     *
     * @param title         title of the movie
     * @param runtime       length of the movie
     */
    @Ignore
    public Movie(@NonNull String title, @NonNull int runtime) {
        this(UUID.randomUUID().toString(), title, runtime);
    }

    /**
     *
     * Constructor to create a new movie
     *
     * @param title         title of the movie
     * @param runtime       length of the movie
     */
    public Movie(@NonNull String id, @NonNull String title, @NonNull int runtime) {
        mId = id;
        mTitle = title;
        mRuntime = runtime;
    }

    @NonNull
    public String getId() { return mId; }

    @NonNull
    public String getTitle() { return mTitle; }

    @NonNull
    public int getRuntime() { return mRuntime; }
}
