package br.com.creditas.hiringbackend.controller

import br.com.creditas.hiringbackend.model.Repository
import br.com.creditas.hiringbackend.model.User
import br.com.creditas.hiringbackend.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UsersController{

    @Autowired
    lateinit var userService: UserService

    @GetMapping("/{username}")
    fun getUserProfile(@PathVariable username : String) : ResponseEntity<User> {
        val user = userService.getUserByUsername(username)

        return ResponseEntity.ok(user)
    }

    @GetMapping("/{username}/repos")
    fun getUserRepositories(@PathVariable username : String) : ResponseEntity<List<Repository>> {
        val userRepositories = userService.getUserRepositoriesByUsername(username)

        return ResponseEntity.ok(userRepositories)
    }

}