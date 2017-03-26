package com.agna.realmvp.realmvpsample.ui.screen.catalog.filter;


import android.content.Context;
import android.content.Intent;

import com.agna.realmvp.realmvpsample.domain.Filter;
import com.agna.realmvp.realmvpsample.ui.base.navigation.activity.route.ActivityWithParamsAndResultRoute;

public class FilterRoute extends ActivityWithParamsAndResultRoute<Filter> {

    private Filter filter;

    public FilterRoute(){}

    public FilterRoute(Filter filter) {
        this.filter = filter;
    }

    public FilterRoute(Intent intent) {
        filter = (Filter) intent.getSerializableExtra(EXTRA_FIRST);
    }

    @Override
    public Intent prepareIntent(Context context) {
        Intent i = new Intent(context, FilterActivityView.class);
        i.putExtra(EXTRA_FIRST, filter);
        return i;
    }

    public Filter getFilter() {
        return filter;
    }
}
