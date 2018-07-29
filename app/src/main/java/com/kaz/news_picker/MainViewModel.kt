package com.kaz.news_picker

import android.text.TextUtils
import android.util.Log
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import jp.keita.kagurazaka.rxproperty.toReadOnlyRxProperty
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.reactivestreams.Subscriber

class MainViewModel {
    private val disposable = CompositeDisposable()
    private val topics: BehaviorRelay<String> = BehaviorRelay.createDefault("")
    val output = topics.toReadOnlyRxProperty()

    fun onCreate() {
        disposable.add(
        fetch(LivedoorNewsPageRequest())
                .schedule()
                .subscribeOn(Schedulers.newThread())
                .subscribeBy(
                        onNext = {
                            Log.d("hoge", "onNext")
                            val document = Jsoup.parse(it.string())
                            val texts: List<String> = document.select("#main > div > section > div > div > div > ul > li > a")
                                    .map { it.text() }
                            val topicsString = TextUtils.join("\n", texts)
                            topics.accept(topicsString)
                        },
                        onError = {
                            Log.e("hoge", "error")
                            Log.e("hoge", it.localizedMessage)
                        }
                )
        )
//               .subscribe(object : Subscriber<ResponseBody>() {
//                    override fun onNext(t: ResponseBody?) {
//                        Log.d("onCreate","onNext")
//                        val document = Jsoup.parse(t?.string())
//                        val title = document.title()
//                        //val textView =  //findViewById(R.id.textView) as TextView
//                        val texts: List<String> = document.select("#main > div > section > div > div > div > ul > li > a")
//                                .map { it.text() }
//                        val a = TextUtils.join("\n", texts)
//                        binding.textView.text = "${a}"
//                    }override fun onError(e: Throwable?) {
//                        Log.d("onCreate","onError", e)
//                    }override fun onCompleted() {
//                        Log.d("onCreate","onCompleted")
//                    } })
//                .subscribeNext {
//                    Log.d("onCreate", "onNext")
//                    val document = Jsoup.parse(it.string())
//                    val texts: List<String> = document.select("#main > div > section > div > div > div > ul > li > a")
//                            .map { it.text() }
//                    val topicsString = TextUtils.join("\n", texts)
//                    topics.accept(topicsString)
//                }
//                .addTo(disposable)
    }
}
