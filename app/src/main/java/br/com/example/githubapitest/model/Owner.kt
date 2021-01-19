package br.com.example.githubapitest.model

import com.google.gson.annotations.SerializedName

data class Owner (
    @SerializedName("login")
    var login: String?,
    @SerializedName("avatar_url")
    val avatar_url: String?
)