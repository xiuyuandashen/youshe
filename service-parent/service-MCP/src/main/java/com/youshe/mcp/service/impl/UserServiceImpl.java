package com.youshe.mcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youshe.mcp.entity.User;
import com.youshe.mcp.mapper.BuildingMapper;
import com.youshe.mcp.mapper.UserMapper;
import com.youshe.mcp.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    BuildingMapper buildingMapper;

    @Override
    public List<String> selectCountUser() {
        return userMapper.selectCountUser();
    }

    @Override
    public List<String> selectCountBuildingUser(String buildingId) {
        return userMapper.selectCountBuildingUser(buildingId);
    }

    @Override
    public User getUserByName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        System.out.println(username);
        queryWrapper.eq("name",username);
        User user = userMapper.selectOne(queryWrapper);
        System.out.println("== === ===" + user) ;
        return user;
    }
}
