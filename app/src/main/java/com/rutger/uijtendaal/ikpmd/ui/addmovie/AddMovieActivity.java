package com.rutger.uijtendaal.ikpmd.ui.addmovie;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rutger.uijtendaal.ikpmd.R;
import com.rutger.uijtendaal.ikpmd.ui.movies.MoviesActivity;
import com.rutger.uijtendaal.ikpmd.ui.movies.MoviesViewModel;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

public class AddMovieActivity extends DaggerAppCompatActivity implements AddMovieNavigator {

    private static final String TAG = AddMovieActivity.class.getName();

    public static final int ADD_MOVIE_REQUEST = 1;

    @Inject
    Lazy<AddMovieFragment> addMovieFragmentProvider;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private AddMovieViewModel mAddMovieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_act);

        mAddMovieViewModel = ViewModelProviders.of(this, viewModelFactory).get(AddMovieViewModel.class);
        mAddMovieViewModel.setNavigator(this);

        setupActionBar();

        AddMovieFragment addMovieFragment = getFragment();

        addMovieFragment.setViewModel(mAddMovieViewModel);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieSaved() {
        setResult(RESULT_OK);
        finish();
    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white);
    }

    private AddMovieFragment getFragment() {
        AddMovieFragment addMovieFragment = (AddMovieFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (addMovieFragment == null) {
            addMovieFragment = addMovieFragmentProvider.get();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.contentFrame, addMovieFragment);
            fragmentTransaction.commit();
        }

        return addMovieFragment;
    }
}
