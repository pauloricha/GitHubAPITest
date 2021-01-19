package br.com.example.githubapitest.model

import com.google.gson.annotations.SerializedName

data class SearchRepository (
    @SerializedName("items")
    val items: List<Repository>
)