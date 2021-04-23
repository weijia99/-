package com.atguigu.yygh.common.exception;

import com.atguigu.yygh.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
//    统一异常处理方法
    @ExceptionHandler(Exception.class)
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(HospitalException.class)

    public Result error(HospitalException e) {
        e.printStackTrace();
        return Result.fail();
    }
}
