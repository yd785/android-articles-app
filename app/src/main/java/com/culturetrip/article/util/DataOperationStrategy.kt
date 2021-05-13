package com.culturetrip.article.util

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers

private const val TAG = "DataOperationStrategy"

fun <T, A> performGetOperation(databaseQuery: () -> LiveData<T>,
                               networkCall: suspend () -> Resource<A>,
                               saveCallResult: suspend (A) -> Unit): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        Log.d(TAG, "performGetOperation: ")
        emit(Resource.loading())
        Log.d(TAG, "performGetOperation: emit resource loading")
        val source = databaseQuery.invoke().map {
            Resource.success(it)
        }

        Log.d(TAG, "performGetOperation: databaseQuery after get source: " + source)
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)

        }
        else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
            emitSource(source)
        }
    }