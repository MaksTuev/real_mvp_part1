package com.agna.realmvp.realmvpsample.ui.base.screen.activity;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.agna.ferro.mvp.view.activity.MvpActivityView;
import com.agna.realmvp.realmvpsample.ui.base.dagger.ActivityScreenModule;
import com.agna.realmvp.realmvpsample.ui.base.delegate.SupportScreenEventDelegation;
import com.agna.realmvp.realmvpsample.ui.base.delegate.manager.ActivityScreenEventDelegateManager;
import com.agna.realmvp.realmvpsample.ui.base.delegate.manager.ScreenEventDelegateManager;
import com.agna.realmvp.realmvpsample.ui.base.error.ErrorHandler;
import com.agna.realmvp.realmvpsample.ui.base.screen.HandlableErrorView;

import javax.inject.Inject;

/**
 * Base class for view, based on Activity
 * todo add delegates from ferro instead inheritance
 */
public abstract class BaseActivityView extends MvpActivityView implements
        HandlableErrorView,
        SupportScreenEventDelegation {

    @Inject
    ErrorHandler errorHandler;

    private ActivityScreenEventDelegateManager delegateManager = new ActivityScreenEventDelegateManager();


    @NonNull
    protected ActivityScreenModule getActivityScreenModule() {
        return new ActivityScreenModule(getPersistentScreenScope());
    }

    @Override
    public ScreenEventDelegateManager getScreenEventDelegateManager() {
        return delegateManager;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        delegateManager.onNewIntent(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        delegateManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        delegateManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void handleError(Throwable error) {
        errorHandler.handleError(error);
    }
}
