package com.caiguantianxia.service.exception;

import com.caiguantianxia.service.dto.BaseErrorCode;

/**
 * Created by yulm1 on 2017/6/1.
 */
public class BusinessException extends BaseException {
    public BusinessException() {
        super(BaseErrorCode.FAILED);
    }

    public BusinessException(String message) {
        super(BaseErrorCode.FAILED, message);
    }

    public BusinessException(Integer errorCode, String message) {
        super(errorCode, message);
    }
}
