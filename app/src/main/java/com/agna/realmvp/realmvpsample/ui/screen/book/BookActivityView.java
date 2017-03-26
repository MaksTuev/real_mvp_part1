package com.agna.realmvp.realmvpsample.ui.screen.book;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agna.ferro.mvp.component.ScreenComponent;
import com.agna.ferro.mvp.presenter.MvpPresenter;
import com.agna.realmvp.realmvpsample.R;
import com.agna.realmvp.realmvpsample.app.App;
import com.agna.realmvp.realmvpsample.domain.Book;
import com.agna.realmvp.realmvpsample.ui.base.screen.activity.BaseActivityView;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

/**
 */
public class BookActivityView extends BaseActivityView {
    @Inject
    BookPresenter presenter;

    private TextView nameTv;
    private ImageView coverIv;
    private Button downloadBtn;
    private View readBtn;
    private TextView descriptionTv;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler = new Handler();
    private View contentContainer;

    @Override
    public MvpPresenter getPresenter() {
        return presenter;
    }

    @Override
    public String getName() {
        return "Book";
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_book;
    }

    @Override
    protected ScreenComponent createScreenComponent() {
        return DaggerBookScreenComponent.builder()
                .appComponent(App.getAppComponent(this))
                .bookScreenModule(new BookScreenModule(new BookRoute(getIntent())))
                .activityScreenModule(getActivityScreenModule())
                .build();
    }

    @Override
    public void onCreate(Bundle savedInstanceState, boolean viewRecreated) {
        super.onCreate(savedInstanceState, viewRecreated);
        findViews();
        initViews();
    }

    public void showLoading() {
        handler.post(() -> swipeRefreshLayout.setRefreshing(true));
    }

    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void initViews() {
        downloadBtn.setOnClickListener(v -> presenter.downloadBook());
        readBtn.setOnClickListener(v -> Toast.makeText(this, "Stub", Toast.LENGTH_SHORT).show());
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.reloadData());
    }

    private void findViews() {
        contentContainer = findViewById(R.id.book_content_container);
        nameTv = (TextView) findViewById(R.id.book_name_tv);
        coverIv = (ImageView) findViewById(R.id.book_cover_iv);
        downloadBtn = (Button)findViewById(R.id.book_download_btn);
        readBtn = findViewById(R.id.book_read_btn);
        descriptionTv = (TextView) findViewById(R.id.book_description_tv);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.book_swr);
    }

    public void showData(FullBookModel fullBookModel) {
        contentContainer.setVisibility(View.VISIBLE);
        Book book = fullBookModel.getBook();
        nameTv.setText(book.getName());
        Glide.with(coverIv.getContext())
                .load(book.getImageUrl())
                .fitCenter()
                .placeholder(R.drawable.book_placeholder)
                .error(R.drawable.book_placeholder)
                .into(coverIv);
        descriptionTv.setText(fullBookModel.getDescription());

        if (book.isDownloaded()) {
            downloadBtn.setVisibility(View.GONE);
            readBtn.setVisibility(View.VISIBLE);
        } else if (book.isDownloading()){
            downloadBtn.setVisibility(View.VISIBLE);
            String downloadBtnText = downloadBtn.getResources()
                    .getString(R.string.downloading_btn, book.getDownloadProgress());
            downloadBtn.setText(downloadBtnText);
            readBtn.setVisibility(View.GONE);
        } else {
            downloadBtn.setVisibility(View.VISIBLE);
            downloadBtn.setText(R.string.download_btn);
            readBtn.setVisibility(View.GONE);
        }
    }
}
