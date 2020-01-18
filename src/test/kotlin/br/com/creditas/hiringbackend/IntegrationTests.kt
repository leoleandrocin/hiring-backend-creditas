package br.com.creditas.hiringbackend

import br.com.creditas.hiringbackend.databuilder.UserDataBuilder
import br.com.creditas.hiringbackend.infrastructure.ErrorMessage
import br.com.creditas.hiringbackend.model.RepositoriesList
import br.com.creditas.hiringbackend.model.User
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [WireMockInitializer::class])
@ExtendWith(SpringExtension::class)
class IntegrationTests {

    @Autowired
    lateinit var wireMockServer : WireMockServer

    @Autowired
    lateinit var testRestTemplate : TestRestTemplate

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

        val responseEntity = testRestTemplate.getForEntity("/users/{username}", User::class.java, "mirjahal")

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertEquals(UserDataBuilder.userData(), responseEntity.body)
    }

    @Test
    fun `given a invalid username when get user by username then return status code not found`() {
        wireMockServer.stubFor(
            WireMock
                .get("/users/@@@@@@@@")
                .willReturn(WireMock.aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBodyFile("githubUsersProfileNotFound.json")
                )
        )

        val responseEntity = testRestTemplate.getForEntity("/users/{username}", ErrorMessage::class.java, "@@@@@@@@")

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.statusCode)
        assertEquals(ErrorMessage("Resource not found exception"), responseEntity.body)
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

        val responseEntity = testRestTemplate.getForEntity("/users/{username}/repos", RepositoriesList::class.java, "mirjahal")

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertEquals(UserDataBuilder.userRepositories(), responseEntity.body)
    }

}
