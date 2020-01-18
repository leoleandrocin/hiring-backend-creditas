package br.com.creditas.hiringbackend.client

import br.com.creditas.hiringbackend.infrastructure.exception.ResourceNotFoundException
import br.com.creditas.hiringbackend.model.RepositoriesList
import br.com.creditas.hiringbackend.model.Repository
import br.com.creditas.hiringbackend.model.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

@Component
class GithubClient {

    @Value("\${github.url}")
    lateinit var githubUrl : String

    fun getUserProfile(username : String) : User {
        try {
            val response = RestTemplate().getForEntity("$githubUrl/users/$username", User::class.java)
            return response.body!!
        } catch (restClientException : RestClientException) {
            throw ResourceNotFoundException()
        }
    }

    fun getUserRepositories(username: String): List<Repository> {
        try {
            val response = RestTemplate().getForEntity("$githubUrl/users/$username/repos", RepositoriesList::class.java)
            return response.body!!.toList()
        } catch (restClientException : RestClientException) {
            throw ResourceNotFoundException()
        }
    }

}
