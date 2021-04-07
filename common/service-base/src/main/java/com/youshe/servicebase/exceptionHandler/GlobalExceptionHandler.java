package com.youshe.servicebase.exceptionHandler;


import com.youshe.commonutils.util.ExceptionUtil;
import com.youshe.commonutils.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
* @Description: 全局异常处理
* @Author: zlf
* @Date: 2021/3/22
*/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResultVo error(Exception e){
        log.error("发生的异常为:{} 异常",e.getMessage());
        return ResultVo.error().data("msg","发生异常");
    }

    @ExceptionHandler(GlobalException.class)
    public ResultVo GlobalException(GlobalException globalException){
        log.error("发生的异常为：{},", ExceptionUtil.getMessage(globalException));
        return ResultVo.error().message(globalException.getMsg()).code(globalException.getCode());
    }

}
