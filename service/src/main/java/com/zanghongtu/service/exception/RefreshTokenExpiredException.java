package com.zanghongtu.service.exception;

import com.zanghongtu.service.dto.BaseErrorCode;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午6:46 19-4-24
 */
public class RefreshTokenExpiredException extends BaseException {

    public RefreshTokenExpiredException() {
        super(BaseErrorCode.REFRESH_TOKEN_EXPIRED);
    }

    public RefreshTokenExpiredException(String message) {
        super(BaseErrorCode.REFRESH_TOKEN_EXPIRED, message);
    }

    public RefreshTokenExpiredException(Integer errorCode, String message) {
        super(errorCode, message);
    }
}
