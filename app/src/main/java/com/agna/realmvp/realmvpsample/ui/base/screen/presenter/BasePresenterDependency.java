package com.agna.realmvp.realmvpsample.ui.base.screen.presenter;


import com.agna.ferro.mvp.component.scope.PerScreen;
import com.agna.realmvp.realmvpsample.app.scheduler.SchedulersProvider;
import com.agna.realmvp.realmvpsample.ui.base.delegate.ScreenEventDelegate;
import com.agna.realmvp.realmvpsample.ui.base.delegate.manager.ScreenEventDelegateManagerProvider;
import com.agna.realmvp.realmvpsample.ui.base.navigation.activity.navigator.ActivityNavigator;
import com.agna.realmvp.realmvpsample.ui.base.permission.PermissionManager;

import javax.inject.Inject;


/**
 * зависимости базового презентера
 * Обернуты в отдельный обьект чтобы не занимать много места в конструкторах классов наследников {@link BasePresenter}
 */
@PerScreen
public class BasePresenterDependency {

    private SchedulersProvider schedulersProvider;
    private ScreenEventDelegateManagerProvider delegateManagerProvider;

    private ActivityNavigator activityNavigator;
    private PermissionManager permissionManager;

    @Inject
    public BasePresenterDependency(SchedulersProvider schedulersProvider,
                                   ScreenEventDelegateManagerProvider delegateManagerProvider,
                                   ActivityNavigator activityNavigator,
                                   PermissionManager permissionManager) {
        this.schedulersProvider = schedulersProvider;
        this.delegateManagerProvider = delegateManagerProvider;
        this.activityNavigator = activityNavigator;
        this.permissionManager = permissionManager;
    }

    public SchedulersProvider getSchedulersProvider() {
        return schedulersProvider;
    }

    public ScreenEventDelegateManagerProvider getDelegateManagerProvider() {
        return delegateManagerProvider;
    }

    public ScreenEventDelegate[] getScreenEventDelegates() {
        return new ScreenEventDelegate[]{ activityNavigator, permissionManager };
    }

    public ActivityNavigator getActivityNavigator() {
        return activityNavigator;
    }
}
