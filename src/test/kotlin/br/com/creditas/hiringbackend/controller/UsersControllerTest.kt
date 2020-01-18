package br.com.creditas.hiringbackend.controller

import br.com.creditas.hiringbackend.databuilder.UserDataBuilder
import br.com.creditas.hiringbackend.infrastructure.ErrorMessage
import br.com.creditas.hiringbackend.infrastructure.exception.ResourceNotFoundException
import br.com.creditas.hiringbackend.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(UsersController::class)
@ExtendWith(SpringExtension::class)
class UsersControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var mapper: ObjectMapper

    @MockBean
    lateinit var userService: UserService

    @Test
    fun `given a valid username when request user profile data then return user data`() {
        val user = UserDataBuilder.userData()

        `when`(userService.getUserByUsername(anyString())).thenReturn(user)

        mockMvc.get("/users/${user.login}") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json(mapper.writeValueAsString(user)) }
        }
    }

    @Test
    fun `given a invalid username when request user profile data then return status not found`() {
        val resourceNotFoundException = ResourceNotFoundException()
        val errorMessage = ErrorMessage(resourceNotFoundException.message)

        `when`(userService.getUserByUsername(anyString())).thenThrow(resourceNotFoundException)

        mockMvc.get("/users/@@@@@@@@") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json(mapper.writeValueAsString(errorMessage)) }
        }
    }

    @Test
    fun `given a valid username when request user repositories then return a list of repositories`() {
        val repositories = UserDataBuilder.userRepositories()

        `when`(userService.getUserRepositoriesByUsername(anyString())).thenReturn(repositories)

        mockMvc.get("/users/mirjahal/repos") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json(mapper.writeValueAsString(repositories)) }
        }
    }

}