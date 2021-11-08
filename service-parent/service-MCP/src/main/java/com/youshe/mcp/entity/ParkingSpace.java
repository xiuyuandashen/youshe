package com.youshe.mcp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zlf
 * @since 2021-09-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ParkingSpace对象", description="")
public class ParkingSpace implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "经纬度 例如：110,120")
    private String latitudeAndLongitude;

    @ApiModelProperty(value = "车位名称")
    private String parkingSpaceName;

    @ApiModelProperty(value = "停车用户id")
    private String userId;
    @ApiModelProperty(value = "停车用户昵称")
    private String userName;

    @ApiModelProperty(value = "是否已经停车")
    private Integer isStop;

    @ApiModelProperty(value = "是否删除，0未删除 1删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
