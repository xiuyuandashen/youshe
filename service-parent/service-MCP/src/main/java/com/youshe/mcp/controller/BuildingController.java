package com.youshe.mcp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youshe.commonutils.result.ResultCode;
import com.youshe.commonutils.vo.ResultVo;
import com.youshe.mcp.entity.Building;
import com.youshe.mcp.entity.User;
import com.youshe.mcp.entity.vo.BuildingVo;
import com.youshe.mcp.mapper.BuildingMapper;
import com.youshe.mcp.service.BuildingService;
import com.youshe.mcp.service.HousingService;
import com.youshe.mcp.service.UserService;
import com.youshe.servicebase.exceptionHandler.GlobalException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.ArrayList;
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
@RequestMapping("/mcp/building")
@Api(tags = "楼房管理")
public class BuildingController {


    @Autowired
    BuildingService buildingService;

    @Autowired
    UserService userService;

    @Autowired
    HousingService housingService;

    @ApiOperation("获取楼房列表")
    @GetMapping("findBuilding/{page}/{size}")
    public ResultVo findBuilding(@ApiParam(name = "page",value = "当前页") @RequestParam(value = "page",defaultValue = "1") int page,
                                 @ApiParam(name = "size",value = "显示条数") @RequestParam(value = "size",defaultValue = "5") int size){
        Page<Building> buildingPage = new Page<Building>(page, size);
        buildingService.page(buildingPage);
        List<BuildingVo> idList = new ArrayList<>();
        buildingService.findPage(buildingPage,idList);
        return ResultVo.ok().data("buildingList",idList).data("total",buildingPage.getTotal());

    }


    @ApiOperation("根据楼房管理员id获取楼房详细信息")
    @GetMapping("findByBuildingManagerId")
    public ResultVo findBuildingByUserId(@RequestParam("id") String id){
        if (id == null)  return ResultVo.error().message("无该管理员!");
        BuildingVo buildingVo = buildingService.findByBuildingManagerId(id);

        return ResultVo.ok().data("buildingVo",buildingVo);
    }


    @ApiOperation("添加楼房信息")
    @PostMapping("addBuilding")
    public ResultVo addBuilding(@RequestBody Building building){

        boolean save = buildingService.save(building);
        if(!save){
            throw new GlobalException(ResultCode.ERROR.getCode(),"添加失败");
        }
        return ResultVo.ok().message("添加成功！");

    }

    @ApiOperation("更新楼房信息")
    @PostMapping("update")
    public ResultVo updateBuilding(@RequestBody Building building){
        boolean b = buildingService.updateById(building);
        if(!b){
            throw new GlobalException(ResultCode.ERROR.getCode(),"更新失败");
        }
        return ResultVo.ok().message("更新成功!");
    }


    @ApiOperation("根据id逻辑删除楼房")
    @PostMapping("deleteById")
    public ResultVo deleteById( @RequestParam("id") String id){
        boolean b = buildingService.removeById(id);
        if(!b) throw new GlobalException(ResultCode.ERROR.getCode(),"删除失败");
        return ResultVo.ok().message("删除成功！");
    }



}

