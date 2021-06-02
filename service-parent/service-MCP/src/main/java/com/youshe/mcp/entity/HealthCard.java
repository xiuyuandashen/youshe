package com.youshe.mcp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zlf
 * @since 2021-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="HealthCard对象", description="")
public class HealthCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "健康打卡id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "体温")
    private Integer bodyTemperature;

    @ApiModelProperty(value = "当前所在地")
    private String currentLocation;

    @ApiModelProperty(value = "0 未离开本地 1 离开本地")
    private Integer isLeaveLocal;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
