package com.rutger.uijtendaal.ikpmd;

import com.rutger.uijtendaal.ikpmd.di.AppComponent;
import com.rutger.uijtendaal.ikpmd.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class IkpmdApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent =  DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);

        return appComponent;
    }
}
