package com.agna.realmvp.realmvpsample.ui.base.delegate;


import com.agna.realmvp.realmvpsample.ui.base.delegate.manager.ScreenEventDelegateManager;

/**
 * интерфейс для экрана, поддерживающего делегирование обработки событий экрана
 */
public interface SupportScreenEventDelegation {
    ScreenEventDelegateManager getScreenEventDelegateManager();
}