package com.zanghongtu.service.aop;

import com.zanghongtu.service.dto.BaseDTO;
import com.zanghongtu.service.dto.BaseErrorCode;
import com.zanghongtu.service.exception.*;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午5:50 19-4-26
 */
@ControllerAdvice(value = "com.zanghongtu")
public class ApiResponseHandler implements ResponseBodyAdvice {

    private static final Class[] ANNOS = {
            RequestMapping.class,
            GetMapping.class,
            PostMapping.class,
            DeleteMapping.class,
            PutMapping.class
    };

    @Override
    public boolean supports(MethodParameter returnType, Class aClass) {
        AnnotatedElement element = returnType.getAnnotatedElement();
        return Arrays.stream(ANNOS).anyMatch(anno -> anno.isAnnotation() && element.isAnnotationPresent(anno));
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType mediaType,
                                  Class aClass, ServerHttpRequest request, ServerHttpResponse response) {
        BaseDTO baseDTO = new BaseDTO(BaseErrorCode.SUCCESS);
        baseDTO.setData(body);
        return baseDTO;
    }

    @ResponseBody
    @ExceptionHandler
    public BaseDTO errorHandler(Exception ex) {
        return handlerException(ex);
    }

    private BaseDTO<?> handlerException(Exception e) {
        BaseDTO<?> result;

        // 已知异常
        if (e instanceof BusinessException) {
            result = new BaseDTO<>(BaseErrorCode.FAILED);
        } else if (e instanceof CheckException) {
            result = new BaseDTO<>(BaseErrorCode.CHECK_ERROR);
        } else if (e instanceof PermitException) {
            result = new BaseDTO<>(BaseErrorCode.USER_NOT_PERMITTED);
        } else if (e instanceof RefreshTokenExpiredException) {
            result = new BaseDTO<>(BaseErrorCode.REFRESH_TOKEN_EXPIRED);
        } else if (e instanceof TokenExpiredException) {
            result = new BaseDTO<>(BaseErrorCode.TOKEN_EXPIRED);
        } else if (e instanceof UnloginException) {
            result = new BaseDTO<>(BaseErrorCode.USER_NO_LOGIN);
        } else {
            //TODO 未知的异常，应该格外注意，可以发送邮件通知等
            result = new BaseDTO<>(BaseErrorCode.ERROR);
        }
        return result;
    }
}
