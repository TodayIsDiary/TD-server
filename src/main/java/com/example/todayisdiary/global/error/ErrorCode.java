package com.example.todayisdiary.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    BAD_REQUEST(400, "Bad Request"),

    USER_NOT_FOUND(404, "User Not Found"),
    USER_ALREADY_EXISTS(409, "User Already Exists"),
    USER_MISS_MATCHED(409, "User Miss Match"),
    ROLE_NOT_FOUND(404,"Role Not Found"),
    EMAIL_NOT_FOUND(404, "Email Not Found"),
    CODE_NOT_FOUND(404, "Code Not Found"),
    PASSWORD_MISS_MATCHED(409, "Password Miss Match"),

    IMAGE_NOT_FOUND(404, "Image Not Found"),
    INVALID_IMAGE_EXTENSION(401, "Invalid Image Extension"),

    NOTIFICATION_NOT_FOUND(404, "Notification Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    JWT_EXPIRED(401, "Jwt Expired"),
    JWT_INVALID(401, "Jwt Invalid");

    private final Integer httpStatus;

    private final String message;
}
