package com.youshe.mcp.entity.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: zlf
 * @Date: 2021/04/05/22:46
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HousingVo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "房屋id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "房屋单元号")
    private String roomNumber;

    @ApiModelProperty(value = "所属的楼房id")
    private String buildingId;

    @ApiModelProperty(value = "所属的楼房名称")
    private String buildName;

    @ApiModelProperty(value = "房主id")
    private String userId;

    @ApiModelProperty(value = "房主名称")
    private String userName;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic //逻辑删除注解
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;
}
