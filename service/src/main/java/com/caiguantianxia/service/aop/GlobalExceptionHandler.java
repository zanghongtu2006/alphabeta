package com.caiguantianxia.service.aop;

import com.caiguantianxia.service.dto.BaseDTO;
import com.caiguantianxia.service.dto.BaseErrorCode;
import com.caiguantianxia.service.threadlocal.ReqInfo;
import com.caiguantianxia.service.threadlocal.ReqInfoOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public BaseDTO customException(Exception e) {
        ReqInfo reqInfo = ReqInfoOperator.get();
        if (reqInfo == null) {
            reqInfo = new ReqInfo();
        }
        reqInfo.setErrorCode(500);
        reqInfo.setErrorMsg(e.getMessage());
        ReqInfoOperator.set(reqInfo);
        log.error("RequestId: " + reqInfo.getRequestId() + "Exec failed ", e);
        return new BaseDTO(BaseErrorCode.ERROR.getCode(), e.getMessage());
    }
}
