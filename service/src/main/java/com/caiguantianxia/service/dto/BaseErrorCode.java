package com.caiguantianxia.service.dto;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午6:03 19-4-24
 */
public enum BaseErrorCode {
    /**
     * 成功
     */
    SUCCESS(0, "SUCCESS"),

    /**
     * 业务失败异常
     */
    FAILED(500, "Business Error, Please contact Admin"),

    /**
     * 未登录
     */
    USER_NO_LOGIN(500, "User is not login"),

    /**
     * 未知异常
     */
    ERROR(500, "Internal Error, Please contact Admin"),


    /**
     * Token失效
     */
    TOKEN_EXPIRED(500, "Token Expired"),

    /**
     * 刷新Token失败
     */
    REFRESH_TOKEN_EXPIRED(500, "refresh token expired"),

    /**
     * 用户无权限
     */
    USER_NOT_PERMITTED(500, "User do not have this permit"),

    /**
     * 参数检查异常
     */
    CHECK_ERROR(500, "Input params check failed");

    private Integer code;

    private String message;

    BaseErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
