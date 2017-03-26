package com.agna.realmvp.realmvpsample.ui.screen.catalog.filter;


import com.agna.ferro.mvp.component.scope.PerScreen;
import com.agna.realmvp.realmvpsample.domain.Filter;
import com.agna.realmvp.realmvpsample.domain.ShowFirstFilterValue;
import com.agna.realmvp.realmvpsample.ui.base.navigation.activity.navigator.ActivityNavigator;
import com.agna.realmvp.realmvpsample.ui.base.screen.presenter.BasePresenter;
import com.agna.realmvp.realmvpsample.ui.base.screen.presenter.BasePresenterDependency;

import javax.inject.Inject;

@PerScreen
public class FilterPresenter extends BasePresenter<FilterActivityView> {

    private final FilterRoute route;
    private final ActivityNavigator activityNavigator;

    private Filter filter;

    @Inject
    public FilterPresenter(FilterRoute route,
                           ActivityNavigator activityNavigator,
                           BasePresenterDependency basePresenterDependency) {
        super(basePresenterDependency);
        this.activityNavigator = activityNavigator;
        this.route = route;
        this.filter = route.getFilter();
    }

    public void applyFilter() {
        activityNavigator.finishWithResult(route, filter);
    }

    @Override
    public void onLoad(boolean viewRecreated) {
        super.onLoad(viewRecreated);
        getView().showFilter(filter);
    }

    public void onShowFirstChanged(ShowFirstFilterValue newValue) {
        filter.setShowFirst(newValue);
    }
}
