package com.kaz.news_picker

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.Observable

fun Disposable.addTo(disposable: CompositeDisposable) = disposable.add(this)
fun <T> Observable<T>.subscribeNext(closure: (T) -> Unit): Disposable = subscribe(closure)