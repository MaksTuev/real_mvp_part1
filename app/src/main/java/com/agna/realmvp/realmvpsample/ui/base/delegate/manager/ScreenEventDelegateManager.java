package com.agna.realmvp.realmvpsample.ui.base.delegate.manager;


import com.agna.realmvp.realmvpsample.ui.base.delegate.ScreenEventDelegate;

/**
 * интерфейс менеджера {@link ScreenEventDelegate}
 */
public interface ScreenEventDelegateManager {
    void registerDelegate(ScreenEventDelegate delegate);
}
