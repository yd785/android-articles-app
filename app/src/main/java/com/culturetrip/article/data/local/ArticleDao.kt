package com.culturetrip.article.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.culturetrip.article.data.local.entity.Article

@Dao
interface ArticleDao {

    @Query("SELECT * From articles")
    fun getAllArticles() : LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<Article>)

    @Query("DELETE FROM articles")
    suspend fun deleteArticles()

}