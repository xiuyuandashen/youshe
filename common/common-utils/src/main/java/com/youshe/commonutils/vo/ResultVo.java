package com.youshe.commonutils.vo;

import com.youshe.commonutils.result.IResultCode;
import com.youshe.commonutils.result.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: zlf
 * @Date: 2021/03/26/21:31
 * @Description:
 */
@ApiModel("统一返回结果")
@Data
public class ResultVo {

    @ApiModelProperty("是否成功")
    private Boolean success;

    @ApiModelProperty("返回码")
    private Integer code;

    @ApiModelProperty("返回信息")
    private String message;

    @ApiModelProperty("返回数据")
    private Map<String,Object> data = new HashMap<>();

    /**
     * 构造方法私有化
     */
    private ResultVo(){}

    /**
     * 成功的静态方法
     * @return ResultVo
     */
    public static ResultVo ok(){
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess(true);
        resultVo.setCode(ResultCode.SUCCESS.getCode());
        resultVo.setMessage(ResultCode.SUCCESS.getMsg());
        return resultVo;
    }

    /**
     * 失败的静态方法
     * @return ResultVo
     */
    public static ResultVo error(){
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess(false);
        resultVo.setCode(ResultCode.ERROR.getCode());
        resultVo.setMessage(ResultCode.ERROR.getMsg());
        return resultVo;
    }


    public ResultVo success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public ResultVo message(String message){
        this.setMessage(message);
        return this;
    }

    public ResultVo code(Integer code){
        this.setCode(code);
        return this;
    }

    public ResultVo data(String key,Object value){
        this.data.put(key,value);
        return this;
    }

    public ResultVo data(Map<String,Object> map){
        this.setData(map);
        return this;
    }

    public ResultVo  codeAndMessage(IResultCode resultCode){
        this.setCode(resultCode.getCode());
        this.setMessage(resultCode.getMsg());
        return this;
    }

}
