package com.youshe.mcp.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youshe.mcp.entity.Announcement;
import com.youshe.mcp.entity.User;
import com.youshe.mcp.entity.vo.AnnouncementVo;
import com.youshe.mcp.mapper.AnnouncementMapper;
import com.youshe.mcp.mapper.UserMapper;
import com.youshe.mcp.service.AnnouncementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
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
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements AnnouncementService {

    @Autowired
    UserMapper userMapper;


    @Override
    public void findPage(Page<Announcement> announcementPage, List<AnnouncementVo> announcementVos) {
        if(announcementPage.getRecords().size()>0){
            announcementPage.getRecords().forEach(e->{
                User user = userMapper.selectById(e.getAuthorId());
                AnnouncementVo announcementVo = new AnnouncementVo();
                announcementVo.setAuthorId(user.getId());
                announcementVo.setAuthorName(user.getName());
                BeanUtils.copyProperties(e,announcementVo);
                announcementVos.add(announcementVo);
            });
        }
    }
}
