package com.rutger.uijtendaal.ikpmd.movies;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviesActivityModule {

    @Provides
    MoviesViewModel getMoviesViewModel(){
        return new MoviesViewModel();
    }
}
