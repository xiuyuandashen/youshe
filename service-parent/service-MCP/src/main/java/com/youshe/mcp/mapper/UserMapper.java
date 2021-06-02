package com.youshe.mcp.mapper;

import com.youshe.mcp.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zlf
 * @since 2021-04-05
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
    * @Description: 统计业主数量
    * @Param: []
    * @return: java.util.List<java.lang.String> 业主id
    * @Author: zlf
    * @Date: 2021/4/6
    */
    List<String> selectCountUser();
    /**
    * @Description: 根据楼房id统计该楼房的业主人数
    * @Param: [buildingId]
    * @return: java.util.List<java.lang.String>
    * @Author: zlf
    * @Date: 2021/4/6
    */
    List<String> selectCountBuildingUser(String buildingId);

    User getByName(String name);

}
