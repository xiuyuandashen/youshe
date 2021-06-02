package com.youshe.mcp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youshe.commonutils.result.ResultCode;
import com.youshe.commonutils.vo.ResultVo;
import com.youshe.mcp.entity.Repairs;
import com.youshe.mcp.entity.vo.RepairsVo;
import com.youshe.mcp.service.RepairsService;
import com.youshe.servicebase.exceptionHandler.GlobalException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zlf
 * @since 2021-04-07
 */
@RestController
@RequestMapping("/mcp/repairs")
@Api(tags = "报修管理")
@CrossOrigin
public class RepairsController {

    @Autowired
    RepairsService repairsService;



    @ApiOperation("分页按更新时间降序查询报修列表")
    @GetMapping("find/{page}/{size}")
    public ResultVo findRepairs(@ApiParam(name = "page",value = "当前页") @RequestParam(value = "page",defaultValue = "1") int page,
                                @ApiParam(name = "size",value = "显示条数") @RequestParam(value = "size",defaultValue = "5") int size){
        Page<Repairs> repairsPage = new Page<>(page,size);
        QueryWrapper<Repairs> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_modified");
        repairsService.page(repairsPage,queryWrapper);
        List<RepairsVo> repairsVos = new ArrayList<>();
        repairsService.findPage(repairsPage,repairsVos);
        return ResultVo.ok().data("repairsVos",repairsVos).data("total",repairsPage.getTotal());
    }


    @ApiOperation("添加报修记录")
    @PostMapping("save/{userId}/{addressId}")
    public ResultVo save(@RequestBody Repairs repairs,@ApiParam(name = "userId",value = "报修人id") @PathVariable("userId") String userId
    ,@ApiParam(name = "addressId",value = "报修房屋id") @PathVariable("addressId") String addressId){
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(addressId))
            throw new GlobalException(ResultCode.ERROR.getCode(), "上报失败!");
        repairs.setUserId(userId);
        repairs.setAddressId(addressId);
        System.out.println(repairs);
        boolean save = repairsService.save(repairs);
        if(!save) throw new GlobalException(ResultCode.ERROR.getCode(), "上报失败！");
        return ResultVo.ok().message("维修已上报！");
    }

    @ApiOperation("维修成功")
    @GetMapping("success/{id}")
    public ResultVo success(@ApiParam("报修id") @PathVariable("id") String id){
        Repairs repair = repairsService.getById(id);
        repair.setIsComplete(1);
        repairsService.updateById(repair);
        return ResultVo.ok();
    }

    @ApiOperation("修改报修记录")
    @PostMapping("update")
    public ResultVo update(@RequestBody Repairs repairs){
        boolean b = repairsService.updateById(repairs);
        if (!b) throw new GlobalException(ResultCode.ERROR.getCode(), "修改失败!");
        return ResultVo.ok().message("修改成功!");
    }

    @ApiOperation("逻辑删除报修记录")
    @DeleteMapping("delete/{id}")
    public ResultVo delete(@PathVariable("id") String id){
        boolean b = repairsService.removeById(id);
        if (!b) throw new GlobalException(ResultCode.ERROR.getCode(), "删除失败！");
        return ResultVo.ok().message("删除成功！");
    }


    @ApiOperation("根据用户id分页按更新时间降序查询报修列表")
    @GetMapping("find/{userId}/{page}/{size}")
    public ResultVo findRepairsByUserId(
            @ApiParam(name = "userId",value = "用户id") @PathVariable(value = "userId") String userId,
            @ApiParam(name = "page",value = "当前页") @PathVariable(value = "page") int page,
            @ApiParam(name = "size",value = "显示条数") @PathVariable(value = "size") int size
    ){
        Page<Repairs> repairsPage =new Page<>(page,size);
        QueryWrapper<Repairs> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_modified");
        queryWrapper.eq("user_id",userId);
        repairsService.page(repairsPage,queryWrapper);
        List<RepairsVo> repairsVos = new ArrayList<>();
        repairsService.findPage(repairsPage,repairsVos);

        return ResultVo.ok().data("repairsVos",repairsVos).data("total",repairsPage.getTotal());
    }

}

