package com.example.moengagearticles.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moengagearticles.Data.Articles
import com.example.moengagearticles.Network.ApiService
import kotlinx.coroutines.launch


class ArticleListViewModel() : ViewModel() {

    private val _articleList = MutableLiveData<Articles>()
    val articleList: LiveData<Articles> get() = _articleList
    private val apiService: ApiService = ApiService()


    fun fetchArticles() {
        viewModelScope.launch {
            try {
                val response = apiService.getArticlesList()
                if (response.isSuccessful) {
                    _articleList.value = response.body()
                } else {
                    // Handle error
                }
            } catch (e: Exception) {
                // Handle network error
            }
        }
    }

}