package com.culturetrip.article.data.repository

import androidx.lifecycle.LiveData
import com.culturetrip.article.data.local.ArticleDao
import com.culturetrip.article.data.local.entity.Article
import com.culturetrip.article.data.remote.ArticleRemoteDataSource
import com.culturetrip.article.util.Resource
import com.culturetrip.article.util.performGetOperation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import okhttp3.Dispatcher

import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val remoteDataSource: ArticleRemoteDataSource,
    private val localDataSource: ArticleDao
)  {
    val articles: LiveData<List<Article>> = localDataSource.getAllArticles()

//    fun fetchArticles() = performGetOperation(
//        databaseQuery = { localDataSource.getAllArticles() },
//        networkCall = { remoteDataSource.getArticles() },
//        saveCallResult = { localDataSource.insertAll(it.data) }
//    )

    suspend fun fetchArticles() {
        try {
            val result = withContext(Dispatchers.IO) {
                remoteDataSource.getArticles()
            }

            if(result.status == Resource.Status.SUCCESS) {
                result.data?.let { localDataSource.insertAll(it.data) }
            } else if (result.status == Resource.Status.ERROR) {
                throw ArticleFetchError("Unable to fetch articles", Throwable(result.message))
            }
        } catch (error: Throwable) {
            throw ArticleFetchError("Unable to fetch articles", error)
        }
    }
}


/**
 * Thrown when there was a error fetching articles
 *
 * @property message user ready error message
 * @property cause the original cause of this exception
 */
class ArticleFetchError(message: String, cause: Throwable) : Throwable(message, cause)