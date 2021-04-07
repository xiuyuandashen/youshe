package com.youshe.mcp.mapper;

import com.youshe.mcp.entity.Building;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zlf
 * @since 2021-04-05
 */
@Repository
public interface BuildingMapper extends BaseMapper<Building> {
    /**
    * @Description: 根据楼房管理员id 获取楼房简要信息
    * @Param: [managerId]
    * @return: com.youshe.mcp.entity.Building
    * @Author: zlf
    * @Date: 2021/4/6
    */
    Building getBuildingByManagerId(String managerId);

}
