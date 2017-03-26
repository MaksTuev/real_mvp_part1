package com.agna.realmvp.realmvpsample.ui.base.screen.presenter;


import android.support.annotation.CallSuper;

import com.agna.ferro.mvp.view.BaseView;
import com.agna.ferro.mvprx.MvpRxPresenter;
import com.agna.realmvp.realmvpsample.app.scheduler.SchedulersProvider;
import com.agna.realmvp.realmvpsample.ui.base.delegate.ScreenEventDelegate;
import com.agna.realmvp.realmvpsample.ui.base.delegate.manager.ScreenEventDelegateManagerProvider;
import com.agna.realmvp.realmvpsample.ui.base.navigation.activity.navigator.ActivityNavigator;
import com.agna.realmvp.realmvpsample.ui.base.screen.HandlableErrorView;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.internal.util.InternalObservableUtils;

/**
 * базовый класс презентра для приложения
 * Подписки через все виды методов {@link #subscribe}, {@link #subscribeWithoutFreezing},
 * {@link #subscribeNetworkQuery} обрабатываются в главном потоке
 * При подписке с помощью методов {@link #subscribeNetworkQuery} observable источника переводится в
 * background поток.
 * Кроме того {@link #subscribeNetworkQuery} содержит стандартную обработку ошибок
 *
 * Кроме того содержит метод {@link #finish()} для закрытия текущего экрана, в дефолтной
 * имплементации закрывает активити
 *
 * см {@link CorePresenter}
 * @param <V>
 */
public abstract class BasePresenter<V extends BaseView & HandlableErrorView> extends MvpRxPresenter<V> {

    private final ActivityNavigator activityNavigator;
    private final SchedulersProvider schedulersProvider;
    private final ScreenEventDelegateManagerProvider delegateManagerProvider;
    private final ScreenEventDelegate[] delegates;

    /**
     * This method is called, when view is ready
     * @param viewRecreated - show whether view created in first time or recreated after
     *                        changing configuration
     */
    @CallSuper
    public void onLoad(boolean viewRecreated) {
        super.onLoad(viewRecreated);
        for (ScreenEventDelegate delegate : delegates) {
            delegateManagerProvider.get().registerDelegate(delegate);
        }
    }

    public BasePresenter(BasePresenterDependency basePresenterDependency) {
        this.delegateManagerProvider = basePresenterDependency.getDelegateManagerProvider();
        this.delegates = basePresenterDependency.getScreenEventDelegates();
        this.schedulersProvider = basePresenterDependency.getSchedulersProvider();
        activityNavigator = basePresenterDependency.getActivityNavigator();
    }

    /**
     * закрывает текущую активити
     * если необходима другая логика закрытия экрана, следует переопределить этот метод
     */
    public void finish(){
        activityNavigator.finishCurrent();
    }

    @Override
    protected <T> Subscription subscribe(Observable<T> observable, Action1<T> onNext, Action1<Throwable> onError) {
        return super.subscribe(observable.observeOn(schedulersProvider.main(), true), onNext, onError);
    }

    protected <T> Subscription subscribe(Observable<T> observable, Action1<T> onNext) {
        return this.subscribe(observable, onNext, InternalObservableUtils.ERROR_NOT_IMPLEMENTED);
    }

    @Override
    protected <T> Subscription subscribe(Observable<T> observable, Func2<T, T, Boolean> replaceFrozenEventPredicate, Action1<T> onNext, Action1<Throwable> onError) {
        return super.subscribe(observable.observeOn(schedulersProvider.main(), true), replaceFrozenEventPredicate, onNext, onError);
    }

    @Override
    protected <T> Subscription subscribe(Observable<T> observable, Func2<T, T, Boolean> replaceFrozenEventPredicate, Subscriber<T> subscriber) {
        return super.subscribe(observable.observeOn(schedulersProvider.main(), true), replaceFrozenEventPredicate, subscriber);
    }

    @Override
    protected <T> Subscription subscribe(Observable<T> observable, Subscriber<T> subscriber) {
        return super.subscribe(observable.observeOn(schedulersProvider.main(), true), subscriber);
    }

    protected <T> Subscription subscribeWithoutFreezing(final Observable<T> observable,
                                                        final Subscriber<T> subscriber) {
        return super.subscribe(observable.observeOn(schedulersProvider.main(), true), subscriber);
    }

    /**
     * Работает также как {@link #subscribe}, кроме того автоматически обрабатывает ошибки,
     * см {@link HandlableErrorView} и переводит выполенения потока в фон
     */
    protected <T> Subscription subscribeNetworkQuery(Observable<T> observable,
                                                     final Action1<T> onNext) {
        return subscribeNetworkQuery(observable, onNext, null);
    }

    /**
     * Работает также как {@link #subscribe}, кроме того автоматически обрабатывает ошибки,
     * см {@link HandlableErrorView} и переводит выполенения потока в фон
     */
    protected <T> Subscription subscribeNetworkQuery(Observable<T> observable,
                                                     final Action1<T> onNext,
                                                     final Action1<Throwable> onError) {
        observable = observable.subscribeOn(schedulersProvider.worker());
        return subscribe(observable, onNext, e -> handleError(e, onError));
    }

    private void handleError(Throwable e, Action1<Throwable> onError) {
        getView().handleError(e);
        if (onError != null) {
            onError.call(e);
        }
    }

}
