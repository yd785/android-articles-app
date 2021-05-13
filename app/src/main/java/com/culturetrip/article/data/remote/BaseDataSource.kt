package com.culturetrip.article.data.remote

import com.culturetrip.article.util.Resource
import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return Resource.error("Network call has failed for this reason : ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return Resource.error(e.message ?: e.toString())
        }
    }

}