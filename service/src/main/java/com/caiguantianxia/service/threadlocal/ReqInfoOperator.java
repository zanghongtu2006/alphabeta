package com.caiguantianxia.service.threadlocal;

public class ReqInfoOperator {
    private static final ThreadLocal<ReqInfo> REQ_INFO_THREAD_LOCAL = new ThreadLocal<>();

    public static ReqInfo get() {
        return REQ_INFO_THREAD_LOCAL.get();
    }

    public static void set(ReqInfo reqInfoThreadLocal) {
        REQ_INFO_THREAD_LOCAL.set(reqInfoThreadLocal);
    }
}
