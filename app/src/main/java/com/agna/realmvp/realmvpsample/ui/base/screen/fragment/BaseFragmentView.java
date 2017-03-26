package com.agna.realmvp.realmvpsample.ui.base.screen.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.agna.ferro.mvp.view.fragment.MvpFragmentV4View;
import com.agna.realmvp.realmvpsample.ui.base.dagger.FragmentScreenModule;
import com.agna.realmvp.realmvpsample.ui.base.delegate.SupportScreenEventDelegation;
import com.agna.realmvp.realmvpsample.ui.base.delegate.manager.FragmentScreenEventDelegateManager;
import com.agna.realmvp.realmvpsample.ui.base.delegate.manager.ScreenEventDelegateManager;
import com.agna.realmvp.realmvpsample.ui.base.error.ErrorHandler;
import com.agna.realmvp.realmvpsample.ui.base.screen.HandlableErrorView;

import javax.inject.Inject;

/**
 * todo add delegates from ferro instead inheritance
 */
public abstract class BaseFragmentView extends MvpFragmentV4View implements
        HandlableErrorView,
        SupportScreenEventDelegation {

    @Inject
    ErrorHandler errorHandler;

    private ScreenEventDelegateManager delegateManager;

    protected FragmentScreenModule getFragmentScreenModule(){
        return new FragmentScreenModule(getPersistentScreenScope());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState, boolean viewRecreated) {
        delegateManager = new FragmentScreenEventDelegateManager((SupportScreenEventDelegation) getActivity());
        super.onActivityCreated(savedInstanceState, viewRecreated);
    }


    @Override
    public ScreenEventDelegateManager getScreenEventDelegateManager() {
        return delegateManager;
    }

    @Override
    public void handleError(Throwable error) {
        errorHandler.handleError(error);
    }
}

