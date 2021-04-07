package com.youshe.mcp.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youshe.commonutils.result.ResultCode;
import com.youshe.commonutils.vo.ResultVo;
import com.youshe.mcp.entity.User;
import com.youshe.mcp.entity.vo.BuildingVo;
import com.youshe.mcp.service.BuildingService;
import com.youshe.mcp.service.UserService;
import com.youshe.servicebase.exceptionHandler.GlobalException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
public class UserController {

    @Autowired
    UserService userService;


    @Autowired
    BuildingService buildingService;

    @ApiOperation("获取用户列表")
    @GetMapping("findUser/{page}/{size}")
    public ResultVo findUser(@ApiParam(name = "page",value = "当前页") @RequestParam(value = "page",defaultValue = "1") int page,@ApiParam(name = "size",value = "显示条数") @RequestParam(value = "size",defaultValue = "5") int size){

        Page<User> pageList = new Page<User>(page,size);
        userService.page(pageList);
        return ResultVo.ok().data("userList",pageList.getRecords()).data("total",pageList.getTotal());
    }


    @ApiOperation("添加用户")
    @PostMapping("addUser")
    public ResultVo addUser(@RequestBody User user){
        boolean save = userService.save(user);
        if(!save){
            throw new GlobalException(ResultCode.ERROR.getCode(),"添加失败");
        }
        //System.out.println(user);
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
}

