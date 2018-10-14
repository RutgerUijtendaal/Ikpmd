package com.rutger.uijtendaal.ikpmd.ui.movies;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.rutger.uijtendaal.ikpmd.data.Movie;
import com.rutger.uijtendaal.ikpmd.ui.movies.movieslist.MoviesAdapter;

import java.util.List;

public class MoviesListBindings {

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:movies")
    public static void setMovies(RecyclerView recyclerView, List<Movie> movies) {
        MoviesAdapter adapter = (MoviesAdapter) recyclerView.getAdapter();
        if (adapter != null)
        {
            adapter.replaceData(movies);
        }
    }
}
