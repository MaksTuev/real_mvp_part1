package com.agna.realmvp.realmvpsample.app.scheduler;

import rx.Scheduler;

public interface SchedulersProvider {
    Scheduler main();

    Scheduler worker();
}