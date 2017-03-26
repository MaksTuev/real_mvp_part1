package com.agna.realmvp.realmvpsample.ui.screen.splash;


import com.agna.ferro.mvp.component.scope.PerScreen;
import com.agna.realmvp.realmvpsample.ui.base.navigation.activity.navigator.ActivityNavigator;
import com.agna.realmvp.realmvpsample.ui.base.navigation.dialog.navigator.DialogNavigator;
import com.agna.realmvp.realmvpsample.ui.base.screen.presenter.BasePresenter;
import com.agna.realmvp.realmvpsample.ui.base.screen.presenter.BasePresenterDependency;
import com.agna.realmvp.realmvpsample.ui.screen.catalog.CatalogRoute;
import com.agna.realmvp.realmvpsample.ui.screen.splash.dialog.StartDialogRoute;

import javax.inject.Inject;

@PerScreen
public class SplashPresenter extends BasePresenter<SplashActivityView> {

    private final ActivityNavigator activityNavigator;
    private final DialogNavigator dialogNavigator;

    @Inject
    public SplashPresenter(ActivityNavigator activityNavigator,
                           DialogNavigator dialogNavigator,
                           BasePresenterDependency basePresenterDependency) {
        super(basePresenterDependency);
        this.activityNavigator = activityNavigator;
        this.dialogNavigator = dialogNavigator;
    }

    @Override
    public void onLoad(boolean viewRecreated) {
        super.onLoad(viewRecreated);
        if(!viewRecreated) {
            dialogNavigator.show(new StartDialogRoute());
        }
    }

    public void openCatalog() {
        activityNavigator.start(new CatalogRoute());
    }
}
