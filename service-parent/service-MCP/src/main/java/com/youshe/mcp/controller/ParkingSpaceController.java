package com.youshe.mcp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youshe.commonutils.vo.ResultVo;
import com.youshe.mcp.entity.ParkingSpace;
import com.youshe.mcp.service.ParkingSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zlf
 * @since 2021-09-13
 */
@RestController
@RequestMapping("/mcp/parking-space")
@CrossOrigin
public class ParkingSpaceController {

    @Autowired
    ParkingSpaceService parkingSpaceService;

    @PostMapping("/save")
    public ResultVo addParkingSpace(@RequestBody ParkingSpace parkingSpace){
        boolean save = parkingSpaceService.save(parkingSpace);
        return save?ResultVo.ok():ResultVo.error().message("添加失败!");
    }

    @PostMapping("/update")
    public ResultVo updateParkingSpace(@RequestBody ParkingSpace parkingSpace){
        boolean b = parkingSpaceService.updateById(parkingSpace);
        return b?ResultVo.ok():ResultVo.error().message("更新失败!");
    }

    @GetMapping("/get/{id}")
    public ResultVo getById(@PathVariable("id") String id){
        ParkingSpace byId = parkingSpaceService.getById(id);
        return ResultVo.ok().data("parkingSpace",byId);
    }

    /**
    * @Description: 根据用户id获取车位信息
    * @Param: [userId]
    * @return: com.youshe.commonutils.vo.ResultVo
    * @Author: zlf
    * @Date: 2021/11/16
    */
    @GetMapping("/getByUserId/{userId}")
    public ResultVo getByUserId(@PathVariable("userId") String userId){
        List<ParkingSpace> parkingSpaceList = parkingSpaceService.list(
                new QueryWrapper<ParkingSpace>().eq("user_id", userId)
        );
        return ResultVo.ok().data("parkingSpaceList",parkingSpaceList);
    }
    /**
    * @Description: 是否已停放车位
    * @Param: [id]
    * @return: com.youshe.commonutils.vo.ResultVo
    * @Author: zlf
    * @Date: 2021/11/15
    */
    @GetMapping("/getStatus/{id}")
    public ResultVo isStopById(@PathVariable("id") String id){
        ParkingSpace byId = parkingSpaceService.getById(id);
        //System.out.println(byId);
        if(byId.getIsStop().compareTo(Integer.valueOf(1))==0){
            return ResultVo.ok().data("isStop",true);
        }
        return ResultVo.ok().data("isStop",false);
    }
    /**
    * @Description: 查看车位名称是否已存在
    * @Param: [name]
    * @return: com.youshe.commonutils.vo.ResultVo
    * @Author: zlf
    * @Date: 2021/9/17
    */
    @GetMapping("/getName/{name}")
    public ResultVo findName(@PathVariable("name") String name){
        List<ParkingSpace> parking_space_name = parkingSpaceService.list(
                new QueryWrapper<ParkingSpace>().eq("parking_space_name", name)
        );
        //System.out.println(parking_space_name);
        return parking_space_name.size()==0?ResultVo.ok():ResultVo.error().message("车位名称已存在！");
    }

    @GetMapping("/list/{page}/{size}")
    public ResultVo getParkingSpace(@PathVariable("page") Integer page,
                                    @PathVariable("size") Integer size){
        Page<ParkingSpace> parkingPage = new Page<>(page,size);
        parkingSpaceService.page(parkingPage);
        return ResultVo.ok().data("parkingSpaceList",parkingPage.getRecords())
                .data("total",parkingPage.getTotal());
    }
    @GetMapping("/list/all")
    public ResultVo getParkingSpaceAll(){
        List<ParkingSpace> list = parkingSpaceService.list();
        return ResultVo.ok().data("parkingSpaceList",list);
    }

    @DeleteMapping("/delete/{id}")
    public ResultVo deleteById(@PathVariable("id")  String id){
        boolean b = parkingSpaceService.removeById(id);
        return b?ResultVo.ok().message("删除成功!"):ResultVo.error().message("删除失败!");
    }
    /**
    * @Description: 解除车位占用
    * @Param: [parkingSpace]
    * @return: com.youshe.commonutils.vo.ResultVo
    * @Author: zlf
    * @Date: 2021/11/16
    */
    @PostMapping("/update/releaseOfOccupancy")
    public ResultVo releaseOfOccupancy(@RequestBody ParkingSpace parkingSpace){

        int i = parkingSpaceService.releaseOfOccupancy(parkingSpace);
        System.out.println(i);
        return ResultVo.ok();
    }
}

