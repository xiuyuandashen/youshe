package com.youshe.mcp.controller;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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


}

