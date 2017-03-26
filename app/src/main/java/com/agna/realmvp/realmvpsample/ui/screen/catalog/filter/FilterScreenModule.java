package com.agna.realmvp.realmvpsample.ui.screen.catalog.filter;

import com.agna.realmvp.realmvpsample.ui.base.dagger.CustomScreenModule;

import dagger.Module;

@Module
public class FilterScreenModule extends CustomScreenModule<FilterRoute> {
    public FilterScreenModule(FilterRoute route) {
        super(route);
    }
}