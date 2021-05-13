package com.culturetrip.article.ui.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.culturetrip.article.data.local.entity.Article
import com.culturetrip.article.data.repository.ArticleFetchError
import com.culturetrip.article.data.repository.ArticleRepository
import com.culturetrip.article.util.Resource
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel @ViewModelInject constructor (
    private val repository : ArticleRepository
) : ViewModel() {

    private val _loadedStatus = MutableLiveData<Boolean>(false)

   val loadedStatus
       get() = _loadedStatus

    private val _spinner = MutableLiveData<Boolean>(false)

    /**
     * Show a loading spinner if true
     */
    val spinner: LiveData<Boolean>
        get() = _spinner

    private val _toastMsg = MutableLiveData<String?>()

    /**
     * Request a toast to display a string.
     */
    val toastMsg: LiveData<String?>
        get() = _toastMsg

    fun fetchArticles() {
        launchDataLoad {
            repository.fetchArticles()
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Unit {
        viewModelScope.launch {
            try {
                _spinner.value = true
                block()
                _loadedStatus.value = true
            } catch (error: ArticleFetchError) {
                _toastMsg.value = error.message
            } finally {
                _spinner.value = false
            }
        }
    }
}