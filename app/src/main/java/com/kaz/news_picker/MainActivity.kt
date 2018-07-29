package com.kaz.news_picker

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import com.kaz.news_picker.databinding.ActivityMainBinding
import com.squareup.okhttp.ResponseBody
import org.jsoup.Jsoup
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    lateinit var hoge: Subscription

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        Log.d("onCreate","onCreate")

        hoge =  client().schedule()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ResponseBody>() {
                    override fun onNext(t: ResponseBody?) {
                        Log.d("onCreate","onNext")
                        val document = Jsoup.parse(t?.string())
//                        val title = document.title()
                        //val textView =  //findViewById(R.id.textView) as TextView
                        val texts: List<String> = document.select("#main > div > section > div > div > div > ul > li > a")
                                .map { it.text() }
                        val a = TextUtils.join("\n", texts)

                        binding.textView.text = "${a}"
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