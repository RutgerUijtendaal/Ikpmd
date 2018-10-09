package com.rutger.uijtendaal.ikpmd.ui.addmovie;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rutger.uijtendaal.ikpmd.R;
import com.rutger.uijtendaal.ikpmd.databinding.AddMovieFragBinding;
import com.rutger.uijtendaal.ikpmd.ui.movies.MoviesViewModel;

import org.w3c.dom.Text;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class  AddMovieFragment extends DaggerFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private AddMovieFragBinding mAddMovieFragBinding;

    private AddMovieViewModel mAddMovieViewModel;

    @Inject
    public AddMovieFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_movie_ab, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddMovieViewModel = ViewModelProviders.of(this, viewModelFactory).get(AddMovieViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_movie_frag, container, false);
        if (mAddMovieFragBinding == null) {
            mAddMovieFragBinding = AddMovieFragBinding.bind(root);
        }

        mAddMovieFragBinding.setViewmodel(mAddMovieViewModel);

        // Set action bar options
        setHasOptionsMenu(true);



        return mAddMovieFragBinding.getRoot();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add_movie:
                mAddMovieViewModel.createMovie();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}