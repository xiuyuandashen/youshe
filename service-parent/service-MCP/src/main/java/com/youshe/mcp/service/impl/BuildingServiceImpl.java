package com.youshe.mcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youshe.mcp.entity.Building;
import com.youshe.mcp.entity.Housing;
import com.youshe.mcp.entity.User;
import com.youshe.mcp.entity.vo.BuildingVo;
import com.youshe.mcp.entity.vo.HousingVo;
import com.youshe.mcp.mapper.BuildingMapper;
import com.youshe.mcp.mapper.HousingMapper;
import com.youshe.mcp.mapper.UserMapper;
import com.youshe.mcp.service.BuildingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youshe.mcp.service.HousingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zlf
 * @since 2021-04-05
 */
@Service
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building> implements BuildingService {

    @Autowired
    HousingMapper housingMapper;

    @Autowired
    UserMapper userMapper;


    @Autowired
    HousingService housingService;

    @Autowired
    BuildingMapper buildingMapper;


    @Override
    public BuildingVo findByBuildingManagerId(String id) {
        // 根据 楼房管理员id查找楼房信息
        QueryWrapper<Building> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("building_manager_id",id);
        Building building = baseMapper.selectOne(queryWrapper);
        // 根据楼房id查找楼房中的房屋信息
        List<HousingVo> housingVos = housingService.selectListByBuildingId(building.getId());


        // 根据楼房管理员id查询楼房管理员信息---》主要是获取名字
        User user = userMapper.selectById(building.getBuildingManagerId());
        BuildingVo buildingVo = new BuildingVo(building.getId(),building.getName(),
                building.getBuildingManagerId(),user.getName(),
                building.getIsDeleted(),building.getGmtCreate(),building.getGmtModified(),housingVos);
        return buildingVo;
    }

    @Override
    public void findPage(Page<Building> page,List<BuildingVo> idList) {
        if(page != null){
            page.getRecords().forEach(e->{
                User user = userMapper.selectById(e.getBuildingManagerId());
                idList.add(new BuildingVo(e.getId(),e.getName(),e.getBuildingManagerId(),user.getName(),e.getIsDeleted(),e.getGmtCreate(),e.getGmtModified(),null));
            });
        }

    }

    @Override
    public Building getBuildingByManagerId(String managerId) {
        return buildingMapper.getBuildingByManagerId(managerId);
    }
}
