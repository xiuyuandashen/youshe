package com.youshe.mcp.controller;

import com.youshe.commonutils.vo.ResultVo;
import com.youshe.mcp.entity.User;
import com.youshe.mcp.service.UserService;
import com.youshe.mcp.service.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.xml.transform.Result;
import java.security.Security;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: zlf
 * @Date: 2021/04/30/18:03
 * @Description:
 */
@RestController
@RequestMapping("/test")
public class testController {

    @Autowired
    UserService userService;

    @Autowired
    WebSocketServer webSocketServer;

    @GetMapping("test")
    public ResultVo test(){
        UserDetails userDetails = (UserDetails) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.getUserByName(username);
        webSocketServer.sendTo("向客户端推送实时消息",user.getId());
        return ResultVo.ok();
    }

    @GetMapping("/hello")
    public ResultVo hello (@RequestParam("a") String a){
        System.out.println(ResultVo.ok());
        return ResultVo.ok();
    }


}
