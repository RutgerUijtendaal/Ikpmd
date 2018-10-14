package com.rutger.uijtendaal.ikpmd.data.source.local;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.rutger.uijtendaal.ikpmd.data.Movie;

import java.util.List;

/**
 * Data access object for Movies table
 */
@Dao
public interface MoviesDao {

    /**
     *
     * Select all movies
     *
     * @return all movies
     */
    @Query("SELECT * FROM movies")
    List<Movie> getMovies();

    /**
     *
     * Select movie by id
     *
     * @param movieId   the movies id
     * @return the movie with the movieId
     */
    @Query("SELECT * FROM movies WHERE id = :movieId")
    Movie getMovieById(String movieId);

    /**
     * Insert a movie into the database. If it already exists ignore
     *
     * @param movie the movie to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    /**
     * Update a movie
     *
     * @param movie movie to update
     * @return number of movies updated
     */
    @Update
    int updateMovie(Movie movie);

    /**
     * Delete a movie by id
     *
     * @param movieId id of movie to delete
     * @return number of movies deleted.
     */
    @Query("DELETE FROM movies WHERE id = :movieId")
    int deleteMovieById(String movieId);

    /**
     * Delete all movies
     */
    @Query("DELETE FROM movies")
    void deleteMovies();

    /**
     *
     */
}
