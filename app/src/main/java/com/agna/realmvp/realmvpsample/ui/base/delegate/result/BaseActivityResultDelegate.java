package com.agna.realmvp.realmvpsample.ui.base.delegate.result;


import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.agna.realmvp.realmvpsample.ui.base.navigation.ScreenResult;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * базовый класс делегата, позволяющий регистрировать обработчики на событие onActivityResult
 */
public class BaseActivityResultDelegate implements ActivityResultDelegate {
    private Map<SupportOnActivityResultRoute, Subject> activityResultSubjects = new HashMap<>();

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        for(SupportOnActivityResultRoute route : activityResultSubjects.keySet()){
            if(route.getRequestCode() == requestCode){
                Subject resultSubject = activityResultSubjects.get(route);
                resultSubject.onNext(new ScreenResult<>(
                        resultCode == Activity.RESULT_OK,
                        data != null ? route.parseResultIntent(data) : null));
                return true;
            }
        }
        return false;
    }

    protected  <T extends Serializable> Observable<ScreenResult<T>> observeOnActivityResult(
            Class<? extends SupportOnActivityResultRoute<T>> routeClass){
        try {
            SupportOnActivityResultRoute<T> route = routeClass.newInstance();
            tryRemoveDuplicateResultSubjects(route);
            PublishSubject<ScreenResult<T>> resultSubject = PublishSubject.create();
            activityResultSubjects.put(route, resultSubject);
            return resultSubject;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("route class " + routeClass.getCanonicalName()
                    + "must have default constructor", e);
        }
    }

    protected boolean isObserved(SupportOnActivityResultRoute route){
        for(SupportOnActivityResultRoute registeredRoute : activityResultSubjects.keySet()){
            if(registeredRoute.getRequestCode()==route.getRequestCode()){
                return true;
            }
        }
        return false;
    }

    private void tryRemoveDuplicateResultSubjects(SupportOnActivityResultRoute route) {
        for(SupportOnActivityResultRoute registeredRoute: activityResultSubjects.keySet()){
            if(registeredRoute.getRequestCode() == route.getRequestCode()){
                activityResultSubjects.get(registeredRoute).onCompleted();
                activityResultSubjects.remove(registeredRoute);
                Log.v(this.getClass().getName(), "duplicate registered SupportOnActivityResultRoute :"
                        + registeredRoute + " old route unregistered");
            }
        }
    }


}
