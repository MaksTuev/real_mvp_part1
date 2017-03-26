package com.agna.realmvp.realmvpsample.ui.screen.book;

import com.agna.ferro.mvp.component.ScreenComponent;
import com.agna.ferro.mvp.component.scope.PerScreen;
import com.agna.realmvp.realmvpsample.app.AppComponent;
import com.agna.realmvp.realmvpsample.ui.base.dagger.ActivityScreenModule;

import dagger.Component;

@PerScreen
@Component(dependencies = AppComponent.class, modules = {ActivityScreenModule.class, BookScreenModule.class})
interface BookScreenComponent extends ScreenComponent<BookActivityView> {
}