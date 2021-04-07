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
 * @Date: 2021/04/07/16:34
 * @Description: 报修表Vo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "报修id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "报修人id")
    private String userId;

    @ApiModelProperty(value = "报修人姓名")
    private String userName;

    @ApiModelProperty(value = "报修房屋id")
    private String addressId;

    @ApiModelProperty(value = "报修房屋号")
    private String roomNumber;

    @ApiModelProperty(value = "报修内容")
    private String content;

    @ApiModelProperty(value = "0 还未维修成功，1 维修成功")
    private Integer isComplete;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic //逻辑删除注解
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}
