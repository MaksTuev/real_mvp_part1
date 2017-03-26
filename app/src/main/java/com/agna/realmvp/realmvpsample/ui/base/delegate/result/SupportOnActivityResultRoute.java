package com.agna.realmvp.realmvpsample.ui.base.delegate.result;


import android.content.Intent;

import java.io.Serializable;

/**
 * интерфейс для Route, работающего через onActivityResult
 * @param <T>
 */
public interface SupportOnActivityResultRoute<T extends Serializable> {

    Intent prepareResultIntent(T resultData);

    T parseResultIntent(Intent resultIntent);

    int getRequestCode();
}
