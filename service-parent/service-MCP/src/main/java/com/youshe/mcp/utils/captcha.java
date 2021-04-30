package com.youshe.mcp.utils;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: zlf
 * @Date: 2021/04/27/18:50
 * @Description:
 */
public class captcha {

    /**
    * @Description: 生成验证码
    * @Param: []
    * @return: java.util.Map<java.lang.String,java.lang.String>
    * @Author: zlf
    * @Date: 2021/4/27
    */
    public static Map<String,String> getCaptcha(){
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 50);
        String code  = lineCaptcha.getCode();
        // redis  存储 code
        String key = UUID.randomUUID().toString().replace("-","");
        String imageBase64 = lineCaptcha.getImageBase64();
        Map<String,String> map = new HashMap<>();
        // 保存存在redis中的key 以及value
        map.put("codeKey",key);
        map.put("captchaCode",code);
        map.put("imgUrl","data:image/png;base64," + imageBase64);
        return map;
    }

}
