package com.culturetrip.article.data.local.entity

import androidx.room.*
import com.culturetrip.article.data.local.ObjectTypeConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "articles")
@TypeConverters(ObjectTypeConverter::class)
data class Article(
    @Embedded
    val author: Author,
    val category: String,
    @PrimaryKey
    @ColumnInfo(name = "article_id")
    val id: String,
    val imageUrl: String,
    val isLiked: Boolean,
    val isSaved: Boolean,
    val likesCount: Int,
    @Embedded
    val metaData: MetaData,
    val title: String
)

data class Author(
    @Embedded
    val authorAvatar: AuthorAvatar,
    val authorName: String,

    @ColumnInfo(name = "author_id")
    @SerializedName("id")
    val authorId: String
)

data class AuthorAvatar(
    @SerializedName("imageUrl")
    val imageAuthorUrl: String
)


data class MetaData(
    val creationTime: String,
    val updateTime: String
)

