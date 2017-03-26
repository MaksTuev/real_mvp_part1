package com.agna.realmvp.realmvpsample.ui.screen.splash.dialog;


import com.agna.realmvp.realmvpsample.ui.base.navigation.dialog.route.DialogRoute;
import com.agna.realmvp.realmvpsample.ui.base.screen.dialog.BaseDialogFragment;

public class StartDialogRoute extends DialogRoute {
    @Override
    protected Class<? extends BaseDialogFragment> getFragmentClass() {
        return StartDialog.class;
    }
}
