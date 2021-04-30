package com.youshe.mcp.controller;

import com.youshe.commonutils.result.ResultCode;
import com.youshe.commonutils.vo.ResultVo;
import com.youshe.mcp.entity.Role;
import com.youshe.mcp.entity.User;
import com.youshe.mcp.entity.form.UserForm;
import com.youshe.mcp.service.RoleService;
import com.youshe.mcp.service.UserService;
import com.youshe.mcp.utils.JwtTokenUtil;
import com.youshe.mcp.utils.RedisUtil;
import com.youshe.servicebase.exceptionHandler.GlobalException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: zlf
 * @Date: 2021/04/08/22:13
 * @Description:
 */
@Api(tags = "登录管理")
@RequestMapping("/user")
@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    UserService userService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RoleService roleService;

    @ApiOperation("登录")
    @PostMapping("login")
    public ResultVo login(@RequestBody UserForm userForm){
        String code = redisUtil.get(userForm.getCaptchaKey());
        System.out.println(code);
        if(code == null || !code.equals(userForm.getCaptchaCode())){
            throw new GlobalException(ResultCode.ERROR.getCode(), "验证码错误或已过期!");
        }
        User user = userService.getUserByName(userForm.getName());
        if(user==null) throw new GlobalException(ResultCode.ERROR.getCode(), "用户名错误");
        if(!userForm.getPassword().equals(user.getPassword())){
            throw new GlobalException(ResultCode.ERROR.getCode(), "密码错误");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("username",user.getName());
        map.put("avatar",user.getAvatar());
        String token = jwtTokenUtil.generateToken(map);
        User user2 = userService.getUserByName(user.getName());
        user2.setPassword(null);
        List<String> authorities  = new ArrayList<>();
        Role byId = roleService.getById(user2.getLevel());
        authorities.add(byId.getRoleName());
        return ResultVo.ok().data("token",tokenHead+" "+token).data("user",user2).data("authorities",authorities);
    }


    @GetMapping("info")
    @PreAuthorize("hasAnyRole('USER')")
    public ResultVo info(HttpServletRequest  request) {
        String authorization = request.getHeader("Authorization");

        String username = jwtTokenUtil.getUserNameFromToken(authorization.substring(this.tokenHead.length()));

        UserDetails userDetails = (UserDetails) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResultVo.ok().data("user",userDetails);

    }

    @PostMapping("logout")
    @PreAuthorize("hasAnyRole('USER')")
    public ResultVo logout(HttpServletRequest  request){

        String authorization = request.getHeader("Authorization");

        //String username = jwtTokenUtil.getUserNameFromToken(authorization.substring(this.tokenHead.length()));


        return ResultVo.ok();
    }

    @GetMapping("captcha")
    @ApiOperation("获取图片验证码")
    public ResultVo captcha(){
        Map<String, String> captcha = com.youshe.mcp.utils.captcha.getCaptcha();
        String key = captcha.get("codeKey");
        String value = captcha.get("captchaCode");
        //System.out.println(key + " " + value);
        redisUtil.set(key, value);
        // 存活120秒
        redisUtil.expire(key,120, TimeUnit.SECONDS);
        return ResultVo.ok().data("captchaKey",key).data("imgUrl",captcha.get("imgUrl"));
    }


}
