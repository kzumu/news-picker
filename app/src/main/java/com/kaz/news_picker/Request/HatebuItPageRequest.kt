package com.kaz.news_picker.Request

class HatebuItPageRequest: NewsPageRequest {
    override val title: String
        get() = "はてブ"

    override val url: String
        get() = "http://b.hatena.ne.jp/hotentry/it/"

    override val cssQuery: String
        get() = "#container > div.wrapper > div > div > ul > li > div > h3 > a"
}