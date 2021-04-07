package com.youshe.mcp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youshe.mcp.entity.Building;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youshe.mcp.entity.vo.BuildingVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zlf
 * @since 2021-04-05
 */
public interface BuildingService extends IService<Building> {

    /**
    * @Description: 根据楼房管理员 id 获取 楼房详细信息
    * @Param: [id]
    * @return: com.youshe.mcp.entity.vo.BuildingVo
    * @Author: zlf
    * @Date: 2021/4/5
    */
    BuildingVo findByBuildingManagerId(String id);

    void findPage(Page<Building> page,List<BuildingVo> list);


    /**
     * @Description: 根据楼房管理员id 获取楼房简要信息
     * @Param: [managerId]
     * @return: com.youshe.mcp.entity.Building
     * @Author: zlf
     * @Date: 2021/4/6
     */
    Building getBuildingByManagerId(String managerId);
}
