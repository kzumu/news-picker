package com.kaz.news_picker

class LivedoorNewsPageRequest :NewsPageRequest {
    override val url: String
        get() = "http://news.livedoor.com/"

    override val cssQuery: String
        get() = "#main > div > section > div > div > div > ul > li > a"
}