package com.youshe.mcp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youshe.mcp.entity.Housing;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youshe.mcp.entity.vo.HousingVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zlf
 * @since 2021-04-05
 */
public interface HousingService extends IService<Housing> {

    /**
    * @Description: 根据楼房id获取房屋信息
    * @Param: [BuildingId]
    * @return: java.util.List<com.youshe.mcp.entity.vo.HousingVo>
    * @Author: zlf
    * @Date: 2021/4/5
    */
    List<HousingVo> selectListByBuildingId(String BuildingId);
    
    /**
    * @Description: 根据房屋id查找房屋信息
    * @Param: [id]
    * @return: com.youshe.mcp.entity.vo.HousingVo
    * @Author: zlf
    * @Date: 2021/4/6
    */
    HousingVo findById(String id);

    /**
    * @Description: 根据用户id查找房屋信息
    * @Param: [userId]
    * @return: java.util.List<com.youshe.mcp.entity.vo.HousingVo>
    * @Author: zlf
    * @Date: 2021/4/6
    */
    List<HousingVo> findByUserId(String userId);

    HousingVo findByRoomNumber(String roomNumber);

    /**
    * @Description: 分页获取房屋详细信息
    * @Param: [page, housingVos]
    * @return: void
    * @Author: zlf
    * @Date: 2021/4/6
    */
    void findAllPage(Page<Housing> page,List<HousingVo> housingVos);

//    void updateById(HousingVo housingVo);
}
