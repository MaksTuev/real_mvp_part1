package com.agna.realmvp.realmvpsample.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.agna.realmvp.realmvpsample.ui.screen.catalog.filter.FilterRoute;

import timber.log.Timber;

public class App extends Application {

    private AppComponent appComponent;

    public static AppComponent getAppComponent(Context context){
        return ((App) context.getApplicationContext()).getAppComponent();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initInjector();
        initLog();
        new FilterRoute(new Intent());
    }

    private void initLog() {
        Timber.plant(new Timber.DebugTree());
    }

    private void initInjector() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return this.appComponent;
    }
}
