package com.rutger.uijtendaal.ikpmd.ui.moviedetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.rutger.uijtendaal.ikpmd.R;
import com.rutger.uijtendaal.ikpmd.databinding.MovieDetailsFragBinding;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class MovieDetailsFragment extends DaggerFragment {

    private static final String TAG = MovieDetailsFragment.class.getName();

    private MovieDetailsFragBinding mMovieDetailsFragBinding;

    private MovieDetailsViewModel mMovieDetailsViewModel;

    @Inject
    public MovieDetailsFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mMovieDetailsViewModel.refresh();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_movie_ab, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.movie_details_frag, container, false);
        if(mMovieDetailsFragBinding ==  null) {
            mMovieDetailsFragBinding = MovieDetailsFragBinding.bind(root);
        }

        mMovieDetailsFragBinding.setViewmodel(mMovieDetailsViewModel);
        mMovieDetailsFragBinding.setLifecycleOwner(this);

        setHasOptionsMenu(true);

        return mMovieDetailsFragBinding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_edit_movie:
                Log.d(TAG, "edit movie");
                mMovieDetailsViewModel.editMovie();
                return true;
            case R.id.action_delete_movie:
                Log.d(TAG, "delete movie");
                mMovieDetailsViewModel.deleteMovie();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setViewModel(MovieDetailsViewModel movieDetailsViewModel) {
        mMovieDetailsViewModel = movieDetailsViewModel;
    }
}
