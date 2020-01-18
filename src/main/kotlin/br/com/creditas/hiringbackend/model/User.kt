package br.com.creditas.hiringbackend.model

import com.fasterxml.jackson.annotation.JsonProperty

data class User(
    val id : Long,
    val login : String,
    val name : String,
    @JsonProperty("avatar_url") val avatarUrl : String,
    @JsonProperty("html_url") val htmlUrl : String
)