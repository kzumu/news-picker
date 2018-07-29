package com.kaz.news_picker

import com.squareup.moshi.Moshi
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface NewsService {
    @GET("/")
    fun schedule(): Observable<ResponseBody>
}

fun fetch(request: NewsPageRequest): NewsService {
    val moshi = Moshi.Builder()
            .build()

    val okClient = OkHttpClient()

    val builder = Retrofit.Builder()
            .client(okClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(request.url)
            .build()

    return builder.create(NewsService::class.java)
}