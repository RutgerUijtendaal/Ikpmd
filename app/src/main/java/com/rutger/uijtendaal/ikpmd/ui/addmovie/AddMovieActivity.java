package com.rutger.uijtendaal.ikpmd.ui.addmovie;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rutger.uijtendaal.ikpmd.R;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

public class AddMovieActivity extends DaggerAppCompatActivity implements AddMovieNavigator {

    @Inject
    Lazy<AddMovieFragment> addMovieFragmentProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_act);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white);

        AddMovieFragment addMovieFragment = (AddMovieFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (addMovieFragment == null) {
            addMovieFragment = addMovieFragmentProvider.get();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.contentFrame, addMovieFragment);
            fragmentTransaction.commit();
        }

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
}
