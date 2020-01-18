package br.com.creditas.hiringbackend.infrastructure.exception

import java.lang.RuntimeException

class ResourceNotFoundException(
    override val message: String = "Resource not found exception"
) : RuntimeException(message) {

}