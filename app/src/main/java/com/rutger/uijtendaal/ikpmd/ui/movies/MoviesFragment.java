package com.rutger.uijtendaal.ikpmd.ui.movies;

import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.rutger.uijtendaal.ikpmd.R;
import com.rutger.uijtendaal.ikpmd.data.Movie;
import com.rutger.uijtendaal.ikpmd.ui.movies.movieslist.MoviesAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class MoviesFragment extends DaggerFragment implements SearchView.OnQueryTextListener {

    private static final String TAG = MoviesFragment.class.getName();

    private MoviesViewModel mMoviesViewModel;

    @Inject
    MoviesAdapter mMoviesAdapter;

    @Inject
    public MoviesFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mMoviesViewModel.init();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movies_ab, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.movies_frag, container, false);

        setupRecyclerViewAdapter(root);

        setupFab(root);

        // Set action bar options
        setHasOptionsMenu(true);

        return root;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.action_delete_movies:
                mMoviesViewModel.deleteMovies();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setViewModel(MoviesViewModel moviesViewModel) {
        this.mMoviesViewModel = moviesViewModel;
    }

    private void setupRecyclerViewAdapter(View root) {
        // Set up movies view
        mMoviesAdapter.setNavigator((MoviesActivity)getActivity());
        RecyclerView recyclerView = root.findViewById(R.id.movies_list);
        recyclerView.setAdapter(mMoviesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity().getApplicationContext(), 0);
        recyclerView.addItemDecoration(decoration);

        mMoviesViewModel.mMovies.observe(this, movies -> mMoviesAdapter.replaceData(movies));
    }

    private void setupFab(View root) {
        FloatingActionButton fab = root.findViewById(R.id.fab_add_movie);

        fab.setOnClickListener(v -> mMoviesViewModel.addNewMovie());
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        mMoviesAdapter.search(s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        mMoviesAdapter.search(s);
        return true;
    }
}
