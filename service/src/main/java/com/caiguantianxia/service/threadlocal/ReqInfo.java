package com.caiguantianxia.service.threadlocal;

import lombok.Data;

@Data
public class ReqInfo {
    private String requestId;

    private int errorCode;

    private String errorMsg;
}
