package com.kaz.news_picker.Request

interface NewsPageRequest {
    val title: String
    val url: String
    val cssQuery: String
}