package com.youshe.mcp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youshe.commonutils.result.ResultCode;
import com.youshe.commonutils.vo.ResultVo;
import com.youshe.mcp.entity.Building;
import com.youshe.mcp.entity.Housing;
import com.youshe.mcp.entity.vo.HousingVo;
import com.youshe.mcp.service.BuildingService;
import com.youshe.mcp.service.HousingService;
import com.youshe.servicebase.exceptionHandler.GlobalException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/mcp/housing")
@Api(tags = "房屋管理")
@CrossOrigin
public class HousingController {

    @Autowired
    HousingService housingService;

    @Autowired
    BuildingService buildingService;

    @ApiOperation("根据id查找房屋信息")
    @GetMapping("/findById/{id}")
    public ResultVo findById(@PathVariable("id") String id){
        HousingVo housingVo = housingService.findById(id);
        return ResultVo.ok().data("housing",housingVo);
    }

    @ApiOperation("根据用户id查找房屋信息")
    @GetMapping("/findByUserId/{userId}")
    public ResultVo findByUserId(@PathVariable("userId") String userId){
        List<HousingVo> housingVos = housingService.findByUserId(userId);
        return ResultVo.ok().data("housingVos",housingVos);
    }

    @ApiOperation("根据房屋单元号查找房屋信息")
    @GetMapping("/findByRoomNumber")
    public ResultVo findByRoomNumber(@RequestParam("roomNumber") String roomNumber ){
        HousingVo housingVo = housingService.findByRoomNumber(roomNumber);
        return ResultVo.ok().data("housingVo",housingVo);
    }


    @ApiOperation("修改房屋信息(只要改房主)")
    @PostMapping("updateById")
    public ResultVo updateById(@RequestBody Housing housing){
        boolean b = housingService.updateById(housing);
        if (!b) throw new GlobalException(ResultCode.ERROR.getCode(),"更新失败");
        return ResultVo.ok().message("更新成功");
    }

    @ApiOperation("逻辑删除房屋")
    @DeleteMapping("/delete/{id}")
    public ResultVo deleteById(@PathVariable("id") String id){
        boolean b = housingService.removeById(id);
        if(!b) throw new GlobalException(ResultCode.ERROR.getCode(),"删除失败");
        return ResultVo.ok().message("删除成功!");
    }

    @ApiOperation("根据楼房管理id添加房屋")
    @PostMapping("save/{buildingManagerId}")
    public ResultVo save(@ApiParam(name = "buildingManagerId",value = "楼房管理员id") @PathVariable("buildingManagerId") String buildingManagerId,
                         @ApiParam(name = "housing",value = "房屋信息") @RequestBody Housing housing){
        // 根据管理员id查询所管理的楼房
        Building building = buildingService.getBuildingByManagerId(buildingManagerId);
        // 绑定所属的楼房id
        housing.setBuildingId(building.getId());
        boolean save = housingService.save(housing);
        if(!save) throw new GlobalException(ResultCode.ERROR.getCode(),"添加房屋失败");
        System.out.println(housing);
        return ResultVo.ok().message("添加房屋成功!");
    }


    @ApiOperation("根据管理员id查询房屋列表")
    @GetMapping("findAllByManagerId/{managerId}/{page}/{size}")
    public ResultVo findAllByManagerId(@ApiParam(name = "managerId",value = "管理员id") @PathVariable("managerId") String managerId,
                                       @ApiParam(name = "page",value = "当前页") @RequestParam(value = "page",defaultValue = "1") int page,
                                       @ApiParam(name = "size",value = "显示条数") @RequestParam(value = "size",defaultValue = "5") int size){
        Building building = buildingService.getBuildingByManagerId(managerId);
        //System.out.println(building);
        Page<Housing> pageList = new Page<>(page,size);
        QueryWrapper<Housing> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("building_id",building.getId());
        housingService.page(pageList,queryWrapper);
        List<HousingVo> list = new ArrayList<>();
        housingService.findAllPage(pageList,list);
        return ResultVo.ok().data("list",list);
    }

}

