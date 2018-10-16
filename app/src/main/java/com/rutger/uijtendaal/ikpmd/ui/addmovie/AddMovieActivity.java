package com.rutger.uijtendaal.ikpmd.ui.addmovie;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.rutger.uijtendaal.ikpmd.R;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

public class AddMovieActivity extends DaggerAppCompatActivity implements AddMovieNavigator {

    private static final String TAG = AddMovieActivity.class.getName();

    public static final String MOVIE_ID = "MOVIE_ID";

    public static final int ADD_MOVIE_REQUEST = 1;

    public static final int EDIT_MOVIE_REQUEST = 2;

    @Inject
    Lazy<AddMovieFragment> mAddMovieFragmentProvider;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private AddMovieViewModel mAddMovieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_act);

        mAddMovieViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AddMovieViewModel.class);
        mAddMovieViewModel.setNavigator(this);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            mAddMovieViewModel.init(null);
            Log.d(TAG, "create movie");
            setTitle(R.string.title_activity_add_movie);
        } else {
            String movieId = extras.getString(MOVIE_ID);
            Log.d(TAG, "edit movie");
            mAddMovieViewModel.init(movieId);
            setTitle(R.string.title_activity_edit_movie);
        }

        setupActionBar();

        AddMovieFragment addMovieFragment = getFragment();

        addMovieFragment.setViewModel(mAddMovieViewModel);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
            addMovieFragment = mAddMovieFragmentProvider.get();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.contentFrame, addMovieFragment);
            fragmentTransaction.commit();
        }

        return addMovieFragment;
    }

    @Override
    public void onMovieSaved() {
        Log.d(TAG, "onMovieSaved");
        setResult(RESULT_OK);
        onBackPressed();
        finish();
    }
}
