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
    @ColumnInfo(name = "notes")
    private final String mNotes;

    @Nullable
    @ColumnInfo(name="posterUrl")
    private final String mPosterUrl;

    @NonNull
    @ColumnInfo(name="timesWatched")
    private final int mTimesWatched;

    /**
     * Constructor to create a movie with a new UUID.
     *
     * @param title              title of the movie
     * @param rating            length of the movie
     * @param notes              notes of the movie
     * @param posterUrl     url of the movie poster
     * @param timesWatched  amount of times watched
     */
    @Ignore
    public Movie(@NonNull String title, @NonNull float rating, @Nullable String notes, @Nullable String posterUrl, @NonNull int timesWatched) {
        this(UUID.randomUUID().toString(), title, rating, notes, posterUrl, timesWatched);
    }

    public Movie(@NonNull String id, @NonNull String title, @NonNull float rating, @Nullable String notes, @Nullable String posterUrl, @NonNull int timesWatched) {
        mId = id;
        mTitle = title;
        mRating = rating;
        mNotes = notes;
        mPosterUrl = posterUrl;
        mTimesWatched = timesWatched;
    }

    @NonNull
    public String getId() { return mId; }

    @NonNull
    public String getTitle() { return mTitle; }

    @NonNull
    public float getRating() { return mRating; }

    @Nullable
    public String getNotes() {
        return mNotes;
    }

    @Nullable
    public String getPosterUrl() { return mPosterUrl; }

    @NonNull
    public int getTimesWatched() { return mTimesWatched; }

}
