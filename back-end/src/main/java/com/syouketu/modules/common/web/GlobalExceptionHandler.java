package com.syouketu.modules.common.web;

import com.syouketu.modules.common.dto.output.ApiResult;
import com.syouketu.utils.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ApiResult handleBusinessException(BusinessException be) {
        if (be.getStatus() == -1) {
            return ApiResult.expired(be.getMessage());
        }
        return ApiResult.fail(be.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ApiResult handleRuntimeException(RuntimeException e) {
        LOG.error("系统异常", e);
        return ApiResult.fail("系统异常，操作失败");
    }
}
