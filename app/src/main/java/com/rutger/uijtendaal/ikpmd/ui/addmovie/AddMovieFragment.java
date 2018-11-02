package com.rutger.uijtendaal.ikpmd.ui.addmovie;

import android.databinding.Observable;
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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.rutger.uijtendaal.ikpmd.R;
import com.rutger.uijtendaal.ikpmd.api.OmdbMovie;
import com.rutger.uijtendaal.ikpmd.databinding.AddMovieFragBinding;
import com.rutger.uijtendaal.ikpmd.ui.addmovie.suggestions.MoviesAutoCompleteAdapter;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class  AddMovieFragment extends DaggerFragment {

    private static final String TAG = AddMovieFragment.class.getName();

    private AddMovieFragBinding mAddMovieFragBinding;

    private AddMovieViewModel mAddMovieViewModel;

    private Observable.OnPropertyChangedCallback mToastCallback;

    @Inject
    MoviesAutoCompleteAdapter moviesAutoCompleteAdapter;

    @Inject
    public AddMovieFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_movie_ab, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_movie_frag, container, false);
        if (mAddMovieFragBinding == null) {
            mAddMovieFragBinding = AddMovieFragBinding.bind(root);
        }

        mAddMovieFragBinding.setViewmodel(mAddMovieViewModel);
        mAddMovieFragBinding.setLifecycleOwner(this);

        AutoCompleteTextView movieTitle = (AutoCompleteTextView) root.findViewById(R.id.add_movie_title);
        movieTitle.setAdapter(moviesAutoCompleteAdapter);
        movieTitle.setOnItemClickListener(onItemClickListener);

        // Set action bar options
        setHasOptionsMenu(true);

        setupToast();

        return mAddMovieFragBinding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add_movie:
                mAddMovieViewModel.saveMovie();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupToast() {
        mToastCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Toast toast = Toast.makeText(getView().getContext(),
                        mAddMovieViewModel.getToastText(), Toast.LENGTH_SHORT);
                toast.show();
            }
        };
        mAddMovieViewModel.toastText.addOnPropertyChangedCallback(mToastCallback);
    }

    public void setViewModel(AddMovieViewModel addMovieViewModel) {
        this.mAddMovieViewModel = addMovieViewModel;
    }

    private AdapterView.OnItemClickListener onItemClickListener =
        new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String movieTitle = ((OmdbMovie) adapterView.getItemAtPosition(position)).getTitle();
                String posterUrl = ((OmdbMovie) adapterView.getItemAtPosition(position)).getPosterUrl();
                mAddMovieViewModel.setTitle(movieTitle);
                mAddMovieViewModel.setPosterUrl(posterUrl);
                mAddMovieViewModel.toastText.set(getString(R.string.toast_movie_selected, movieTitle));
            }
        };

}
