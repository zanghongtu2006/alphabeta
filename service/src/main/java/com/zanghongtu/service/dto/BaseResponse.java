package com.zanghongtu.service.dto;

import java.io.Serializable;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午5:58 19-4-24
 */
public class BaseResponse implements Serializable {
    private Integer code;

    private String message;

    public BaseResponse() {
    }

    public BaseResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse(BaseErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public BaseResponse(BaseErrorCode errorCode, String message) {
        this.code = errorCode.getCode();
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
