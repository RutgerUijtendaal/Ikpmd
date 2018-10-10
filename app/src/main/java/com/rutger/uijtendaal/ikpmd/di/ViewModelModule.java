package com.rutger.uijtendaal.ikpmd.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.rutger.uijtendaal.ikpmd.ui.addmovie.AddMovieViewModel;
import com.rutger.uijtendaal.ikpmd.ui.movies.movieslist.MovieItemViewModel;
import com.rutger.uijtendaal.ikpmd.ui.movies.MoviesViewModel;
import com.rutger.uijtendaal.ikpmd.viewmodel.IkpmdViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel.class)
    abstract ViewModel bindMoviesViewModel(MoviesViewModel moviesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AddMovieViewModel.class)
    abstract ViewModel bindAddMovieViewModel(AddMovieViewModel addMovieViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MovieItemViewModel.class)
    abstract ViewModel bindMovieItemViewModel(MovieItemViewModel movieItemViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(IkpmdViewModelFactory factory);
}

