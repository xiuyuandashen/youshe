package com.youshe.mcp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youshe.commonutils.result.ResultCode;
import com.youshe.commonutils.vo.ResultVo;
import com.youshe.mcp.entity.Announcement;
import com.youshe.mcp.entity.vo.AnnouncementVo;
import com.youshe.mcp.service.AnnouncementService;
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
 * @since 2021-04-07
 */
@RestController
@RequestMapping("/mcp/announcement")
@Api(tags = "公告管理")
public class AnnouncementController {


    @Autowired
    AnnouncementService announcementService;


    @ApiOperation("分页按更新时间降序查询公告")
    @GetMapping("/findAnnouncement/{page}/{size}")
    public ResultVo findAnnouncement(@ApiParam(name = "page",value = "当前页") @RequestParam(value = "page",defaultValue = "1") int page,
                                     @ApiParam(name = "size",value = "显示条数") @RequestParam(value = "size",defaultValue = "5") int size){
        Page<Announcement> announcementPage = new Page<Announcement>(page,size);
        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_modified");
        announcementService.page(announcementPage,queryWrapper);
        List<AnnouncementVo> announcementVos = new ArrayList<>();
        announcementService.findPage(announcementPage,announcementVos);
        return ResultVo.ok().data("announcementVos",announcementVos);
    }


    @ApiOperation("添加公告")
    @PostMapping("save/{userId}")
    public ResultVo save(@RequestBody Announcement announcement,@PathVariable("userId") String userId){
        announcement.setAuthorId(userId);
        boolean save = announcementService.save(announcement);
        if(!save) throw new GlobalException(ResultCode.ERROR.getCode(),"添加失败！");
        return ResultVo.ok().message("添加成功");
    }


    @ApiOperation("根据公告id逻辑删除公告")
    @DeleteMapping("delete/{id}")
    public ResultVo delete(@ApiParam(name = "id",value = "公告id") @PathVariable("id") String id){
        boolean b = announcementService.removeById(id);
        if (!b) throw new GlobalException(ResultCode.ERROR.getCode(), "删除失败！");
        return ResultVo.ok().message("删除成功！");
    }


}

