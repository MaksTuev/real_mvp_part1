package com.agna.realmvp.realmvpsample.ui.screen.splash.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.agna.realmvp.realmvpsample.ui.base.screen.dialog.BaseDialogFragmentWithResult;
import com.agna.realmvp.realmvpsample.ui.screen.splash.SplashPresenter;
import com.agna.realmvp.realmvpsample.ui.screen.splash.SplashScreenComponent;

import javax.inject.Inject;


public class StartDialog extends BaseDialogFragmentWithResult {

    @Inject
    SplashPresenter presenter;

    @Override
    public String getName() {
        return "StartDialog";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((SplashScreenComponent)getScreenComponent()).inject(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Продолжить?")
                .setPositiveButton("да", (d, which) -> presenter.openCatalog())
                .setNegativeButton("нет", (d, which) -> presenter.finish())
                .setCancelable(false)
                .create();
    }
}
