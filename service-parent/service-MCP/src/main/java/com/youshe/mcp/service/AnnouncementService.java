package com.youshe.mcp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youshe.mcp.entity.Announcement;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youshe.mcp.entity.vo.AnnouncementVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zlf
 * @since 2021-04-07
 */
public interface AnnouncementService extends IService<Announcement> {


    /**
    * @Description: 分页查询
    * @Param: [announcementPage, announcementVos]
    * @return: void
    * @Author: zlf
    * @Date: 2021/4/7
    */
    void findPage(Page<Announcement> announcementPage, List<AnnouncementVo> announcementVos);
}
