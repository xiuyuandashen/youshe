package com.youshe.mcp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youshe.commonutils.result.ResultCode;
import com.youshe.commonutils.vo.ResultVo;
import com.youshe.mcp.entity.User;
import com.youshe.mcp.entity.vo.BuildingVo;
import com.youshe.mcp.service.BuildingService;
import com.youshe.mcp.service.OssService;
import com.youshe.mcp.service.UserService;
import com.youshe.servicebase.exceptionHandler.GlobalException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zlf
 * @since 2021-04-05
 */
@RestController
@RequestMapping("/mcp/user")
@Api(tags = "用户管理")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    OssService ossService;

    @Autowired
    BuildingService buildingService;

    @ApiOperation("获取用户列表")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("findUser/{page}/{size}")
    public ResultVo findUser(@ApiParam(name = "page",value = "当前页") @PathVariable(value = "page") int page
            ,@ApiParam(name = "size",value = "显示条数") @PathVariable(value = "size") int size){

        Page<User> pageList = new Page<User>(page,size);
        userService.page(pageList);
        return ResultVo.ok().data("userList",pageList.getRecords()).data("total",pageList.getTotal()).data("current",pageList.getCurrent());
    }


    @ApiOperation("添加用户")
    @PreAuthorize("hasAnyRole('ADMIN','BUILDINGADMIN')")
    @PostMapping("addUser")
    public ResultVo addUser(@RequestBody User user) throws IOException {

//        System.out.println(user);
       if(userService.getUserByName(user.getName())!=null) throw new GlobalException(ResultCode.ERROR.getCode(),"用户名重复！");
        boolean save = userService.save(user);
        if(!save){
            throw new GlobalException(ResultCode.ERROR.getCode(),"添加失败");
        }

        return ResultVo.ok().message("添加成功!");
    }

    @ApiOperation("更新用户")
    @PostMapping("update")
    public ResultVo update(@RequestBody User user){
        boolean b = userService.updateById(user);
        if(!b){
            throw new GlobalException(ResultCode.ERROR.getCode(),"更新失败");
        }
        return ResultVo.ok().message("更新成功!");
    }

    @ApiOperation("逻辑删除")
    @DeleteMapping("delete")
    public ResultVo deleteById(@RequestParam("id") String id){
        boolean b = userService.removeById(id);
        if(!b){
            throw new GlobalException(ResultCode.ERROR.getCode(),"删除失败");
        }
        return ResultVo.ok().message("删除成功!");
    }

    /**
    * @Description: 获取社区人员数量
    * @Param: 
    * @return: 
    * @Author: zlf
    * @Date: 2021/4/6
    */
    @ApiOperation("统计业主人数")
    @GetMapping("selectCountUser")
    public ResultVo selectCountUser(){
        List<String> strings = userService.selectCountUser();
        return ResultVo.ok().data("userSize",strings.size());
    }


    @ApiOperation("统计该楼房业主人数")
    @GetMapping("selectCountBuildingUser/{BuildingManagerId}")
    public ResultVo selectCountBuildingUser(@ApiParam(name = "BuildingManagerId",value = "楼房管理员id") @PathVariable("BuildingManagerId") String BuildingManagerId){
        User user = userService.getById(BuildingManagerId);
        if (user == null) throw new GlobalException(ResultCode.ERROR.getCode(),"管理员不存在");
        BuildingVo buildingVo = buildingService.findByBuildingManagerId(user.getId());
        List<String> strings = userService.selectCountBuildingUser(buildingVo.getId());
        return ResultVo.ok().data("userSize",strings.size());
    }

    @ApiOperation("根据id获取用户")
    @GetMapping("/getUserById/{id}")
    public ResultVo getUserById(@PathVariable("id") String id){
        User user = userService.getById(id);
        System.out.println(user);
        return  ResultVo.ok()
                .data("userInfo",user);
    }

    @ApiOperation("上传头像")
    @PostMapping("/uploadAvatar")
    public ResultVo uploadAvatar(MultipartFile file){
        String url = null;
        try {
            url = ossService.uploadFileAvatar(file);
        }catch (Exception e){
            throw new GlobalException(ResultCode.ERROR.getCode(), "上传失败！");
        }
        return ResultVo.ok().data("url",url);
    }

    @ApiOperation("获取楼房管理员列表")
    @GetMapping("getBuildingManagerIdList")
    public ResultVo getBuildingManagerIdList(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("level",2);
        List<User> list = userService.list(queryWrapper);
        return ResultVo.ok().data("list",list);
    }
    @ApiOperation("根据用户名模糊查询用户id以及名字")
    @GetMapping("blurFindUserByName")
    public ResultVo blurFindUserByName(@RequestParam(name = "name") String name){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name); // % name %
        queryWrapper.select("id","name");
        List<Object> list = userService.listObjs(queryWrapper);
        return ResultVo.ok().data("list",list);

    }

}

