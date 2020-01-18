package br.com.creditas.hiringbackend.service.impl

import br.com.creditas.hiringbackend.model.User
import br.com.creditas.hiringbackend.client.GithubClient
import br.com.creditas.hiringbackend.model.Repository
import br.com.creditas.hiringbackend.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {

    @Autowired
    lateinit var githubClient : GithubClient

    override fun getUserByUsername(username: String): User {
        return githubClient.getUserProfile(username)
    }

    override fun getUserRepositoriesByUsername(username: String): List<Repository> {
        return githubClient.getUserRepositories(username)
    }

}