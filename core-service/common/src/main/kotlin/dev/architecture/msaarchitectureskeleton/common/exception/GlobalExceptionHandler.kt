package dev.architecture.msaarchitectureskeleton.common.exception

import dev.architecture.msaarchitectureskeleton.common.controller.CommonResponse
import dev.architecture.msaarchitectureskeleton.common.controller.ResponseBuilder
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

private val log = KotlinLogging.logger {}

@RestControllerAdvice
class GlobalExceptionHandler {

    /**
     * @Valid 어노테이션을 통한 유효성 검사 실패 시 발생하는 예외를 처리하는 핸들러
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    private fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<CommonResponse<Map<String, String>>> {
        log.error(e) { "MethodArgumentNotValidException caught" }

        val errors = e.bindingResult.allErrors.associate { error ->
            val field = (error as FieldError).field
            val message = error.defaultMessage ?: "유효하지 않은 값입니다."
            field to message
        }

        return ResponseBuilder.fail(
            message = "VALIDATION_FAILED",
            status = HttpStatus.BAD_REQUEST,
            data = errors
        )
    }

    /**
     * ApplicationException.CommonException 타입의 예외를 처리하는 핸들러
     */
    @ExceptionHandler(ApplicationException.CommonException::class)
    private fun handleCommonException(e: ApplicationException.CommonException): ResponseEntity<CommonResponse<Map<String, Any?>>> {
        // 람다를 사용한 올바른 로깅 방식으로 수정하고 예외(e)도 함께 기록
        log.error(e) { "CommonException caught: ErrorCode - ${e.errorCode}, Message - ${e.message}, Data - ${e.data}" }
        // 삭제되었던 return 문을 다시 추가
        return e.toResponse()
    }

    /**
     * 로그인 시 비밀번호가 일치하지 않을 때 발생하는 예외를 처리하는 핸들러
     */
    @ExceptionHandler(BadCredentialsException::class)
    private fun handleBadCredentialsException(e: BadCredentialsException): ResponseEntity<CommonResponse<Unit>> {
        log.warn { "${"Login failed due to bad credentials: {}"} ${e.message}" }

        return ResponseBuilder.fail(
            message = "AUTH_001", // 예시 에러 코드
            status = HttpStatus.UNAUTHORIZED
        )
    }

    /**
     * 처리되지 않은 모든 예외를 처리하는 핸들러
     */
    @ExceptionHandler(Exception::class)
    private fun handleAllUncaughtException(e: Exception): ResponseEntity<CommonResponse<Unit>> {
        log.error(e) { "Unhandled exception caught" }

        return ResponseBuilder.fail(
            message = "INTERNAL_SERVER_ERROR",
            status = HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}
