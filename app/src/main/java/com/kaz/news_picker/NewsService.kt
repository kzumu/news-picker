package com.kaz.news_picker

import android.text.TextUtils
import android.util.Log
import android.util.TimeUtils
import com.kaz.news_picker.Request.NewsPageRequest
import com.squareup.moshi.Moshi
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface NewsService {
    @GET("/")
    fun schedule(): Observable<ResponseBody>
}

typealias Title = String
typealias Response = String

fun fetch(request: NewsPageRequest): Observable<Pair<Title, Response>> {
    val moshi = Moshi.Builder()
            .build()

    val okClient = OkHttpClient
            .Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()

    val builder = Retrofit.Builder()
            .client(okClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(request.url)
            .build()

    val response = builder
            .create(NewsService::class.java)
            .schedule()
            .map {
                val document = Jsoup.parse(it.string())
                Log.d("text", document.select(request.cssQuery).html())
                val texts: List<String> = document
                        .select(request.cssQuery)
                        .map { it.text() }
                TextUtils.join("\n", texts)
            }

    return response.map { Pair(request.title, it) }
}