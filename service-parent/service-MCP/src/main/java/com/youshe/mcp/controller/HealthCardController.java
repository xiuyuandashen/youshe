package com.youshe.mcp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youshe.commonutils.result.ResultCode;
import com.youshe.commonutils.vo.ResultVo;
import com.youshe.mcp.entity.HealthCard;
import com.youshe.mcp.service.HealthCardService;
import com.youshe.servicebase.exceptionHandler.GlobalException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zlf
 * @since 2021-06-02
 */
@RestController
@RequestMapping("/mcp/health-card")
@CrossOrigin
@Api(tags = "健康打卡管理")
public class HealthCardController {


    @Autowired
    HealthCardService healthCardService;

    @ApiOperation("添加打卡记录")
    @PostMapping("/save/{userId}")
    public ResultVo saveCard(
            @RequestBody HealthCard healthCard,
            @PathVariable(name = "userId") String userId
    ){
        healthCard.setUserId(userId);
        boolean save = healthCardService.save(healthCard);
        if(!save) new GlobalException(ResultCode.ERROR.getCode(), "打卡失败！");

        return ResultVo.ok().data("message","打卡成功！");
    }

    @GetMapping("/lastTimeCard/{userId}")
    public ResultVo getLastTimeCard(
            @PathVariable(name = "userId") String userId
    ){
        QueryWrapper<HealthCard> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_modified");
        queryWrapper.eq("user_id",userId);
        List<HealthCard> list = healthCardService.list(queryWrapper);
        if(list.size()>0){
            return ResultVo.ok().data("item",list.get(0));
        }
        return ResultVo.ok();
    }


}

