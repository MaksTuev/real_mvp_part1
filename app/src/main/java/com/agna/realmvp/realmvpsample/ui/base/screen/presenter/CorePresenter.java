package com.agna.realmvp.realmvpsample.ui.base.screen.presenter;


import android.support.annotation.CallSuper;

import com.agna.ferro.mvp.view.BaseView;
import com.agna.ferro.mvprx.MvpRxPresenter;
import com.agna.ferro.rx.OperatorFreeze;
import com.agna.realmvp.realmvpsample.ui.base.delegate.ScreenEventDelegate;
import com.agna.realmvp.realmvpsample.ui.base.delegate.manager.ScreenEventDelegateManagerProvider;

/**
 * базовый класс презентера, содержащий всю корневую логику
 * Методы {@link #subscribe} добавляют логику замораживания
 * Rx событий на время пересоздания вью или когда экран находится в фоне, см {@link OperatorFreeze}
 * Также все подписки освобождаются при полном уничтожении экрана
 * @param <V>
 */
public abstract class CorePresenter<V extends BaseView> extends MvpRxPresenter {


}
