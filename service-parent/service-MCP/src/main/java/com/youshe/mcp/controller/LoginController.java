package com.youshe.mcp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youshe.commonutils.result.ResultCode;
import com.youshe.commonutils.vo.ResultVo;
import com.youshe.mcp.entity.Role;
import com.youshe.mcp.entity.User;
import com.youshe.mcp.entity.form.UserForm;
import com.youshe.mcp.mapper.UserMapper;
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
    @Autowired
    UserMapper userMapper;

    @PostMapping("/admin/login")
    public ResultVo adminLogin(@RequestBody UserForm userForm){
        User user = userService.getUserByName(userForm.getName());
        if(user==null) throw new GlobalException(ResultCode.ERROR.getCode(), "用户名错误");
        if(!userForm.getPassword().equals(user.getPassword())){
            throw new GlobalException(ResultCode.ERROR.getCode(), "密码错误");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("username",user.getName());
        map.put("avatar",user.getAvatar());
        String token = jwtTokenUtil.generateToken(map);
        return ResultVo.ok().data("token",tokenHead+" "+token);
    }


    @ApiOperation("登录")
    @PostMapping("login")
    public ResultVo login(@RequestBody UserForm userForm){
        System.out.println(userForm);
        String code = redisUtil.get(userForm.getCaptchaKey());
//        System.out.println(code);
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
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResultVo info(HttpServletRequest  request) {
        String token = request.getParameter("token");
//        System.out.println(token);
        String authorization = request.getHeader("Authorization");

        String username = jwtTokenUtil.getUserNameFromToken(authorization.substring(this.tokenHead.length()));
        User user = userService.getUserByName(username);
        List<String> authorities  = new ArrayList<>();
        Role byId = roleService.getById(user.getLevel());
        authorities.add(byId.getRoleName());

//        UserDetails userDetails = (UserDetails) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResultVo.ok()
                .data("roles",authorities)
                .data("avatar",user.getAvatar())
                .data("name",user.getName())
                .data("userId",user.getId());

    }

    @PostMapping("logout")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResultVo logout(HttpServletRequest  request){

        String authorization = request.getHeader("Authorization");

        //String username = jwtTokenUtil.getUserNameFromToken(authorization.substring(this.tokenHead.length()));
        return ResultVo.ok();
    }

    @ApiOperation("刷新token")
    @GetMapping("/RefreshToken")
    public ResultVo getRefreshToken(@RequestParam("token") String token){
//        System.out.println(token);
        String newToken = jwtTokenUtil.refreshToken(token.substring(this.tokenHead.length()));
        return ResultVo.ok().data("token",this.tokenHead+" "+ newToken);
    }

    @GetMapping("captcha")
    @ApiOperation("获取图片验证码")
    public ResultVo captcha(){
        Map<String, String> captcha = com.youshe.mcp.utils.captcha.getCaptcha();
        String key = captcha.get("codeKey");
        String value = captcha.get("captchaCode");
//        System.out.println(key + " " + value);
        redisUtil.set(key, value);
        // 存活120秒
        redisUtil.expire(key,120, TimeUnit.SECONDS);
        return ResultVo.ok().data("captchaKey",key).data("imgUrl",captcha.get("imgUrl"));
    }

    @GetMapping("test1")
    public ResultVo test1(){

//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("name","丁辉");
//        User user = userMapper.selectOne(queryWrapper);
        //List<User> users = userMapper.selectList(null);
        //System.out.println(users);
//        System.out.println(user);
        User user = userMapper.getByName("abc");
        return ResultVo.ok().data("list",user);
    }


}
