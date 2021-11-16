package com.youshe.mcp.service;

import com.youshe.mcp.entity.ParkingSpace;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zlf
 * @since 2021-09-13
 */
public interface ParkingSpaceService extends IService<ParkingSpace> {


    // 解除占用车位
    int releaseOfOccupancy(ParkingSpace parkingSpace);
}
