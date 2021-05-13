package com.culturetrip.article.data.remote

import com.culturetrip.article.data.local.entity.Article

data class ArticlesResponse(
    val data: List<Article>
)