package com.syouketu.utils;

/**
 * BusinessException
 * 业务逻辑错误
 *
 * @author xiaojie
 * @date 2017/4/13
 */
public class BusinessException extends RuntimeException {

    /**
     *状态: 0 异常 -1 TOKEN过期
     */
    private int status = 0;

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, int status) {
        super(message);
        this.status = status;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getStatus() {
        return status;
    }
}
