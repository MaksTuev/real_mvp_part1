package com.agna.realmvp.realmvpsample.ui.screen.book;

import com.agna.realmvp.realmvpsample.ui.base.dagger.CustomScreenModule;

import dagger.Module;

@Module
public class BookScreenModule extends CustomScreenModule<BookRoute> {
    public BookScreenModule(BookRoute route) {
        super(route);
    }
}