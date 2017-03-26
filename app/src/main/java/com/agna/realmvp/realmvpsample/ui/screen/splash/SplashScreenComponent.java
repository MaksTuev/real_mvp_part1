package com.agna.realmvp.realmvpsample.ui.screen.splash;

import com.agna.ferro.mvp.component.ScreenComponent;
import com.agna.ferro.mvp.component.scope.PerScreen;
import com.agna.realmvp.realmvpsample.app.AppComponent;
import com.agna.realmvp.realmvpsample.ui.base.dagger.ActivityScreenModule;
import com.agna.realmvp.realmvpsample.ui.screen.splash.dialog.StartDialog;

import dagger.Component;

@PerScreen
@Component(dependencies = AppComponent.class, modules = {ActivityScreenModule.class})
public interface SplashScreenComponent extends ScreenComponent<SplashActivityView> {
    void inject(StartDialog dialog);
}