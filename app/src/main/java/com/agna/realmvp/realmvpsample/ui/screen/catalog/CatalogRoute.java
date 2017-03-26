package com.agna.realmvp.realmvpsample.ui.screen.catalog;


import android.content.Context;
import android.content.Intent;

import com.agna.realmvp.realmvpsample.ui.base.navigation.activity.route.ActivityRoute;

public class CatalogRoute extends ActivityRoute {

    @Override
    public Intent prepareIntent(Context context) {
        Intent intent = new Intent(context, CatalogActivityView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }
}
