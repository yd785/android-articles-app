package com.culturetrip.article.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface ArticleServiceApi {
    companion object {
        const val BASE_URL = "https://cdn.theculturetrip.com/"
    }

    @GET("home-assignment/response.json")
    suspend fun getAllArticles() : Response<ArticlesResponse>
}