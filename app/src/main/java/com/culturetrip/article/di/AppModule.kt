@file:Suppress("DEPRECATION")

package com.culturetrip.article.di

import android.content.Context
import com.culturetrip.article.data.local.AppDatabase
import com.culturetrip.article.data.local.ArticleDao
import com.culturetrip.article.data.remote.ArticleRemoteDataSource
import com.culturetrip.article.data.remote.ArticleServiceApi
import com.culturetrip.article.data.repository.ArticleRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(ArticleServiceApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideArticleDao(db: AppDatabase) = db.articleDao()

    @Provides
    fun provideArticlesService(retrofit: Retrofit): ArticleServiceApi =
        retrofit.create(ArticleServiceApi::class.java)

    @Singleton
    @Provides
    fun provideArticleRemoteDataSource(articleService: ArticleServiceApi) =
        ArticleRemoteDataSource(articleService)

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: ArticleRemoteDataSource,
        localDataSource: ArticleDao
    ) =
        ArticleRepository(remoteDataSource, localDataSource)

}