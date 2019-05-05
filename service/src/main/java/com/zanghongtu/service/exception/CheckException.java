package com.zanghongtu.service.exception;

import com.zanghongtu.service.dto.BaseErrorCode;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午6:38 19-4-24
 */
public class CheckException extends BaseException {
    public CheckException() {
        super(BaseErrorCode.CHECK_ERROR);
    }

    public CheckException(String message) {
        super(BaseErrorCode.CHECK_ERROR, message);
    }

    public CheckException(String message, Throwable throwable) {
        super(BaseErrorCode.CHECK_ERROR, message, throwable);
    }

    public CheckException(Integer code, String message) {
        super(code, message);
    }
}
