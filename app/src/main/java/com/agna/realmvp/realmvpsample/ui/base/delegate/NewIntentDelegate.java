package com.agna.realmvp.realmvpsample.ui.base.delegate;


import android.content.Intent;

/**
 * делегат, обрабатывающий событие onNewIntent
 */
public interface NewIntentDelegate extends ScreenEventDelegate {

    boolean onNewIntent(Intent intent);
}
