package com.youshe.mcp.mapper;

import com.youshe.mcp.entity.ParkingSpace;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zlf
 * @since 2021-09-13
 */
public interface ParkingSpaceMapper extends BaseMapper<ParkingSpace> {


    // 解除占用车位
    int releaseOfOccupancy(ParkingSpace parkingSpace);
}
