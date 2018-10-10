package com.rutger.uijtendaal.ikpmd.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
    @ColumnInfo(name = "rating")
    private final float mRating;

    @Nullable
    @ColumnInfo(name = "thoughts")
    private final String mThoughts;

    /**
     * Constructor to create a movie with a new UUID.
     *
     * @param title         title of the movie
     * @param rating       length of the movie
     * @param thoughts   thoughts of the movie
     */
    @Ignore
    public Movie(@NonNull String title, @NonNull float rating, @Nullable String thoughts) {
        this(UUID.randomUUID().toString(), title, rating, thoughts);
    }

    /**
     * Constructor to create a new movie
     *
     * @param id               id of the movie
     * @param title         title of the movie
     * @param rating       length of the movie
     * @param thoughts   thoughts of the movie
     */
    public Movie(@NonNull String id, @NonNull String title, @NonNull float rating, @Nullable String thoughts) {
        mId = id;
        mTitle = title;
        mRating = rating;
        mThoughts = thoughts;
    }

    @NonNull
    public String getId() { return mId; }

    @NonNull
    public String getTitle() { return mTitle; }

    @NonNull
    public float getRating() { return mRating; }

    @Nullable
    public String getThoughts() {
        return mThoughts;
    }

}
