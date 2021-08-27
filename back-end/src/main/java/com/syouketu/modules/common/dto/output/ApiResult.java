package com.syouketu.modules.common.dto.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * API返回参数
 *
 * @author: JXI
 * @description:
 * @date: Created on 2017/11/15
 */

@ApiModel(value = "API返回参数")
public class ApiResult {
    // 消息内容
    @ApiModelProperty(value = "响应消息", required = false)
    private String message;

    // 成功或有效为1，失败或无效为0，token过期
    @ApiModelProperty(value = "结果码（1：成功；0：失败；-1：token过期）", required = true)
    private Integer status;

    // 响应中的数据
    @ApiModelProperty(value = "响应数据", required = false)
    private Object data;

    public static ApiResult expired(String message) {
        return new ApiResult(-1, message, null);
    }

    public static ApiResult fail(String message) {
        return new ApiResult(0, message, null);
    }

    public static ApiResult ok(String message) {
        return new ApiResult(1, message, null);
    }

    public static ApiResult ok() {
        return new ApiResult(1, "OK", null);
    }

    public static ApiResult build(Integer status, String msg, Object data) {
        return new ApiResult(1, msg, data);
    }

    public static ApiResult ok(String message, Object data) {
        return new ApiResult(1, message, data);
    }

    public ApiResult() {

    }

    public static ApiResult build(Integer status, String msg) {
        return new ApiResult(status, msg, null);
    }

    public ApiResult(Integer status, String msg, Object data) {
        this.status = status;
        this.message = msg;
        this.data = data;
    }

    public ApiResult(Object data) {
        this.status = 1;
        this.message = "OK";
        this.data = data;
    }

    public ApiResult(String message) {
        this(1, message, null);
    }

    public ApiResult(String message, Integer status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
