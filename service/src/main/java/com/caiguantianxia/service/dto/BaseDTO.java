package com.caiguantianxia.service.dto;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午5:09 19-4-24
 */
public class BaseDTO<T> extends BaseResponse {
    private T data;

    public BaseDTO(T data) {
        super(BaseErrorCode.SUCCESS);
        this.data = data;
    }

    public BaseDTO(BaseErrorCode errorCode) {
        super(errorCode);
    }

    public BaseDTO(Integer code, String message) {
        super(code, message);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SimpleResponse{" +
                "data=" + data +
                "} " + super.toString();
    }
}
