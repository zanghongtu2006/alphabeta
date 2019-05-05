package com.zanghongtu.service.exception;

import com.zanghongtu.service.dto.BaseErrorCode;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午6:45 19-4-24
 */
public class TokenExpiredException extends BaseException {
    public TokenExpiredException() {
        super(BaseErrorCode.TOKEN_EXPIRED);
    }

    public TokenExpiredException(String message) {
        super(BaseErrorCode.TOKEN_EXPIRED, message);
    }

    public TokenExpiredException(Integer errorCode, String message) {
        super(errorCode, message);
    }

}
