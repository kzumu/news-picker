package com.kaz.news_picker.Request

class NewsPicksPageRequest: NewsPageRequest {
    override val title: String
        get() = "NewsPicks"

    override val url: String
        get() = "https://newspicks.com/theme-news/technology/"

    override val cssQuery: String
        get() = "body > div.contents-container > div.page-content > div > div > div > div > a > div > div > div.title"
}