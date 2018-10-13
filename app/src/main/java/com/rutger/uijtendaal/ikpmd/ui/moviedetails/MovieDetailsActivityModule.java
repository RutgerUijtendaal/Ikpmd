package com.rutger.uijtendaal.ikpmd.ui.moviedetails;

import com.rutger.uijtendaal.ikpmd.di.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MovieDetailsActivityModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MovieDetailsFragment movieDetailsFragment();
}
