package com.rutger.uijtendaal.ikpmd.ui.movies;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.rutger.uijtendaal.ikpmd.R;
import com.rutger.uijtendaal.ikpmd.data.Movie;
import com.rutger.uijtendaal.ikpmd.ui.addmovie.AddMovieActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class MoviesFragment extends DaggerFragment {

    private static final String TAG = MoviesFragment.class.getName();

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private MoviesViewModel mMoviesViewModel;
    private MoviesAdapter mMoviesAdapter;

    @Inject
    public MoviesFragment() {
        // Requires empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoviesAdapter = new MoviesAdapter(new ArrayList<Movie>());
        mMoviesViewModel = ViewModelProviders.of(this, viewModelFactory).get(MoviesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.movies_frag, container, false);


        // Set up movies view
        RecyclerView recyclerView = root.findViewById(R.id.movies_list);
        recyclerView.setAdapter(mMoviesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mMoviesViewModel.getMovies().observe(MoviesFragment.this, movies -> mMoviesAdapter.replaceData(movies));


        FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_movie);

        fab.setOnClickListener(v -> showAddTask());


        return root;

    }

    private void showAddTask() {
        Intent intent = new Intent(getContext(), AddMovieActivity.class);
        startActivity(intent);
    }

}
