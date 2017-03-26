package com.agna.realmvp.realmvpsample.ui.screen.book;

import com.agna.ferro.mvp.component.scope.PerScreen;
import com.agna.ferro.rx.OperatorFreeze;
import com.agna.realmvp.realmvpsample.domain.Book;
import com.agna.realmvp.realmvpsample.interactor.book.BookRepository;
import com.agna.realmvp.realmvpsample.ui.base.permission.PermissionManager;
import com.agna.realmvp.realmvpsample.ui.base.screen.presenter.BasePresenter;
import com.agna.realmvp.realmvpsample.ui.base.screen.presenter.BasePresenterDependency;
import com.agna.realmvp.realmvpsample.ui.common.permission.WriteStoragePermissionRequest;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * presenter for Book screen
 * <p>
 * Presenter with freeze logic.
 * If subscribe to {@link Observable} via one of {@link #subscribe(Observable, Subscriber)} method,
 * all rx events (onNext, onError, onComplete) would be frozen when view destroyed and unfrozen
 * when view recreated (see {@link OperatorFreeze}).
 * All events would be also frozen when screen paused and unfrozen when screen resumed.
 * When screen finally destroyed, all subscriptions would be automatically unsubscribed.
 * <p>
 * When configuration changed, presenter isn't destroyed and reused for new view
 */
@PerScreen
class BookPresenter extends BasePresenter<BookActivityView> {

    private final BookRepository bookRepository;
    private final PermissionManager permissionManager;
    private final String bookId;

    private FullBookModel fullBookModel;
    private Subscription loadFullBookSubscription;

    @Inject
    public BookPresenter(BookRepository bookRepository,
                         PermissionManager permissionManager,
                         BookRoute route,
                         BasePresenterDependency basePresenterDependency) {
        super(basePresenterDependency);
        this.bookRepository = bookRepository;
        this.permissionManager = permissionManager;
        this.bookId = route.getBookId();
    }

    @Override
    public void onLoad(boolean viewRecreated) {
        super.onLoad(viewRecreated);
        tryLoadData();
        if (!viewRecreated) {
            observeChangingBook();
        }
    }

    public void downloadBook() {
        subscribe(permissionManager.request(new WriteStoragePermissionRequest())
                .filter(granted -> granted),
                granted -> {
                    if (fullBookModel != null && !fullBookModel.getBook().isDownloading()) {
                        bookRepository.startDownload(bookId);
                    }
                });
    }

    /**
     * example of request, which load main data for screen
     */
    private void tryLoadData() {
        if (fullBookModel != null) {
            //if books already loaded, just show data
            onLoadDataSuccess(fullBookModel);
        } else if (isSubscriptionInactive(loadFullBookSubscription)) {
            //if data isn't loading now, start loading
            getView().showLoading();
            loadData();
        } else {
            //else simple wait while data has loaded
            getView().showLoading();
        }
    }

    /**
     * example of simple request
     * you do not check already loaded data and loading status
     */
    public void reloadData() {
        loadData();
    }

    private void loadData() {
        Observable<FullBookModel> observable = Observable.zip(
                bookRepository.getBook(bookId),
                bookRepository.getBookDescription(bookId),
                FullBookModel::new);

        loadFullBookSubscription = subscribeNetworkQuery(observable,
                this::onLoadDataSuccess,
                this::onLoadDataError);
    }

    /**
     * example for subscribing to observable, which emits many events
     */
    private void observeChangingBook() {
        Observable<Book> observable = bookRepository.observeChangingBooks()
                //getting event only for book with bookId
                .filter(book -> book.getId().equals(bookId))
                .observeOn(AndroidSchedulers.mainThread());

        subscribe(observable,
                //Keep only last book in freeze buffer.
                //This prevent handling not relevant events when buffer would be unfrozen.
                //You can simple unsubscribe/subscrube to this observable and not use
                //  replaceFrozenEventPredicate, but then you can miss important event
                (frozenBook, newBook) -> true,
                this::updateBook,
                e -> Timber.e(e, "update book error"));

    }

    private void updateBook(Book newBook) {
        if (fullBookModel != null) {
            fullBookModel.setBook(newBook);
            getView().showData(fullBookModel);
        }
    }

    private void onLoadDataSuccess(FullBookModel fullBookModel) {
        this.fullBookModel = fullBookModel;
        getView().hideLoading();
        getView().showData(fullBookModel);
    }

    private void onLoadDataError(Throwable e) {
        getView().hideLoading();
    }
}
