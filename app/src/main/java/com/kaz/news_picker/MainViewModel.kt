package com.kaz.news_picker

import com.jakewharton.rxrelay2.BehaviorRelay
import com.kaz.news_picker.Request.HatebuItPageRequest
import com.kaz.news_picker.Request.LivedoorNewsPageRequest
import com.kaz.news_picker.Request.NewsPicksPageRequest
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.schedulers.Schedulers
import jp.keita.kagurazaka.rxproperty.toReadOnlyRxProperty
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainViewModel {
    private val disposable = CompositeDisposable()
    private val topics: BehaviorRelay<String> = BehaviorRelay.createDefault("")
    val output = topics.toReadOnlyRxProperty()

    fun onCreate() {
        Observable.merge(Observable.just(Unit),Observable.interval(1, TimeUnit.HOURS))
                .withLatestFrom(
                        Observables.combineLatest(
                                fetch(LivedoorNewsPageRequest()),
                                fetch(NewsPicksPageRequest()),
                                fetch(HatebuItPageRequest())
                                )
                )
                .map { it.second }
                .map {
                    val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    val date = df.format(Date())
                    "取得日時 ${date}\n${it.first.first}\n${it.first.second}\n\n" +
                    "${it.second.first}\n${it.second.second}\n\n" +
                    "${it.third.first}\n${it.third.second}"
                }
                .subscribeOn(Schedulers.newThread())
                .subscribeNext {
                    topics.accept(it)
                }.addTo(disposable)
    }
}
