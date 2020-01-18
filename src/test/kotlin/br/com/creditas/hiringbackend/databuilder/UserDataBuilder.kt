package br.com.creditas.hiringbackend.databuilder

import br.com.creditas.hiringbackend.model.Repository
import br.com.creditas.hiringbackend.model.User

object UserDataBuilder {

    fun userData() = User(
        3357439,
        "mirjahal",
        "Almir Jr.",
        "https://avatars3.githubusercontent.com/u/3357439?v=4",
        "https://github.com/mirjahal"
    )

    fun userRepositories() = listOf(
        Repository(36698987, "ambiente-php-vagrant", "https://github.com/mirjahal/ambiente-php-vagrant", "Ambiente de desenvolvimento PHP construído com Vagrant"),
        Repository(48911376, "contact-me", "https://github.com/mirjahal/contact-me", "A Laravel Application for storing all your contacts")
    )

}