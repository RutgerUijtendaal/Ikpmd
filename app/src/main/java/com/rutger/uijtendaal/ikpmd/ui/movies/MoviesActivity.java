package com.rutger.uijtendaal.ikpmd.ui.movies;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.rutger.uijtendaal.ikpmd.R;
import com.rutger.uijtendaal.ikpmd.ui.addmovie.AddMovieActivity;
import com.rutger.uijtendaal.ikpmd.ui.moviedetails.MovieDetailsActivity;
import com.rutger.uijtendaal.ikpmd.ui.movies.movieslist.MovieItemNavigator;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

public class MoviesActivity extends DaggerAppCompatActivity implements MoviesNavigator, MovieItemNavigator {

    private static final String TAG = MoviesActivity.class.getName();

    @Inject
    Lazy<MoviesFragment> moviesFragmentProvider;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    MoviesFragment mMoviesFragment;

    private MoviesViewModel mMoviesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_act);

        mMoviesViewModel = ViewModelProviders.of(this, viewModelFactory).get(MoviesViewModel.class);
        mMoviesViewModel.setNavigator(this);

        setupActionBar();

        mMoviesFragment = getFragment();

        mMoviesFragment.setViewModel(mMoviesViewModel);

    }

    @Override
    public void openMovieDetails(String movieId) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.MOVIE_ID, movieId);
        startActivity(intent);
    }

    @Override
    public void addNewMovie() {
        Intent intent = new Intent(this, AddMovieActivity.class);
        startActivity(intent);
    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private MoviesFragment getFragment() {
        MoviesFragment moviesFragment = (MoviesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (moviesFragment == null) {
            moviesFragment = moviesFragmentProvider.get();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.contentFrame, moviesFragment);
            fragmentTransaction.commit();
        }

        return moviesFragment;
    }

}
