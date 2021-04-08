package com.youshe.mcp.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youshe.commonutils.result.ResultCode;
import com.youshe.mcp.entity.Housing;
import com.youshe.mcp.entity.Repairs;
import com.youshe.mcp.entity.User;
import com.youshe.mcp.entity.vo.RepairsVo;
import com.youshe.mcp.mapper.HousingMapper;
import com.youshe.mcp.mapper.RepairsMapper;
import com.youshe.mcp.mapper.UserMapper;
import com.youshe.mcp.service.RepairsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youshe.servicebase.exceptionHandler.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zlf
 * @since 2021-04-07
 */
@Service
public class RepairsServiceImpl extends ServiceImpl<RepairsMapper, Repairs> implements RepairsService {


    @Autowired
    UserMapper userMapper;

    @Autowired
    HousingMapper housingMapper;

    @Override
    public void findPage(Page<Repairs> repairsPage, List<RepairsVo> repairsVos) {
        if (repairsPage.getRecords().size()>0){
            repairsPage.getRecords().forEach( e -> {
                User user = userMapper.selectById(e.getUserId());
                if (user == null) throw new GlobalException( ResultCode.ERROR.getCode(), "报修人员不存在！");
                Housing housing = housingMapper.selectById(e.getAddressId());
                if (housing == null) throw new GlobalException(ResultCode.ERROR.getCode(), "所报修的房屋不存在！");

                repairsVos.add(new RepairsVo(e.getId(),e.getUserId(),user.getName(),
                        e.getAddressId(),housing.getRoomNumber(),e.getContent(),e.getIsComplete(),e.getIsDeleted()
                ,e.getGmtCreate(),e.getGmtModified()));
            });
        }
        return ;
    }
}
