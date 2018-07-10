package com.kaz.news_picker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.squareup.okhttp.ResponseBody
import retrofit.Response
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    lateinit var hoge: Subscription

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("onCreate","onCreate")

        hoge =  client().schedule()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ResponseBody>() {
                    override fun onNext(t: ResponseBody?) {

                        Log.d("onCreate","onNext")
                        Log.d("onCreate", t?.string())
                    }

                    override fun onError(e: Throwable?) {
                        Log.d("onCreate","onError", e)
                    }

                    override fun onCompleted() {
                        Log.d("onCreate","onCompleted")
                    }
                })
    }
}