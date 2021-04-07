package com.youshe.mcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youshe.commonutils.result.ResultCode;
import com.youshe.commonutils.vo.ResultVo;
import com.youshe.mcp.entity.Building;
import com.youshe.mcp.entity.Housing;
import com.youshe.mcp.entity.User;
import com.youshe.mcp.entity.vo.HousingVo;
import com.youshe.mcp.mapper.BuildingMapper;
import com.youshe.mcp.mapper.HousingMapper;
import com.youshe.mcp.mapper.UserMapper;
import com.youshe.mcp.service.HousingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youshe.servicebase.exceptionHandler.GlobalException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class HousingServiceImpl extends ServiceImpl<HousingMapper, Housing> implements HousingService {

    @Autowired
    HousingMapper housingMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BuildingMapper buildingMapper;

    @Override
    public List<HousingVo> selectListByBuildingId(String BuildingId) {
        // 获取楼房名称
        Building building = buildingMapper.selectById(BuildingId);
        if (building == null) throw new GlobalException(ResultCode.ERROR.getCode(),"楼房不存在！");
        // 根据楼房id查找 房屋列表
        QueryWrapper<Housing> queryWrapper = new QueryWrapper<Housing>();
        queryWrapper.eq("building_id",BuildingId);
        List<Housing> housings = housingMapper.selectList(queryWrapper);
        List<HousingVo> housingVos = new ArrayList<>();
        // 补充Vo
        housings.forEach(e->{
            if(e.getUserId() == null){
                housingVos.add(new HousingVo(e.getId(),e.getRoomNumber(),e.getBuildingId(),building.getName(),
                        null,null,e.getIsDeleted(),e.getGmtCreate(),e.getGmtModified()));
            }else {
                User user = userMapper.selectById(e.getUserId());
                housingVos.add(new HousingVo(e.getId(),e.getRoomNumber(),e.getBuildingId(),building.getName(),
                        e.getUserId(),user.getName(),e.getIsDeleted(),e.getGmtCreate(),e.getGmtModified()));
            }

        });
        return housingVos;
    }

    @Override
    public HousingVo findById(String id) {
        Housing housing = housingMapper.selectById(id);
        if (housing == null) throw new GlobalException(ResultCode.ERROR.getCode(),"未查到此房屋信息！");
        User user = userMapper.selectById(housing.getUserId());
        Building building = buildingMapper.selectById(housing.getBuildingId());
        HousingVo housingVo = new HousingVo(housing.getId(), housing.getRoomNumber(),
                housing.getBuildingId(), building.getName(), housing.getUserId(),
                user.getName(), housing.getIsDeleted(), housing.getGmtCreate(), housing.getGmtModified());

        return housingVo;
    }

    @Override
    public List<HousingVo> findByUserId(String userId) {
        User user = userMapper.selectById(userId);
        if (user == null) throw new GlobalException(ResultCode.ERROR.getCode(),"业主不存在！");
        QueryWrapper<Housing> queryWrapper = new  QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Housing> housings = housingMapper.selectList(queryWrapper);
        List<HousingVo> housingVos = new ArrayList<>();
        housings.forEach(e->{
            Building building = buildingMapper.selectById(e.getBuildingId());
            housingVos.add(new HousingVo(e.getId(),e.getRoomNumber(),e.getBuildingId(),building.getName(),
            e.getUserId(),user.getName(),e.getIsDeleted(),e.getGmtCreate(),e.getGmtModified()));
        });
        return housingVos;
    }

    @Override
    public HousingVo findByRoomNumber(String roomNumber) {

        QueryWrapper<Housing> queryWrapper = new  QueryWrapper<>();

        queryWrapper.eq("room_number",roomNumber);
        Housing housing = housingMapper.selectOne(queryWrapper);
        if (housing == null) throw new GlobalException(ResultCode.ERROR.getCode(),"该房屋不存在!");
        User user = null;
        if( housing.getUserId()!=null )
           user  = userMapper.selectById(housing.getUserId());

        Building building = buildingMapper.selectById(housing.getBuildingId());
        HousingVo housingVo = null;
        if(user != null){
            housingVo = new HousingVo(housing.getId(), housing.getRoomNumber(),
                    housing.getBuildingId(), building.getName(), housing.getUserId(),
                    user.getName(), housing.getIsDeleted(), housing.getGmtCreate(), housing.getGmtModified());
        }else {
            housingVo = new HousingVo(housing.getId(), housing.getRoomNumber(),
                    housing.getBuildingId(), building.getName(), housing.getUserId(),
                    null, housing.getIsDeleted(), housing.getGmtCreate(), housing.getGmtModified());
        }

        return housingVo;
    }

    @Override
    public void findAllPage(Page<Housing> page, List<HousingVo> housingVos) {
        if(page!=null && page.getRecords().size()>0){
            page.getRecords().forEach(housing->{
                HousingVo housingVo = null;
                User user = null;
                Building building = buildingMapper.selectById(housing.getBuildingId());
                if(housing.getUserId() == null){
                    housingVo = new HousingVo(housing.getId(), housing.getRoomNumber(),
                            housing.getBuildingId(), building.getName(), housing.getUserId(),
                            null, housing.getIsDeleted(), housing.getGmtCreate(), housing.getGmtModified());
                }else {
                    user = userMapper.selectById(housing.getUserId());
                    housingVo = new HousingVo(housing.getId(), housing.getRoomNumber(),
                            housing.getBuildingId(), building.getName(), housing.getUserId(),
                            user.getName(), housing.getIsDeleted(), housing.getGmtCreate(), housing.getGmtModified());
                }
                housingVos.add(housingVo);
            });
        }
    }


}
