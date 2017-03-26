package com.agna.realmvp.realmvpsample.ui.base.screen.dialog;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.agna.ferro.core.HasName;
import com.agna.ferro.mvp.component.ScreenComponent;
import com.agna.realmvp.realmvpsample.ui.base.screen.activity.BaseActivityView;
import com.agna.realmvp.realmvpsample.ui.base.screen.fragment.BaseFragmentView;

public abstract class BaseDialogFragmentWithResult extends BaseDialogFragment implements HasName {
    public static final String EXTRA_PARENT = "EXTRA_PARENT";
    private Parent parentType;

    public void show(BaseActivityView parentActivityView) {
        parentType = Parent.ACTIVITY;
        show(parentActivityView.getSupportFragmentManager());
    }

    public void show(BaseFragmentView parentFragment) {
        parentType = Parent.FRAGMENT;
        this.setTargetFragment(parentFragment, 0);
        show(parentFragment.getFragmentManager());
    }

    protected void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, getName());
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        throw new RuntimeException("Instead this method, use method show(parentFragment) " +
                "or show(parentActivity)");
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        throw new RuntimeException("Instead this method, use method show(parentFragment) " +
                "or show(parentActivity)");
    }

    protected ScreenComponent getScreenComponent() {
        switch (parentType) {
            case ACTIVITY:
                return ((BaseActivityView)getActivity()).getScreenComponent();
            case FRAGMENT:
                return ((BaseFragmentView)getTargetFragment()).getScreenComponent();
            default:
                throw new IllegalStateException("Unsupported parent type: " + parentType);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (parentType == null) {
            parentType = (Parent)savedInstanceState.getSerializable(EXTRA_PARENT);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_PARENT, parentType);
    }

    private enum Parent {
        ACTIVITY,
        FRAGMENT
    }

}
