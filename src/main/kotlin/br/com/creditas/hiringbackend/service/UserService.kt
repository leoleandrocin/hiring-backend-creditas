package br.com.creditas.hiringbackend.service

import br.com.creditas.hiringbackend.model.Repository
import br.com.creditas.hiringbackend.model.User

interface UserService {
    fun getUserByUsername(username: String): User
    fun getUserRepositoriesByUsername(username: String): List<Repository>
}