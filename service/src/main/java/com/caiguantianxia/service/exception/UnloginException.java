package com.caiguantianxia.service.exception;

import com.caiguantianxia.service.dto.BaseErrorCode;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午6:45 19-4-24
 */
public class UnloginException extends BaseException {
    public UnloginException() {
        super(BaseErrorCode.USER_NO_LOGIN);
    }

    public UnloginException(String message) {
        super(BaseErrorCode.USER_NO_LOGIN, message);
    }

    public UnloginException(Integer errorCode, String message) {
        super(errorCode, message);
    }

}
