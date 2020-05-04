package com.caiguantianxia.service.exception;

import com.caiguantianxia.service.dto.BaseErrorCode;
import lombok.Data;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午6:34 19-4-24
 */
@Data
public class BaseException extends RuntimeException {

    private Integer errorCode;

    public BaseException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.getCode();
    }

    public BaseException(String message) {
        super(message);
        this.errorCode = BaseErrorCode.ERROR.getCode();
    }

    public BaseException(BaseErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode.getCode();
    }

    public BaseException(BaseErrorCode errorCode, String message, Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode.getCode();
    }

    public BaseException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseException(Integer errorCode, String message, Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
    }

}
