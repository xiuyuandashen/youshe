package com.youshe.mcp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youshe.commonutils.result.ResultCode;
import com.youshe.commonutils.vo.ResultVo;
import com.youshe.mcp.entity.Announcement;
import com.youshe.mcp.entity.User;
import com.youshe.mcp.entity.vo.AnnouncementVo;
import com.youshe.mcp.service.AnnouncementService;
import com.youshe.mcp.service.UserService;
import com.youshe.servicebase.exceptionHandler.GlobalException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
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
@CrossOrigin
public class AnnouncementController {


    @Autowired
    AnnouncementService announcementService;

    @Autowired
    UserService userService;



    @ApiOperation("分页按更新时间降序查询公告")
    @GetMapping("/findAnnouncement/{page}/{size}")
    public ResultVo findAnnouncement(@ApiParam(name = "page",value = "当前页",defaultValue = "1") @PathVariable(name = "page") int page,
                                     @ApiParam(name = "size",value = "显示条数",defaultValue = "3") @PathVariable(name = "size") int size){
        Page<Announcement> announcementPage = new Page<Announcement>(page,size);
        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_modified");
        announcementService.page(announcementPage,queryWrapper);
        List<AnnouncementVo> announcementVos = new ArrayList<>();
        announcementService.findPage(announcementPage,announcementVos);
        return ResultVo.ok().data("announcementVos",announcementVos)
                // 总记录数
                .data("total",announcementPage.getTotal())
                // 当前页
                .data("Current",announcementPage.getCurrent())
                // 当前记录数
                .data("size",announcementPage.getSize());
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
        return ResultVo.ok()
                .message("删除成功！");
    }

    @ApiOperation("根据公告id查询公告信息")
    @GetMapping("{id}")
    public ResultVo selectById(@PathVariable("id") String id){
        Announcement announcement = announcementService.getById(id);
        User user = userService.getById(announcement.getAuthorId());
        AnnouncementVo announcementVo = new AnnouncementVo();
        BeanUtils.copyProperties(announcement,announcementVo);
        announcementVo.setAuthorId(user.getId());
        announcementVo.setAuthorName(user.getName());
        return ResultVo.ok()
                .data("announcementVo",announcementVo);
    }

    @ApiOperation("修改公告")
    @PostMapping("update")
    public ResultVo update(@RequestBody Announcement announcement){
        //if (announcement.getId() == null) throw new GlobalException(ResultCode.ERROR.getCode(), "更新失败!");
        boolean b = announcementService.updateById(announcement);
        if (!b) throw new GlobalException(ResultCode.ERROR.getCode(), "修改失败!");
        return ResultVo.ok()
                .message("修改成功！");
    }

}

