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
    BOARD_NOT_FOUND(404, "Board Not Found"),
    COMMENT_NOT_FOUND(404, "Comment Not Found"),
    REPORT_USER_NOT_FOUND(404, "Report User Not Found"),
    REPORT_COMMENT_NOT_FOUND(404, "Report Comment Not Found"),
    REPORT_BOARD_NOT_FOUND(404, "Report Board Not Found"),
    EMAIL_NOT_FOUND(404, "Email Not Found"),
    EMAIL_MISS_MATCHED(409, "Email Miss Match"),
    CODE_NOT_FOUND(404, "Code Not Found"),
    CODE_EXPIRED(401, "Code Expired"),
    ACCOUNT_ID_NOT_CHECK(401, "AccountId Not Check"),
    PASSWORD_MISS_MATCHED(409, "Password Miss Match"),
    ORIGIN_PASSWORD_MISS_MATCHED(409, "Origin Password Miss Match"),
    CHANGE_PASSWORD_MISS_MATCHED(409, "Change Password Miss Match"),

    CODE_MISS_MATCHED(409,"Code Miss Match"),

    EXIST_EMAIL(401, "Exist Email"),
    EXIST_USER(401, "Exist User"),

    IMAGE_NOT_FOUND(404, "Image Not Found"),
    INVALID_IMAGE_EXTENSION(401, "Invalid Image Extension"),

    NOTIFICATION_NOT_FOUND(404, "Notification Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    JWT_EXPIRED(401, "Jwt Expired"),
    JWT_INVALID(401, "Jwt Invalid");

    private final Integer httpStatus;

    private final String message;
}
