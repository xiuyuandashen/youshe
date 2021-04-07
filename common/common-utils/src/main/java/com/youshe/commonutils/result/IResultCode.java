package com.youshe.commonutils.result;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: zlf
 * @Date: 2021/03/26/21:30
 * @Description: 响应码和响应信息定义
 */
public interface IResultCode {

    /**
     *  获取响应码
     * @return 响应码
     */
    Integer getCode();


    /**
     * 获取响应信息
     *
     * @return 响应信息
     */
    String getMsg();
}
