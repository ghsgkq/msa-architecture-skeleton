package dev.architecture.msaarchitectureskeleton.common.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class CommonResponse<T>(
    val status: Int,
    val message: String,
    val data: T? = null
)

object ResponseBuilder {
    fun <T> success(
        data: T,
        message: String = "SUCCESS",
        status: HttpStatus = HttpStatus.OK
    ): ResponseEntity<CommonResponse<T>> {
        return ResponseEntity
            .status(status)
            .body(
                CommonResponse(
                    status = status.value(),
                    message = message,
                    data = data
                )
            )
    }

    fun success(
        message: String = "SUCCESS",
        status: HttpStatus = HttpStatus.OK
    ): ResponseEntity<CommonResponse<Unit>> {
        return ResponseEntity
            .status(status)
            .body(
                CommonResponse(
                    status = status.value(),
                    message = message
                )
            )
    }

    fun <T> fail(
        message: String,
        status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
        data: T? = null
    ): ResponseEntity<CommonResponse<T>> {
        return ResponseEntity
            .status(status)
            .body(
                CommonResponse(
                    status = status.value(),
                    message = message,
                    data = data
                )
            )
    }
}
