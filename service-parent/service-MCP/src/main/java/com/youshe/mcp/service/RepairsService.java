package com.youshe.mcp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youshe.mcp.entity.Repairs;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youshe.mcp.entity.vo.RepairsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zlf
 * @since 2021-04-07
 */
public interface RepairsService extends IService<Repairs> {
    /**
    * @Description: 分页获取报修信息
    * @Param: [repairsPage, repairsVos]
    * @return: void
    * @Author: zlf
    * @Date: 2021/4/7
    */
    void findPage(Page<Repairs> repairsPage, List<RepairsVo> repairsVos);
}
