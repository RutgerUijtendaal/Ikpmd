package com.rutger.uijtendaal.ikpmd.ui.movies.movieslist;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rutger.uijtendaal.ikpmd.R;
import com.rutger.uijtendaal.ikpmd.data.Movie;
import com.rutger.uijtendaal.ikpmd.data.source.MoviesRepository;
import com.rutger.uijtendaal.ikpmd.databinding.MovieItemBinding;
import com.rutger.uijtendaal.ikpmd.ui.movies.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private static final String TAG = MoviesAdapter.class.getName();

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private List<Movie> mMovies;
    private List<Movie> mMoviesCopy;

    private MovieItemNavigator mMovieItemNavigator;

    private Context mContext;

    private MoviesRepository mMoviesRepository;

    private LayoutInflater layoutInflater;

    @Inject
    public MoviesAdapter(Context context, MoviesRepository moviesRepository) {
        mContext = context;
        mMoviesRepository = moviesRepository;
        setList(new ArrayList<Movie>(0));
    }

    public void setNavigator(MovieItemNavigator movieItemNavigator) {
        mMovieItemNavigator = movieItemNavigator;
    }

    private void setList(List<Movie> movies) {
        mMovies = movies;
        //Create a copy of the list for filtering
        mMoviesCopy = new ArrayList<>(movies);
    }

    public void replaceData(List<Movie> movies) {
        setList(movies);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        MovieItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.movie_item, viewGroup, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Movie movie = mMovies.get(i);

        final MovieItemViewModel viewModel = new MovieItemViewModel(mContext, mMoviesRepository);

        viewModel.setMovieById(movie.getId());
        viewModel.setNavigator(mMovieItemNavigator);

        viewHolder.bind(viewModel);
    }

    @Override
    public int getItemCount() {
        return mMovies != null ? mMovies.size() : 0;
    }

    public void search(String text) {
        mMovies.clear();
        if(text.isEmpty()) {
            mMovies.addAll(mMoviesCopy);
        } else {
            text = text.toLowerCase();
            for(Movie movie: mMoviesCopy) {
                if(movie.getTitle().toLowerCase().contains(text)){
                    mMovies.add(movie);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final MovieItemBinding mBinding;

        public void bind(MovieItemViewModel MovieItemViewModel) {
            mBinding.setViewmodel(MovieItemViewModel);
            mBinding.executePendingBindings();
        }
        public ViewHolder(MovieItemBinding movieItemBinding) {
            super(movieItemBinding.getRoot());
            this.mBinding = movieItemBinding;
        }
    }
}
