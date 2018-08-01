package com.kaz.news_picker.Request

class LivedoorNewsPageRequest : NewsPageRequest {
    override val title: String
        get() = "ライブドア" +
                ""
    override val url: String
        get() = "http://news.livedoor.com/"
//        get() = "http://localhost:3000/"

    override val cssQuery: String
        get() = "#main > div > section > div > div > div > ul > li > a"
}