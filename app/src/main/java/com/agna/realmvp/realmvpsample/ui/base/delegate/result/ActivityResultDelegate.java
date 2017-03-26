package com.agna.realmvp.realmvpsample.ui.base.delegate.result;


import android.content.Intent;

import com.agna.realmvp.realmvpsample.ui.base.delegate.ScreenEventDelegate;


/**
 * делегат, обрабатывающий событие onActivityResult
 */
public interface ActivityResultDelegate extends ScreenEventDelegate {

    boolean onActivityResult(int requestCode, int resultCode, Intent data);
}
