package com.breze.common.exception;


import com.breze.common.enums.ErrorEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.breze.common.result.Result;

import java.nio.file.AccessDeniedException;

/**
 * @Author tylt6688
 * @Date 2022/2/5 11:57
 * @Description 全局异常处理机制，捕获 Controller 部分
 * @Copyright(c) 2022 , 青枫网络工作室
 */
@Log4j2
@RestControllerAdvice
public class GlobalException extends Throwable {


    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public Result handler(AccessDeniedException e) {
        log.error("security权限不足：----------------{}", e.getMessage());
        return Result.createFailMessage(ErrorEnum.NoPermission, "权限不足");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e) {
        log.error("实体校验异常：----------------{}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return Result.createFailMessage(ErrorEnum.UnknowError, objectError.getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e) {
        log.error("Assert异常：----------------{}", e.getMessage());
        return Result.createFailMessage(ErrorEnum.FindException, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e) {
        log.error("运行时异常：----------------{}", e.getMessage());
        return Result.createFailMessage(ErrorEnum.FindException, e.getMessage());
    }
}
