package com.zanghongtu.service.exception;

import com.zanghongtu.service.dto.BaseErrorCode;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午6:46 19-4-24
 */
public class PermitException extends BaseException {
    public PermitException() {
        super(BaseErrorCode.USER_NOT_PERMITTED);
    }

    public PermitException(String message) {
        super(BaseErrorCode.USER_NOT_PERMITTED, message);
    }

    public PermitException(Integer errorCode, String message) {
        super(errorCode, message);
    }
}
