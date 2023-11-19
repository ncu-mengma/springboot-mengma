package com.example.demo.exception;

import com.power.common.interfaces.IMessage;

public class CustomException extends RuntimeException implements IMessage{
    private String code;
    private String msg;
    public CustomException(String code,String msg){
        super(msg);
        this.code=code;
        this.msg=msg;
    }
    public CustomException(IMessage message){
        super(message.getMessage());
        this.code=message.getCode();
        this.msg=message.getMessage();
    }
    @Override
    public String getCode() {
        return code;
    }
    @Override
    public String getMessage() {
        return msg;
    }
}
