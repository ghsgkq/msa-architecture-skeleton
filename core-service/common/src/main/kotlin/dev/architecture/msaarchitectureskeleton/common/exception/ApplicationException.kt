package dev.architecture.msaarchitectureskeleton.common.exception

import dev.architecture.msaarchitectureskeleton.common.controller.CommonResponse
import dev.architecture.msaarchitectureskeleton.common.controller.ResponseBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

/**
 * 애플리케이션에서 사용할 최상위 예외 클래스
 */
abstract class ApplicationException(
    open val errorCode: ErrorCode,
    open val errorMessage: ErrorMessage?,
    open val data: Map<String, String>? = null
) : RuntimeException(errorMessage?.message()) {

    /**
     * 일반적인 예외 상황에서 사용되는 클래스
     */
    class CommonException(
        override val errorCode: ErrorCode,
        override val errorMessage: ErrorMessage?,
        override val data: Map<String, String>? = null
    ) : ApplicationException(errorCode, errorMessage, data) {

        fun toResponse(): ResponseEntity<CommonResponse<Map<String, Any?>>> {
            val status = when (errorCode) {
                ErrorCode.E400 -> HttpStatus.BAD_REQUEST
                ErrorCode.E401 -> HttpStatus.UNAUTHORIZED
                ErrorCode.E403 -> HttpStatus.FORBIDDEN
                ErrorCode.E404 -> HttpStatus.NOT_FOUND
                ErrorCode.E409 -> HttpStatus.CONFLICT
                ErrorCode.E500 -> HttpStatus.INTERNAL_SERVER_ERROR
            }

            // 응답의 message 필드에는 에러 코드를 문자열로 전달
            val code = errorMessage?.toString() ?: errorCode.description

            // 응답의 data 필드에는 상세 메시지와 추가 정보를 담음
            val dataPayload = mutableMapOf<String, Any?>()
            errorMessage?.message()?.let { dataPayload["message"] = it }

            if (!data.isNullOrEmpty()) {
                dataPayload["details"] = data
            }

            return ResponseBuilder.fail(
                message = code,
                status = status,
                data = dataPayload.ifEmpty { null }
            )
        }
    }
}