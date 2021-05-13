package com.culturetrip.article.data.remote

import javax.inject.Inject


class ArticleRemoteDataSource @Inject constructor(
    private val articleServiceApi: ArticleServiceApi
): BaseDataSource() {

    suspend fun getArticles() = getResult { articleServiceApi.getAllArticles() }
}