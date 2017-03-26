package com.agna.realmvp.realmvpsample.ui.screen.book;


import android.content.Context;
import android.content.Intent;

import com.agna.realmvp.realmvpsample.ui.base.navigation.activity.route.ActivityWithParamsRoute;

public class BookRoute extends ActivityWithParamsRoute{
    private String bookId;

    public BookRoute(String bookId) {
        this.bookId = bookId;
    }

    public BookRoute(Intent intent){
        super(intent);
    }


    public String getBookId() {
        return bookId;
    }

    @Override
    public Intent prepareIntent(Context context) {
        Intent i = new Intent(context, BookActivityView.class);
        i.putExtra(EXTRA_FIRST, bookId);
        return i;
    }

    @Override
    protected void parseIntent(Intent intent) {
        bookId = intent.getStringExtra(EXTRA_FIRST);
    }
}
