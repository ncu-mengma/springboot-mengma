package com.example.demo.exception.hadnler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.SaTokenException;
import com.example.demo.enums.ErrorCodeEnum;
import com.example.demo.exception.CustomException;
import com.power.common.model.CommonResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(NotLoginException.class)
    public CommonResult<?> handlerNotLoginException(NotLoginException e){
        e.printStackTrace();
        int code = e.getCode();
        String msg = switch (code) {
            case 11012-> "Token无效";
            case 11013-> "身份已过期,请重新登录";
            case 11014-> "已被顶下线,请重新登录";
            case 11015-> "已被踢下线,请重新登录";
            case 11016-> "账号已被封禁";
            default -> null;
        };
        return CommonResult.fail(ErrorCodeEnum.TOKEN_ERROR.getCode(),msg);
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public CommonResult<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){
        return  CommonResult.fail(ErrorCodeEnum.PARAM_ERROR.getCode(),"参数校验失败");
    }
    @ExceptionHandler({CustomException.class})
    public CommonResult<?> handlerCustomMethodArgumentNotValidException(CustomException e){
        return CommonResult.fail(e);
    }
    @ExceptionHandler({SaTokenException.class})
    public CommonResult<?> handlerSaTokenException(SaTokenException e){
        int code = e.getCode();
        return CommonResult.fail(ErrorCodeEnum.UNKNOWN_ERROR.getCode(),"sa-Token异常状态码："+code);
    }
}