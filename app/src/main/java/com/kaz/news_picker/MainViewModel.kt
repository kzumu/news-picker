package com.kaz.news_picker

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import jp.keita.kagurazaka.rxproperty.toReadOnlyRxProperty

class MainViewModel {
    private val disposable = CompositeDisposable()
    private val topics: BehaviorRelay<String> = BehaviorRelay.createDefault("")
    val output = topics.toReadOnlyRxProperty()

    fun onCreate() {
        fetch(LivedoorNewsPageRequest())
                .subscribeOn(Schedulers.newThread())
                .subscribeNext {
                    topics.accept(it)
                }.addTo(disposable)
    }
}
