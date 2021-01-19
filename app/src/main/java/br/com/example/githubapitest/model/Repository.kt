package br.com.example.githubapitest.model

import com.google.gson.annotations.SerializedName

data class Repository (@SerializedName("id") val id: String,
                       @SerializedName("name") val name: String,
                       @SerializedName("owner") val owner: Owner,
                       @SerializedName("html_url") val htmlUrl: String,
                       @SerializedName("stargazers_count") val stargazersCount: Int,
                       @SerializedName("forks_count") val forksCount: Int)