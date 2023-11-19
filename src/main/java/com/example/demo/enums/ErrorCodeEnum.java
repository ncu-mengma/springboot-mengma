package com.example.demo.enums;

import com.power.common.interfaces.IMessage;

/**
 * 错误码枚举类
 * @author zhou
 */
public enum ErrorCodeEnum implements IMessage {
    TOKEN_ERROR("5000", "token异常"),
    PARAM_ERROR("5001", "参数异常"),
    //SUCCESS和UNKNOWN_ERROR不可变,CommonResult的ok和fail方法会用到,写在了BaseErrorCode中
    SUCCESS("0000", "succeed"),
    UNKNOWN_ERROR("9999", "系统繁忙，请稍后再试...."),
    NOT_FOUND ("404","Not Found"),
    CONFLICT("409","Conflict"),
    UNAUTHORIZED("401","Unauthorized"),
    TOO_MANY_REQUESTS("429","Too Many Requests"),
    Gone("410","Gone");

    private final String code;
    private final String message;


    ErrorCodeEnum(String errorCode, String errorMessage) {
        this.code = errorCode;
        this.message = errorMessage;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}