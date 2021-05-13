package com.culturetrip.article.data.local

import androidx.room.TypeConverter
import com.culturetrip.article.data.local.entity.AuthorAvatar

import com.google.gson.Gson

class ObjectTypeConverter {

    @TypeConverter
    inline fun <reified T> fromObj(valueStr: String): T {
        return Gson().fromJson(valueStr, T::class.java)
    }

    @TypeConverter
    inline fun <reified T> toString(typeObj: T): String {
        return Gson().toJson(typeObj)
    }


    @TypeConverter
    fun authorAvaterToString(authorAvatar: AuthorAvatar): String = Gson().toJson(authorAvatar)

    @TypeConverter
    fun stringToauthorAvater(string: String): AuthorAvatar = Gson().fromJson(string, AuthorAvatar::class.java)
}