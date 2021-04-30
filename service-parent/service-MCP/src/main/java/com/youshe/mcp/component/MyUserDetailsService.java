package com.youshe.mcp.component;

import com.youshe.commonutils.result.ResultCode;
import com.youshe.mcp.entity.Role;
import com.youshe.mcp.entity.User;
import com.youshe.mcp.service.RoleService;
import com.youshe.mcp.service.UserService;
import com.youshe.servicebase.exceptionHandler.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: zlf
 * @Date: 2021/03/30/23:38
 * @Description:
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 这边可以通过username去获取数据库中的用户信息 如果没有抛出异常
        User user = userService.getUserByName(username);
        if(user == null) throw new GlobalException(ResultCode.ERROR.getCode(), "用户名错误");
        List<GrantedAuthority> authorityList = new ArrayList<>();
        /* 此处查询数据库得到角色权限列表，这里可以用Redis缓存以增加查询速度 */
        Role role = roleService.getById(user.getLevel());
        authorityList.add(new SimpleGrantedAuthority(role.getRoleName()));  // 角色 需要以 ROLE_ 开头
        return new org.springframework.security.core.userdetails.User(username,  new BCryptPasswordEncoder().encode(user.getPassword()), authorityList);
    }
}
