package com.youshe.mcp.entity.form;

import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: zlf
 * @Date: 2021/04/27/22:59
 * @Description: user表单类
 */
@Data
public class UserForm implements Serializable {

    private static final  long serialVersionUID = 1L;
    private String name ;
    private String password;
    private String captchaKey;
    private String captchaCode;
}
