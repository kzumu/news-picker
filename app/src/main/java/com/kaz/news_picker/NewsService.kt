package com.kaz.news_picker

import com.squareup.moshi.Moshi
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.ResponseBody
import retrofit.MoshiConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import retrofit.http.GET
import rx.Observable

interface NewsService {
    @GET("/")
    fun schedule(): Observable<ResponseBody>
}

data class NewsResponse(
    var body: String
)

fun client(): NewsService {
    val moshi = Moshi.Builder()
            .build()

    val okClient = OkHttpClient()

    val builder = Retrofit.Builder()
            .client(okClient)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("http://news.livedoor.com/")
            .build()

    return builder.create(NewsService::class.java)
}