package com.rutger.uijtendaal.ikpmd.di;


import android.app.Application;

import com.rutger.uijtendaal.ikpmd.IkpmdApplication;
import com.rutger.uijtendaal.ikpmd.data.source.MoviesRepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {MoviesRepositoryModule.class,
        ApplicationModule.class,
        ActivityBindingModule.class,
        ViewModelModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<IkpmdApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
