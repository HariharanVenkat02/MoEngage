package com.example.moengagearticles.Data

import com.google.gson.annotations.SerializedName

data class Articles(
    @SerializedName("status") var status: String? = null,
    @SerializedName("articles") var articles: ArrayList<IndArticle> = arrayListOf()
)
