package br.com.creditas.hiringbackend.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Repository(
    val id : Long,
    val name : String,
    @JsonProperty("html_url") val htmlUrl : String,
    val description : String?
)

class RepositoriesList : MutableList<Repository> by ArrayList()