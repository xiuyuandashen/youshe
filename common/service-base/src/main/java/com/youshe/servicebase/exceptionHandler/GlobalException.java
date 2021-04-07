package com.youshe.servicebase.exceptionHandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @Description: 自定义异常类
* @Author: zlf
* @Date: 2021/3/22
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalException extends RuntimeException{

    @ApiModelProperty( value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "异常信息")
    private String msg;

    @Override
    public String toString() {
        return "GuliException{" +
                "message=" + this.getMsg() +
                ", code=" + code +
                '}';
    }
}
