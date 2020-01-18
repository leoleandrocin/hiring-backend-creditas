package br.com.creditas.hiringbackend.service

import br.com.creditas.hiringbackend.databuilder.UserDataBuilder
import br.com.creditas.hiringbackend.infrastructure.exception.ResourceNotFoundException
import br.com.creditas.hiringbackend.client.GithubClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
class UserServiceTest {

    @Autowired
    lateinit var userService: UserService

    @MockBean
    lateinit var githubClient: GithubClient

    @Test
    fun `given a valid username when get user by username then return user data`() {
        val userExcpected = UserDataBuilder.userData()
        `when`(githubClient.getUserProfile(anyString())).thenReturn(userExcpected)

        val user = userService.getUserByUsername("mirjahal")

        assertEquals(userExcpected, user)
    }

    @Test()
    fun `given a invalid username when get user by username then throw ResourceNotFoundException`() {
        `when`(githubClient.getUserProfile(anyString())).thenThrow(ResourceNotFoundException())

        val exception = assertThrows(ResourceNotFoundException::class.java) {
            userService.getUserByUsername("@@@@@@@@")
        }

        assertEquals("Resource not found exception", exception.message)
    }

    @Test
    fun `given a valid username when get user repositories by username then return a list of repositories`() {
        val userRepositoriesExcpected = UserDataBuilder.userRepositories()
        `when`(githubClient.getUserRepositories(anyString())).thenReturn(userRepositoriesExcpected)

        val repositories = userService.getUserRepositoriesByUsername("mirjahal")

        assertEquals(userRepositoriesExcpected, repositories)
    }

}