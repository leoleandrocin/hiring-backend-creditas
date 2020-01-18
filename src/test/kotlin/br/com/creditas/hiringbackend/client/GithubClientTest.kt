package br.com.creditas.hiringbackend.client

import br.com.creditas.hiringbackend.WireMockInitializer
import br.com.creditas.hiringbackend.databuilder.UserDataBuilder
import br.com.creditas.hiringbackend.infrastructure.exception.ResourceNotFoundException
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(initializers = [WireMockInitializer::class])
class GithubClientTest {

    @Autowired
    lateinit var wireMockServer : WireMockServer

    @Autowired
    lateinit var githubClient: GithubClient

    @AfterEach
    fun afterEach() {
        wireMockServer.resetAll()
    }

    @Test
    fun `given a valid username when get user by username then return user data`() {
        wireMockServer.stubFor(
            WireMock
                .get("/users/mirjahal")
                .willReturn(WireMock.aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBodyFile("githubUsersProfileSuccess.json")
                )
        )

        val user = githubClient.getUserProfile("mirjahal")

        assertEquals(UserDataBuilder.userData(), user)
    }

    @Test
    fun `given a invalid username when get user by username then throw ResourceNotFoundException`() {
        wireMockServer.stubFor(
            WireMock
                .get("/users/@@@@@@@@")
                .willReturn(WireMock.aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBodyFile("githubUsersProfileNotFound.json")
                )
        )

        val exception = assertThrows(ResourceNotFoundException::class.java) {
            githubClient.getUserProfile("@@@@@@@@")
        }

        assertEquals("Resource not found exception", exception.message)
    }

    @Test
    fun `given a valid username when get user repositories by username then return a list of repositories`() {
        wireMockServer.stubFor(
            WireMock
                .get("/users/mirjahal/repos")
                .willReturn(WireMock.aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBodyFile("githubUsersReposSuccess.json")
                )
        )

        val repositories = githubClient.getUserRepositories("mirjahal")

        assertEquals(UserDataBuilder.userRepositories(), repositories)
    }

}