package com.rutger.uijtendaal.ikpmd.ui.moviedetails;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.rutger.uijtendaal.ikpmd.R;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

public class MovieDetailsActivity extends DaggerAppCompatActivity implements MovieDetailsNavigator {

    private static final String TAG = MovieDetailsActivity.class.getName();

    public static final String MOVIE_ID = "MOVIE_ID";

    @Inject
    Lazy<MovieDetailsFragment> mMoviesDetailFragmentProvider;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private MovieDetailsViewModel mMoviesDetailViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_act);

        mMoviesDetailViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MovieDetailsViewModel.class);
        mMoviesDetailViewModel.setNavigator(this);

        Bundle extras = getIntent().getExtras();
        String movieId = extras.getString(MOVIE_ID);

        Log.d(TAG, movieId);

        mMoviesDetailViewModel.setMovieById(movieId);

        setupActionBar();

        MovieDetailsFragment movieDetailsFragment = getFragment();

        movieDetailsFragment.setViewModel(mMoviesDetailViewModel);

    }

    @Override
    public void editMovie() {

    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }

    private MovieDetailsFragment getFragment() {
        MovieDetailsFragment movieDetailsFragment = (MovieDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (movieDetailsFragment == null) {
            movieDetailsFragment = mMoviesDetailFragmentProvider.get();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.contentFrame, movieDetailsFragment);
            fragmentTransaction.commit();
        }

        return movieDetailsFragment;
    }
}
