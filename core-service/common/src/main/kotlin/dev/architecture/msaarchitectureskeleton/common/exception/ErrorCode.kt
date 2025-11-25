package dev.architecture.msaarchitectureskeleton.common.exception

enum class ErrorCode(val description: String) {
    E400("Bad Request"),
    E401("Unauthorized"),
    E403("Forbidden"),
    E404("Not Found"),
    E409("Conflict"),
    E500("Internal Server Error");
}