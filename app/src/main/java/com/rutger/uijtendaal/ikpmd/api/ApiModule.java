package com.rutger.uijtendaal.ikpmd.api;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Dagger Module to provide API related dependencies
 *
 */
@Module
public class ApiModule {

    @Singleton
    @Provides
    OmdbService getOmdbService() {
        return new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OmdbService.class);
    }

}
