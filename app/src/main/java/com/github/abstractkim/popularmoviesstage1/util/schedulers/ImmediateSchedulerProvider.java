package com.github.abstractkim.popularmoviesstage1.util.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class ImmediateSchedulerProvider implements BaseSchedulerProvider {
    @Override
    public Scheduler computation() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }
}
