package com.kotlin.test.kotlintest.global.exception.enums

enum class ErrorCode(
    val status: Int,
    val cause: String
) {
    USER_NOT_FOUND(404, "User Not Found"),
    PASSWORD_NOT_MATCH(404, "Password Not Match"),
    USER_ALREADY_EXIST(409, "User Already Exist"),
    INVALID_TOKEN(401, "Invalid Token"),
    TOKEN_EXPIRED(401, "Token Expired"),
    POST_NOT_ACCESSIBLE(403, "Not Accessible"),
    POST_NOT_FOUND(404, "Post Not Found"),
    METHOD_ARGUMENT(400, "Invalid Method Argument"),
    NO_HANDLER(404, "No Controller Handler"),
    UNEXPECTED_EXCEPTION(500, "Unexpected Exception")
}